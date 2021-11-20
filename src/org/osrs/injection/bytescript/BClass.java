package org.osrs.injection.bytescript;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * - The target type and this type must have equal super types
 *
 * - This type is free to implement as many interfaces as it sees fit.
 * If the given interface already exits within the target type, then
 * it will not be added again.
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BClass {
    /** The public name of this type in which is to be resolved at runtime **/
    String name() default "this";
}
