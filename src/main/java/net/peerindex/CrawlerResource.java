package net.peerindex;

import net.peerindex.challenge.webcrawler.Factory;
import net.peerindex.challenge.webcrawler.KeyValueStore;
import net.peerindex.challenge.webcrawler.WebCrawler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/crawler")
public class CrawlerResource {

    private final WebCrawler c = Factory.getCrawler();

    public CrawlerResource() {
        c.execute();
    }

    @GET
    @Produces("text/plain")
    @Path("/status")
    public String getHello() {
        return "This is the crawler resource";
    }
}
