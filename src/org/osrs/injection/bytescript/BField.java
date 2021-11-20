package org.osrs.injection.bytescript;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BField {
	/**
	 * Used to identify a field in proxy class to soft-inject
	 * Soft-inject - field is just a mapping; will be renamed to obfuscated name
	 */
    /** The public name of this field **/
    String name() default "this";
}
