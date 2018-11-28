package ir.piana.dev.grizzly.http;

import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.http.PianaHttpServer;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.jetty.internal.LocalizationMessages;
import org.glassfish.jersey.server.ContainerFactory;

import javax.ws.rs.ProcessingException;

/**
 * @author Mohammad Rahmati, 7/3/2017 6:32 AM
 */
public class JettyPianaHttpServer
        extends PianaHttpServer {
    private final static Logger logger =
            Logger.getLogger(JettyPianaHttpServer.class);
    private Server server;

    JettyPianaHttpServer(PianaServer pianaServer) {
        this.pianaServer = pianaServer;
    }

    @Override
    protected void startService() {
        logger.info("initializing http core....");

        if(pianaServer.sslServer().keyStoreName() != null) {
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(JettyPianaHttpServer.class
                    .getResource("/" + pianaServer.sslServer().keyStoreName()).toExternalForm());
//                    .getResource("/" + pianaServer.sslServer().keyStoreName()).getPath());
            if(!pianaServer.sslServer().keyStorePassword().isEmpty())
                sslContextFactory.setKeyStorePassword(pianaServer.sslServer().keyStorePassword());
            if(!pianaServer.sslServer().keyManagerPassword().isEmpty())
                sslContextFactory.setKeyManagerPassword(pianaServer.sslServer().keyManagerPassword());
            if(!pianaServer.sslServer().provider().isEmpty())
                sslContextFactory.setProvider(pianaServer.sslServer().provider());

            server = new Server();

            // HTTP Configuration
            HttpConfiguration httpConfig = new HttpConfiguration();
            httpConfig.setSecureScheme("https");
            httpConfig.setSecurePort(pianaServer.sslServer().httpsPort());
            httpConfig.addCustomizer(new SecureRequestCustomizer());

//            setup the secure config using the original http config + SecureRequestCustomizer
            HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
            httpsConfig.addCustomizer(new SecureRequestCustomizer());

            //Create a connector on port http to listen for HTTP requests (that will get redirected)
            ServerConnector httpConnector = new ServerConnector(server);
            httpConnector.addConnectionFactory(new HttpConnectionFactory(httpConfig));
            httpConnector.setPort(pianaServer.httpPort());

            //Connector on port https for HTTPS requests
            ServerConnector sslConnector = new ServerConnector(server,
                    new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.toString()),
                    new HttpConnectionFactory(httpsConfig));
            sslConnector.setPort(pianaServer.sslServer().httpsPort());

            // Setting HTTP and HTTPS connectors
            server.setConnectors(new Connector[]{httpConnector, sslConnector});

//            JettyRedirectUnsecuredHandler redirectHandler =
//                    new JettyRedirectUnsecuredHandler();
            final JettyHttpContainer container = ContainerFactory.createContainer(
                    JettyHttpContainer.class, resourceConfig);
            ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
//            contextHandlers.setHandlers(new Handler[] { redirectHandler });
            HandlerList handlers = new HandlerList();
            handlers.addHandler(contextHandlers);
            if (container != null) {
                handlers.addHandler(container);
            }
            try {

                server.setHandler(handlers);
                server.start();
//                core.join();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessingException(LocalizationMessages.ERROR_WHEN_CREATING_SERVER(), e);
            }


//            core = JettyHttpContainerFactory.createServer(
//                    getServerBaseUri(pianaServer), sslContextFactory, resourceConfig);
        } else {
            server = JettyHttpContainerFactory.createServer(
                    getServerBaseUri(pianaServer, properties), resourceConfig);
//                    getServerBaseUri(pianaServer), resourceConfig);
        }

//        create and start jetty core

    }

    protected void startService2() {
        logger.info("initializing http core....");

        if(pianaServer.sslServer().keyStoreName() != null) {
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(JettyPianaHttpServer.class
                    .getResource("/" + pianaServer.sslServer().keyStoreName()).toExternalForm());
//                    .getResource("/" + pianaServer.sslServer().keyStoreName()).getPath());
            if(!pianaServer.sslServer().keyStorePassword().isEmpty())
                sslContextFactory.setKeyStorePassword(pianaServer.sslServer().keyStorePassword());
            if(!pianaServer.sslServer().keyManagerPassword().isEmpty())
                sslContextFactory.setKeyManagerPassword(pianaServer.sslServer().keyManagerPassword());
            if(!pianaServer.sslServer().provider().isEmpty())
                sslContextFactory.setProvider(pianaServer.sslServer().provider());

            server = new Server();

            // Setup HTTP Connector
            HttpConfiguration httpConf = new HttpConfiguration();
            httpConf.setSecurePort(pianaServer.sslServer().httpsPort());
            httpConf.setSecureScheme("https");

            // Establish the HTTP ServerConnector
            ServerConnector httpConnector = new ServerConnector(server,
                    new HttpConnectionFactory(httpConf));
            httpConnector.setPort(pianaServer.httpPort());
            server.addConnector(httpConnector);

            // Setup HTTPS Configuration
            HttpConfiguration httpsConf = new HttpConfiguration(httpConf);
            httpsConf.addCustomizer(new SecureRequestCustomizer()); // adds ssl info to request object

            // Establish the HTTPS ServerConnector
            ServerConnector httpsConnector = new ServerConnector(server,
                    new SslConnectionFactory(sslContextFactory,"http/1.1"),
                    new HttpConnectionFactory(httpsConf));
            httpsConnector.setPort(pianaServer.sslServer().httpsPort());
            server.addConnector(httpsConnector);


            try {
                HandlerList handlers = new HandlerList();
                handlers.addHandler(new SecuredRedirectHandler());
                final JettyHttpContainer container = ContainerFactory.createContainer(JettyHttpContainer.class, resourceConfig);
                if (container != null) {
                    handlers.addHandler(container);
                }
                server.setHandler(handlers);
                server.start();
//                core.join();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessingException(LocalizationMessages.ERROR_WHEN_CREATING_SERVER(), e);
            }


//            core = JettyHttpContainerFactory.createServer(
//                    getServerBaseUri(pianaServer), sslContextFactory, resourceConfig);
        } else {
            server = JettyHttpContainerFactory.createServer(
                    getServerBaseUri(pianaServer, properties), resourceConfig);
//                    getServerBaseUri(pianaServer), resourceConfig);
        }

//        create and start jetty core

    }

    @Override
    protected void stopService() throws Exception {
        server.stop();
    }
}
