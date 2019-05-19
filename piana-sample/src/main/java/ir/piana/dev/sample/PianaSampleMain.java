package ir.piana.dev.sample;

import ir.piana.dev.core.PianaAnnotationAppMain;
import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.grizzly.http.GrizzlyPianaHttpServer;

/**
 * @author Mohammad Rahmati, 1/26/2019
 */
@PianaServer
public class PianaSampleMain {
    public static void main(String[] args)
            throws Exception {
        PianaAnnotationAppMain.start(
                new GrizzlyPianaHttpServer(),
                PianaSampleMain.class);
    }
}
