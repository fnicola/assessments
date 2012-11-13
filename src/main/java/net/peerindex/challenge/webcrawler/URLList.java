package net.peerindex.challenge.webcrawler;

import com.google.common.collect.ImmutableList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class URLList {

    // total hack - out of time
    private final static String DATA_DIR = "/Users/smanek/apps/challenge/resources";

    private static URL makeUrl(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static final List<URL> Urls = ImmutableList.of(
            makeUrl("http://www.eveningsun.com/localsports/ci_20095590?source=rss"),
            makeUrl("http://www.cruiselinesbest.com/lochness.php"),
            makeUrl("http://lockerz.com/s/181622632"));

    public static Iterable<URL> getURLs() {
/*
        return new Iterable<URL>() {
            @Override
            public Iterator<URL> iterator() {

                return new Iterator<URL>() {

                    final List<File> dataFiles = Lists.newArrayList(new File(DATA_DIR).listFiles());
                    BufferedReader curReader = null;

                    @Override
                    public boolean hasNext() {
                        if (curReader != null || dataFiles.size() > 0) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public synchronized URL next() {
                        if (curReader == null) {
                            BufferedReader br = new BufferedReader(new FileReader(dataFiles.remove(0)));
                            String line;
                            while ((line = br.readLine()) != null) {
                                // process the line.
                            }
                            br.close();
                        }
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
*/
                return Urls;
            }
        }
    
