package net.peerindex;


import net.peerindex.challenge.webcrawler.Factory;
import net.peerindex.challenge.webcrawler.KeyValueStore;

import javax.ws.rs.*;

@Path("/kv")
public class KVResource {

    private final KeyValueStore store = Factory.getStore();

    @GET
    @Produces("text/plain")
    @Path("/status")
    public String getHello() {
        return "The kv currently has " + store.size() + " elements";
    }

    @GET
    @Path("/data/{param}")
    @Produces("text/plain")
    public String get(@PathParam("param") String param) {
        return store.get(param);
    }

    @DELETE
    @Path("/data/{param}")
    @Produces("text/plain")
    public boolean delete(@PathParam("param") String param) {
        return store.delete(param);
    }

}
