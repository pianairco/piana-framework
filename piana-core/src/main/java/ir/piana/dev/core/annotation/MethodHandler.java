package ir.piana.dev.core.annotation;

import ir.piana.dev.core.role.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //on class level
public @interface MethodHandler {
    RoleType requiredRole() default RoleType.NEEDLESS;
    String httpMethod() default "GET";
    boolean sync() default true;
    boolean urlInjected() default false;
}
