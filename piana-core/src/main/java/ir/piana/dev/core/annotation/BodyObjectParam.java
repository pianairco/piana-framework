package ir.piana.dev.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) //on param level
public @interface BodyObjectParam {
}
