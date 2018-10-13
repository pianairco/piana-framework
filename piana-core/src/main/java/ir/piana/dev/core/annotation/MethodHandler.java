package ir.piana.dev.core.annotation;

import ir.piana.dev.core.role.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ASUS on 7/28/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //on class level
public @interface MethodHandler {
    RoleType requiredRole() default RoleType.NEEDLESS;
    String httpMethod() default "GET";
    boolean sync() default true;
    boolean urlInjected() default false;
}
