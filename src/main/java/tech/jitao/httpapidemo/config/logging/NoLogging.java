package tech.jitao.httpapidemo.config.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NoLogging {
    boolean omitRequest() default true;

    boolean omitResponse() default true;
}
