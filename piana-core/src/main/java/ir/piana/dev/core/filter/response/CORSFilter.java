package ir.piana.dev.core.filter.response;

import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.annotation.PianaServerCORS;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * @author Mohammad Rahmati, 4/12/2017 2:27 PM
 */
@Singleton
public class CORSFilter
        implements ContainerResponseFilter {
    @Context
    private Configuration config;

    private PianaServer pianaServer = null;
    private PianaServerCORS serverCORS = null;

    @PostConstruct
    public void init() {
        pianaServer = (PianaServer) config
                .getProperty("PIANA_SERVER_CONFIG");
        serverCORS = pianaServer.serverCORS();
    }

    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response)
            throws IOException {
        response.getHeaders().add("Cache-Control", "no-cache");
        response.getHeaders().addAll(
                "Access-Control-Allow-Origin",
                serverCORS.allowOrigin());
        String allowHeaders = "";
        for (String allowHeader : serverCORS.allowHeaders())
            allowHeaders = allowHeaders.concat(allowHeader).concat(",");
        response.getHeaders().addAll(
                "Access-Control-Allow-Headers",
                allowHeaders);
        response.getHeaders().add(
                "Access-Control-Allow-Credentials",
                serverCORS.allowCredentials());
        response.getHeaders().add(
                "Allow",
                "HEAD, POST, GET, OPTIONS, PUT");
        String allowMethods = "";
        for (String allowMethod : serverCORS.allowMethods())
            allowMethods = allowMethods.concat(allowMethod).concat(",");
        response.getHeaders().addAll(
                "Access-Control-Allow-Methods",
                allowMethods);
//                serverCORS.allowMethods());
    }
}
