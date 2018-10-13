package ir.piana.dev.core.annotation;

import ir.piana.dev.core.http.HttpServerType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mohammad Rahmati, 7/28/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface PianaServer {
    String host() default "localhost";
    int httpPort() default 8000;
    SSLServer sslServer() default @SSLServer;
    String httpBaseUrl() default "";
    String httpDocIp() default "localhost";
    int httpDocPort() default 8000;
    String docStartUrl() default "piana-doc";
    boolean removeOtherCookies() default false;
    String outputClassPath() default "./classes";
    PianaServerSession serverSession() default @PianaServerSession;
    PianaServerCORS serverCORS() default @PianaServerCORS;
}
