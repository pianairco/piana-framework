package ir.piana.dev.core;

import ir.piana.dev.core.annotation.AnnotationController;
import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.annotation.PianaSpaceProperty;
import ir.piana.dev.core.http.PianaHttpServer;
import ir.piana.dev.core.space.PianaSpace;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Mohammad Rahmati, 4/23/2017 12:13 PM
 */
public abstract class PianaAnnotationAppMain {
    protected static Logger logger = Logger
            .getLogger(PianaAnnotationAppMain.class);
    private static PianaHttpServer pianaHttpServer = null;

    public static void start(PianaHttpServer pianaHttpServer, Class pianaServerAnnotatedClass)
            throws Exception {
        PianaServer pianaServer = AnnotationController
                .getPianaServer(pianaServerAnnotatedClass);
        List<PianaSpaceProperty> pianaSpaceProperties = AnnotationController
                .getPianaSpaceProperties(pianaServerAnnotatedClass);
        for(PianaSpaceProperty spaceProperty : pianaSpaceProperties){
            PianaSpace.setProperty(spaceProperty);
        }
        pianaHttpServer.setPianaServer(pianaServer);
        pianaHttpServer.start();
        logger.debug("core started. please wait for initialize...");
    }
}