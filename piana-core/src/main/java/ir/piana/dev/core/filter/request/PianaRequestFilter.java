package ir.piana.dev.core.filter.request;

import ir.piana.dev.core.annotation.PianaServer;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Singleton
public class PianaRequestFilter
        implements ContainerRequestFilter {
    @Context
    private Configuration config;

    private PianaServer pianaServer = null;

    @PostConstruct
    public void init() {
        pianaServer = (PianaServer) config
                .getProperty("PIANA_SERVER_CONFIG");
    }

    @Override
    public void filter(
            ContainerRequestContext containerRequestContext)
            throws IOException {
    }
}
