package net.peerindex.challenge.webcrawler;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.CounterMetric;
import com.yammer.metrics.core.GaugeMetric;
import com.yammer.metrics.core.TimerMetric;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AsyncCrawler implements WebCrawler {

    private final CounterMetric completedFetches = Metrics.newCounter(AsyncCrawler.class, "completed");
    private final TimerMetric timerMetric = Metrics.newTimer(AsyncCrawler.class, "fetchTimes",
            TimeUnit.MILLISECONDS, TimeUnit.HOURS);

    private final GaugeMetric<Integer> availFetcherCapacity = Metrics.newGauge(AsyncCrawler.class,
            "availFetcherCapacity",
            new GaugeMetric<Integer>() {
                @Override
                public Integer value() {
                    return concurrentLock.availablePermits();
                }
            });

    private static final Logger LOG = Logger.getLogger(AsyncCrawler.class);

    private static final int SIMULTANEOUS_REQUESTS = 1000;

    private final KeyValueStore store;
    private final Iterator<URL> urls;
    private final AsyncHttpClient client = new AsyncHttpClient();
    private final Semaphore concurrentLock = new Semaphore(SIMULTANEOUS_REQUESTS);
    private final Thread manager;

    public AsyncCrawler(KeyValueStore s, Iterator<URL> i) {
        this.store = s;
        this.urls = i;

        this.manager = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("here");
                try {
                    while (urls.hasNext()) {
                        final String url = urls.next().toString();
                        System.out.println("fetching url");

                        concurrentLock.acquire();

                        client.prepareGet(url).execute(new AsyncCompletionHandler<Boolean>() {
                            @Override
                            public Boolean onCompleted(Response response) throws Exception {
                                System.out.println("completed " + url);
                                if (response.getStatusCode() == 200) {
                                    AsyncCrawler.this.store.put(url, response.getResponseBody());
                                } else {
                                    LOG.warn("Couldn't fetch " + url + " (code = " + response.getStatusCode() + ")");
                                }

                                timerMetric.count();
                                concurrentLock.release();
                                return true;
                            }
                        });
                    }
                } catch (Throwable t) {
                    LOG.error("FetcherManager error: " + t, t);
                }
            }
        }, "FetcherManager");
    }

    @Override
    public void initialise() {

    }

    @Override
    public void execute() {
        this.manager.start();
    }

    @Override
    public void shutdown() {
        this.manager.interrupt();
        this.client.close();
    }
}
