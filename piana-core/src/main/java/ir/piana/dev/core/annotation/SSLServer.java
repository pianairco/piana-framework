package ir.piana.dev.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mohammad Rahmati, 7/28/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface SSLServer {
    String httpsHost() default "localhost";
    int httpsPort() default 8443;
    String keyStoreName() default "";
    String keyStorePassword() default "";
    String keyManagerPassword() default "";
    String provider() default "";
}
