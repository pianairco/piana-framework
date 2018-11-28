package ir.piana.dev.core.http;

import ir.piana.dev.core.annotation.AnnotationController;
import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.filter.response.CORSFilter;
import ir.piana.dev.core.route.RouteClassGenerator;
import ir.piana.dev.core.session.SessionManager;
import ir.piana.dev.core.websocket.WebSocketClassGenerator;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.ServerSocket;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Mohammad Rahmati, 4/23/2017 11:36 AM
 */
public abstract class PianaHttpServer {
    protected Logger logger = Logger
            .getLogger(PianaHttpServer.class);
    protected Map<String, Object> httpProperties
            = new LinkedHashMap<>();
    protected ResourceConfig resourceConfig
            = new ResourceConfig();
    protected SessionManager sessionManager = null;
    private boolean isStart = false;
    protected PianaServer pianaServer;
    protected Properties properties;

//    public static PianaHttpServer createServer(
//            PianaServer pianaServer)
//            throws Exception {
//        HttpServerType serverType = pianaServer.serverType();
//
//        if(HttpServerType.GRIZZLY == serverType)
//            return new GrizzlyHttpServer(
//                    pianaServer);
//        else if(HttpServerType.JETTY == serverType)
//            return new JettyHttpServer(
//                    pianaServer);
//        else if(HttpServerType.NETTY == serverType)
//            return new NettyHttpServer(
//                    pianaServer);
//        else
//            throw new Exception("type of http core not founded.");
//    }

    public void setPianaServer(PianaServer pianaServer) {
        this.pianaServer = pianaServer;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void addHttpProperties(
            String key, Object property) {
        httpProperties.put(key, property);
    }

    public void start()
            throws Exception {
        if(isStart) {
            logger.error("core is started already.");
            return;
        }

        Set<ServerSocket> webSockets = WebSocketClassGenerator
                .generateWebsocketClasses(pianaServer);
        Set<Class<?>> handlerClasses = RouteClassGenerator
                .generateHandlerClasses(pianaServer);
        resourceConfig.registerClasses(handlerClasses);

//        Set<Class<?>> routeClasses = RouteClassGenerator
//                    .generateRouteClasses(pianaServer);
//
//        Set<Class<?>> documentClasses = RouteClassGenerator
//                .generateDocumentClasses(pianaServer);
//        if(serverConfig.hasDocPath()) {
//            DocumentResolver.initialize(
//                    serverConfig.getDocPath());
//        }
//        resourceConfig.registerClasses(routeClasses);
//        resourceConfig.registerClasses(documentClasses);
//        resourceConfig.register(PianaRequestFilter.class);

        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(CORSFilter.class);

        resourceConfig.registerClasses(AnnotationController.getFilterClasses());

        sessionManager = SessionManager
                .getSessionManager(pianaServer.serverSession());

        httpProperties.put(
                "PIANA_SERVER_CONFIG",
                pianaServer);
//        httpProperties.put(
//                "PIANA_ROUTER_CONFIG",
//                routerConfig);
        httpProperties.put(
                SessionManager.PIANA_SESSION_MANAGER,
                sessionManager);
        resourceConfig.addProperties(httpProperties);
        startService();
        isStart = true;
    }

    protected abstract void startService();

    public void stop()
            throws Exception {
        if(isStart) {
            stopService();
            isStart = false;
        } else {
            logger.error("core is not started already.");
        }
    }

    protected abstract void stopService()
            throws Exception;

    public URI getServerBaseUri(PianaServer pianaServer) {
        String basePath = "";
        if (!pianaServer.sslServer().keyStoreName().isEmpty())
            basePath = basePath.concat("https://")
                    .concat(pianaServer.sslServer().httpsHost())
                    .concat(":")
                    .concat(String.valueOf(pianaServer.sslServer().httpsPort()))
                    .concat("/");
        else
            basePath = basePath.concat("http://")
                    .concat(pianaServer.host())
                    .concat(":")
                    .concat(String.valueOf(pianaServer.httpPort()))
                    .concat("/");
        basePath.concat(pianaServer.httpBaseUrl());

        return UriBuilder.fromUri(basePath)
                .build();
    }

    public URI getServerBaseUri(PianaServer pianaServer, Properties properties) {
        String basePath = "";
        if (!pianaServer.sslServer().keyStoreName().isEmpty())
            basePath = basePath.concat("https://")
                    .concat(pianaServer.sslServer().httpsHost())
                    .concat(":")
                    .concat(String.valueOf(pianaServer.sslServer().httpsPort()))
                    .concat("/");
        else {
            String host = properties.getProperty("http-ip");
            if(host == null)
                host = pianaServer.host();

            String port = properties.getProperty("http-port");
            if(port == null)
                port = String.valueOf(pianaServer.httpPort());
            basePath = basePath.concat("http://")
                    .concat(host)
                    .concat(":")
                    .concat(port)
                    .concat("/");
        }
        basePath.concat(pianaServer.httpBaseUrl());

        return UriBuilder.fromUri(basePath)
                .build();
    }

    public URI getRedirectorBaseUri(PianaServer pianaServer) {
        return UriBuilder.fromUri("http://"
                .concat(pianaServer.host())
                .concat(":")
                .concat(String.valueOf(pianaServer.httpPort()))
                .concat("/")
                .concat(pianaServer.httpBaseUrl()))
                .build();
    }
}
