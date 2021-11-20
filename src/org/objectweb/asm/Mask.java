package org.objectweb.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.math.BigInteger;

/**
 * User: Tyler Sedlar, TSedlar
 * Date: 11/9/13
 * Time: 11:03 AM
 * To alter this template use File | Settings | File Templates.
 */
public class Mask extends BigInteger {
	private static final long serialVersionUID = 1L;
	public static final Mask NOP = of(Opcodes.NOP);
    public static final Mask ACONST_NULL = of(Opcodes.ACONST_NULL);
    public static final Mask ICONST_M1 = of(Opcodes.ICONST_M1);
    public static final Mask ICONST_0 = of(Opcodes.ICONST_0);
    public static final Mask ICONST_1 = of(Opcodes.ICONST_1);
    public static final Mask ICONST_2 = of(Opcodes.ICONST_2);
    public static final Mask ICONST_3 = of(Opcodes.ICONST_3);
    public static final Mask ICONST_4 = of(Opcodes.ICONST_4);
    public static final Mask ICONST_5 = of(Opcodes.ICONST_5);
    public static final Mask LCONST_0 = of(Opcodes.LCONST_0);
    public static final Mask LCONST_1 = of(Opcodes.LCONST_1);
    public static final Mask FCONST_0 = of(Opcodes.FCONST_0);
    public static final Mask FCONST_1 = of(Opcodes.FCONST_1);
    public static final Mask FCONST_2 = of(Opcodes.FCONST_2);
    public static final Mask DCONST_0 = of(Opcodes.DCONST_0);
    public static final Mask DCONST_1 = of(Opcodes.DCONST_1);
    public static final Mask BIPUSH = of(Opcodes.BIPUSH);
    public static final Mask SIPUSH = of(Opcodes.SIPUSH);
    public static final Mask LDC = of(Opcodes.LDC);
    public static final Mask ILOAD = of(Opcodes.ILOAD);
    public static final Mask LLOAD = of(Opcodes.LLOAD);
    public static final Mask FLOAD = of(Opcodes.FLOAD);
    public static final Mask DLOAD = of(Opcodes.DLOAD);
    public static final Mask ALOAD = of(Opcodes.ALOAD);
    public static final Mask IALOAD = of(Opcodes.IALOAD);
    public static final Mask LALOAD = of(Opcodes.LALOAD);
    public static final Mask FALOAD = of(Opcodes.FALOAD);
    public static final Mask DALOAD = of(Opcodes.DALOAD);
    public static final Mask AALOAD = of(Opcodes.AALOAD);
    public static final Mask BALOAD = of(Opcodes.BALOAD);
    public static final Mask CALOAD = of(Opcodes.CALOAD);
    public static final Mask SALOAD = of(Opcodes.SALOAD);
    public static final Mask ISTORE = of(Opcodes.ISTORE);
    public static final Mask LSTORE = of(Opcodes.LSTORE);
    public static final Mask FSTORE = of(Opcodes.FSTORE);
    public static final Mask DSTORE = of(Opcodes.DSTORE);
    public static final Mask ASTORE = of(Opcodes.ASTORE);
    public static final Mask IASTORE = of(Opcodes.IASTORE);
    public static final Mask LASTORE = of(Opcodes.LASTORE);
    public static final Mask FASTORE = of(Opcodes.FASTORE);
    public static final Mask DASTORE = of(Opcodes.DASTORE);
    public static final Mask AASTORE = of(Opcodes.AASTORE);
    public static final Mask BASTORE = of(Opcodes.BASTORE);
    public static final Mask CASTORE = of(Opcodes.CASTORE);
    public static final Mask SASTORE = of(Opcodes.SASTORE);
    public static final Mask POP = of(Opcodes.POP);
    public static final Mask POP2 = of(Opcodes.POP2);
    public static final Mask DUP = of(Opcodes.DUP);
    public static final Mask DUP_X1 = of(Opcodes.DUP_X1);
    public static final Mask DUP_X2 = of(Opcodes.DUP_X2);
    public static final Mask DUP2 = of(Opcodes.DUP2);
    public static final Mask DUP2_X1 = of(Opcodes.DUP2_X1);
    public static final Mask DUP2_X2 = of(Opcodes.DUP2_X2);
    public static final Mask SWAP = of(Opcodes.SWAP);
    public static final Mask IADD = of(Opcodes.IADD);
    public static final Mask LADD = of(Opcodes.LADD);
    public static final Mask FADD = of(Opcodes.FADD);
    public static final Mask DADD = of(Opcodes.DADD);
    public static final Mask ISUB = of(Opcodes.ISUB);
    public static final Mask LSUB = of(Opcodes.LSUB);
    public static final Mask FSUB = of(Opcodes.FSUB);
    public static final Mask DSUB = of(Opcodes.DSUB);
    public static final Mask IMUL = of(Opcodes.IMUL);
    public static final Mask LMUL = of(Opcodes.LMUL);
    public static final Mask FMUL = of(Opcodes.FMUL);
    public static final Mask DMUL = of(Opcodes.DMUL);
    public static final Mask IDIV = of(Opcodes.IDIV);
    public static final Mask LDIV = of(Opcodes.LDIV);
    public static final Mask FDIV = of(Opcodes.FDIV);
    public static final Mask DDIV = of(Opcodes.DDIV);
    public static final Mask IREM = of(Opcodes.IREM);
    public static final Mask LREM = of(Opcodes.LREM);
    public static final Mask FREM = of(Opcodes.FREM);
    public static final Mask DREM = of(Opcodes.DREM);
    public static final Mask INEG = of(Opcodes.INEG);
    public static final Mask LNEG = of(Opcodes.LNEG);
    public static final Mask FNEG = of(Opcodes.FNEG);
    public static final Mask DNEG = of(Opcodes.DNEG);
    public static final Mask ISHL = of(Opcodes.ISHL);
    public static final Mask LSHL = of(Opcodes.LSHL);
    public static final Mask ISHR = of(Opcodes.ISHR);
    public static final Mask LSHR = of(Opcodes.LSHR);
    public static final Mask IUSHR = of(Opcodes.IUSHR);
    public static final Mask LUSHR = of(Opcodes.LUSHR);
    public static final Mask IAND = of(Opcodes.IAND);
    public static final Mask LAND = of(Opcodes.LAND);
    public static final Mask IOR = of(Opcodes.IOR);
    public static final Mask LOR = of(Opcodes.LOR);
    public static final Mask IXOR = of(Opcodes.IXOR);
    public static final Mask LXOR = of(Opcodes.LXOR);
    public static final Mask IINC = of(Opcodes.IINC);
    public static final Mask I2L = of(Opcodes.I2L);
    public static final Mask I2F = of(Opcodes.I2F);
    public static final Mask I2D = of(Opcodes.I2D);
    public static final Mask L2I = of(Opcodes.L2I);
    public static final Mask L2F = of(Opcodes.L2F);
    public static final Mask L2D = of(Opcodes.L2D);
    public static final Mask F2I = of(Opcodes.F2I);
    public static final Mask F2L = of(Opcodes.F2L);
    public static final Mask F2D = of(Opcodes.F2D);
    public static final Mask D2I = of(Opcodes.D2I);
    public static final Mask D2L = of(Opcodes.D2L);
    public static final Mask D2F = of(Opcodes.D2F);
    public static final Mask I2B = of(Opcodes.I2B);
    public static final Mask I2C = of(Opcodes.I2C);
    public static final Mask I2S = of(Opcodes.I2S);
    public static final Mask LCMP = of(Opcodes.LCMP);
    public static final Mask FCMPL = of(Opcodes.FCMPL);
    public static final Mask FCMPG = of(Opcodes.FCMPG);
    public static final Mask DCMPL = of(Opcodes.DCMPL);
    public static final Mask DCMPG = of(Opcodes.DCMPG);
    public static final Mask IFEQ = of(Opcodes.IFEQ);
    public static final Mask IFNE = of(Opcodes.IFNE);
    public static final Mask IFLT = of(Opcodes.IFLT);
    public static final Mask IFGE = of(Opcodes.IFGE);
    public static final Mask IFGT = of(Opcodes.IFGT);
    public static final Mask IFLE = of(Opcodes.IFLE);
    public static final Mask IF_ICMPEQ = of(Opcodes.IF_ICMPEQ);
    public static final Mask IF_ICMPNE = of(Opcodes.IF_ICMPNE);
    public static final Mask IF_ICMPLT = of(Opcodes.IF_ICMPLT);
    public static final Mask IF_ICMPGE = of(Opcodes.IF_ICMPGE);
    public static final Mask IF_ICMPGT = of(Opcodes.IF_ICMPGT);
    public static final Mask IF_ICMPLE = of(Opcodes.IF_ICMPLE);
    public static final Mask IF_ACMPEQ = of(Opcodes.IF_ACMPEQ);
    public static final Mask IF_ACMPNE = of(Opcodes.IF_ACMPNE);
    public static final Mask GOTO = of(Opcodes.GOTO);
    public static final Mask JSR = of(Opcodes.JSR);
    public static final Mask RET = of(Opcodes.RET);
    public static final Mask TABLESWITCH = of(Opcodes.TABLESWITCH);
    public static final Mask LOOKUPSWITCH = of(Opcodes.LOOKUPSWITCH);
    public static final Mask IRETURN = of(Opcodes.IRETURN);
    public static final Mask LRETURN = of(Opcodes.LRETURN);
    public static final Mask FRETURN = of(Opcodes.FRETURN);
    public static final Mask DRETURN = of(Opcodes.DRETURN);
    public static final Mask ARETURN = of(Opcodes.ARETURN);
    public static final Mask RETURN = of(Opcodes.RETURN);
    public static final Mask GETSTATIC = of(Opcodes.GETSTATIC);
    public static final Mask PUTSTATIC = of(Opcodes.PUTSTATIC);
    public static final Mask GETFIELD = of(Opcodes.GETFIELD);
    public static final Mask PUTFIELD = of(Opcodes.PUTFIELD);
    public static final Mask INVOKEVIRTUAL = of(Opcodes.INVOKEVIRTUAL);
    public static final Mask INVOKESPECIAL = of(Opcodes.INVOKESPECIAL);
    public static final Mask INVOKESTATIC = of(Opcodes.INVOKESTATIC);
    public static final Mask INVOKEINTERFACE = of(Opcodes.INVOKEINTERFACE);
    public static final Mask NEW = of(Opcodes.NEW);
    public static final Mask NEWARRAY = of(Opcodes.NEWARRAY);
    public static final Mask ANEWARRAY = of(Opcodes.ANEWARRAY);
    public static final Mask ARRAYLENGTH = of(Opcodes.ARRAYLENGTH);
    public static final Mask ATHROW = of(Opcodes.ATHROW);
    public static final Mask CHECKCAST = of(Opcodes.CHECKCAST);
    public static final Mask INSTANCEOF = of(Opcodes.INSTANCEOF);
    public static final Mask MONITORENTER = of(Opcodes.MONITORENTER);
    public static final Mask MONITOREXIT = of(Opcodes.MONITOREXIT);
    public static final Mask MULTIANEWARRAY = of(Opcodes.MULTIANEWARRAY);
    public static final Mask IFNULL = of(Opcodes.IFNULL);
    public static final Mask IFNONNULL = of(Opcodes.IFNONNULL);
    public int distance = 10;
    public String owner = null, desc = null;
    public int operand = -1;
    public boolean descContains = false;

