package org.objectweb.asm.util;

import org.objectweb.asm.Opcodes;

public class Modifier {

	public static String convertAccessMaskToModifier(int asmAccessMask)
    {
        int modifier = 0;

        // Convert the ASM access info into Reflection API modifiers.

        if ((asmAccessMask & Opcodes.ACC_FINAL) != 0)
            modifier |= java.lang.reflect.Modifier.FINAL;

        if ((asmAccessMask & Opcodes.ACC_NATIVE) != 0)
            modifier |= java.lang.reflect.Modifier.NATIVE;

        if ((asmAccessMask & Opcodes.ACC_INTERFACE) != 0)
            modifier |= java.lang.reflect.Modifier.INTERFACE;

        if ((asmAccessMask & Opcodes.ACC_ABSTRACT) != 0)
            modifier |= java.lang.reflect.Modifier.ABSTRACT;

        if ((asmAccessMask & Opcodes.ACC_PRIVATE) != 0)
            modifier |= java.lang.reflect.Modifier.PRIVATE;

        if ((asmAccessMask & Opcodes.ACC_PROTECTED) != 0)
            modifier |= java.lang.reflect.Modifier.PROTECTED;

        if ((asmAccessMask & Opcodes.ACC_PUBLIC) != 0)
            modifier |= java.lang.reflect.Modifier.PUBLIC;

        if ((asmAccessMask & Opcodes.ACC_STATIC) != 0)
            modifier |= java.lang.reflect.Modifier.STATIC;

        if ((asmAccessMask & Opcodes.ACC_STRICT) != 0)
            modifier |= java.lang.reflect.Modifier.STRICT;

        if ((asmAccessMask & Opcodes.ACC_SYNCHRONIZED) != 0)
            modifier |= java.lang.reflect.Modifier.SYNCHRONIZED;

        if ((asmAccessMask & Opcodes.ACC_TRANSIENT) != 0)
            modifier |= java.lang.reflect.Modifier.TRANSIENT;

        if ((asmAccessMask & Opcodes.ACC_VOLATILE) != 0)
            modifier |= java.lang.reflect.Modifier.VOLATILE;

        return java.lang.reflect.Modifier.toString(modifier);
    }
}
