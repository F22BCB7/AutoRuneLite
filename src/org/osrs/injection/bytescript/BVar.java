package org.osrs.injection.bytescript;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BVar {
	/**
	 * Used to identify fields in proxy classes to hard-inject
	 * Hard-inject - literally switch FieldNode from one ClassNode to another
	 */
}