    public Mask(String bigInt) {
        super(bigInt);
    }

    public static Mask of(int opcode) {
        return new Mask(new BigInteger("1").shiftLeft(opcode).toString());
    }

    private Mask from(String bigInt, int distance, String owner, String desc, int operand, boolean descContains) {
        Mask mask = new Mask(bigInt);
        mask.distance = distance;
        mask.owner = owner;
        mask.desc = desc;
        mask.operand = operand;
        mask.descContains = descContains;
        return mask;
    }

    private Mask from(int distance, String owner, String desc, int operand, boolean descContains) {
        return from(toString(), distance, owner, desc, operand, descContains);
    }

    public Mask distance(int distance) {
        return from(distance, owner, desc, operand, descContains);
    }

    public Mask own(String owner) {
        return from(distance, owner, desc, operand, descContains);
    }

    public Mask describe(String desc) {
        return from(distance, owner, desc, operand, descContains);
    }

    public Mask describe(String desc, boolean descContains) {
        return from(distance, owner, desc, operand, descContains);
    }

    public Mask operand(int operand) {
        return from(distance, owner, desc, operand, descContains);
    }

    @Override
    public final Mask or(BigInteger bi) {
        return from(super.or(bi).toString(), distance, owner, desc, operand, descContains);
    }

    @Override
    public final Mask xor(BigInteger bi) {
        return from(super.xor(bi).toString(), distance, owner, desc, operand, descContains);
    }

