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
@Target(ElementType.TYPE) //on class level
public @interface AssetHandler {
    String assetPath();
    RoleType requiredRole() default RoleType.NEEDLESS;
    boolean sync() default true;
    boolean urlInjected() default false;
}
