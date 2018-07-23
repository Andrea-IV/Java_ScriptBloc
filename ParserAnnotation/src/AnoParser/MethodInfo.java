package AnoParser;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo{
    int revision() default 1;
    String name();
    String date();
    String arguments();
    String comments();
    String returnValue();
}
