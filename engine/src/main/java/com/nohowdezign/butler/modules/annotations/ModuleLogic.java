package com.nohowdezign.butler.modules.annotations;

import java.lang.annotation.*;

/**
 * @author Noah Howard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleLogic {
    String subjectWord() default "";
}
