package ir.piana.dev.grizzly.http;

import ir.piana.dev.core.http.PianaHttpServer;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Mohammad Rahmati, 8/10/2018 2:21 PM
 */
public class GrizzlyPianaHttpServer
        extends PianaHttpServer {
    private final static Logger logger =
            Logger.getLogger(GrizzlyPianaHttpServer.class);
    HttpServer httpServer;
    HttpServer httpRedirector;

    public GrizzlyPianaHttpServer() {
    }

    @Override
    protected void startService() {
        logger.info("initializing http core....");

        if(pianaServer.sslServer().keyStoreName().isEmpty()) {
            httpServer = GrizzlyHttpServerFactory
                    .createHttpServer(getServerBaseUri(pianaServer), resourceConfig);
        } else {
            SSLContextConfigurator sslCon = new SSLContextConfigurator();
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(
                        Paths.get("./ssl/".concat(pianaServer.sslServer().keyStoreName())));
            } catch (IOException e) {
                logger.error(e);
            }
            sslCon.setKeyStoreBytes(bytes); // contains web keypair
            sslCon.setKeyStorePass(pianaServer.sslServer().keyStorePassword());
            SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslCon);
            sslEngineConfigurator.setClientMode(false).setNeedClientAuth(false);
            httpServer = GrizzlyHttpServerFactory
                    .createHttpServer(getServerBaseUri(pianaServer), resourceConfig, true, sslEngineConfigurator);

            httpRedirector = GrizzlyHttpServerFactory
                    .createHttpServer(getRedirectorBaseUri(pianaServer), resourceConfig);
            httpRedirector.getServerConfiguration()
                    .addHttpHandler(new HttpHandlerRedirector(
                            pianaServer.sslServer().httpsHost(),
                            pianaServer.sslServer().httpsPort(),
                            true));
        }


        logger.info("http core started....");
    }

    @Override
    protected void stopService()
            throws InterruptedException {
        httpServer.shutdown();
        logger.info("http core stopped....");
    }
}
