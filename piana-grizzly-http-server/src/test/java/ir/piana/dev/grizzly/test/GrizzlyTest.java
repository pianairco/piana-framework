package ir.piana.dev.grizzly.test;

import ir.piana.dev.core.PianaAnnotationAppMain;
import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.annotation.SSLServer;
import ir.piana.dev.grizzly.http.GrizzlyPianaHttpServer;

/**
 * @author Mohammad Rahmati, 10/13/2018
 */
@PianaServer(sslServer = @SSLServer(keyStoreName = "keystore.jks", keyStorePassword = "password"))
public class GrizzlyTest {
    public static void main(String[] args) throws Exception {
        PianaAnnotationAppMain.start(new GrizzlyPianaHttpServer(), GrizzlyTest.class);
    }
}
