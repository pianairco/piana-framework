package ir.piana.dev.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface PianaServerCORS {
    String[] allowOrigin() default {""};
    String[] allowHeaders() default {"origin", "content-type", "accept", "authorization"};
    boolean allowCredentials() default true;
    String[] allowMethods() default {"GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"};
}