    @Override
    public final Mask and(BigInteger bi) {
        return from(super.and(bi).toString(), distance, owner, desc, operand, descContains);
    }

    @Override
    public final Mask andNot(BigInteger bi) {
        return from(super.andNot(bi).toString(), distance, owner, desc, operand, descContains);
    }

    @Override
    public final Mask not() {
        return from(super.not().toString(), distance, owner, desc, operand, descContains);
    }

    public final boolean matches(AbstractInsnNode ain) {
        if (of(ain.getOpcode()).and(this).bitCount() == 0) return false;
        if (ain instanceof FieldInsnNode) {
            FieldInsnNode fin = (FieldInsnNode) ain;
            return (owner == null || fin.owner.equals(owner)) && (desc == null || (descContains ? fin.desc.contains(desc) :
                    fin.desc.equals(desc)));
        } else if (ain instanceof MethodInsnNode) {
            MethodInsnNode min = (MethodInsnNode) ain;
            return (owner == null || min.owner.equals(owner)) && (desc == null || (descContains ? min.desc.contains(desc) :
            	new Wildcard(desc).matches(min.desc)));
        } else if (ain instanceof IntInsnNode) {
            IntInsnNode iin = (IntInsnNode) ain;
            return operand == -1 || iin.operand == operand;
        } else if (ain instanceof VarInsnNode) {
            VarInsnNode vin = (VarInsnNode) ain;
            return operand == -1 || vin.var == operand;
        }
        return true;
    }
}
