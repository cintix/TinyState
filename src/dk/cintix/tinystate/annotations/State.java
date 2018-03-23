package dk.cintix.tinystate.annotations;

import dk.cintix.tinystate.config.States;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author migo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface State {
    States from();
    States to() default States.NONE;
    States onError() default States.ERROR;
}
