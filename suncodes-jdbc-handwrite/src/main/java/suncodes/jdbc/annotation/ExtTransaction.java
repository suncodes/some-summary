package suncodes.jdbc.annotation;

import java.lang.annotation.*;

/**
 * @author sunchuizhe
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtTransaction {
    String value() default "";
}
