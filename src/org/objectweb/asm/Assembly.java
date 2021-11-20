package org.objectweb.asm;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;

import java.util.ArrayList;
import java.util.List;

public class Assembly {

    public static String name(AbstractInsnNode ain) {
        try {
            return Printer.OPCODES[ain.getOpcode()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "NULL";
        }
    }

    public static AbstractInsnNode prev(AbstractInsnNode ain, int max, Mask mask) {
        for (int i = 0; i < max && (ain = ain.getPrevious()) != null; i++) {
            if (mask.matches(ain)) return ain;
        }
        return null;
    }

    public static AbstractInsnNode next(AbstractInsnNode ain, int max, Mask mask) {
        for (int i = 0; i < max && (ain = ain.getNext()) != null; i++) {
            if (mask.matches(ain)) return ain;
        }
        return null;
    }

    public static ArrayList<AbstractInsnNode> next(AbstractInsnNode ain, Mask... masks) {
        ArrayList<AbstractInsnNode> insns = new ArrayList<AbstractInsnNode>();
        for (Mask mask : masks) {
            AbstractInsnNode next = next(ain, mask.distance, mask);
            if (next == null) return null;
            ain = next;
            insns.add(next);
        }
        return insns;
    }

    public static ArrayList<AbstractInsnNode> find(ClassNode cn, Mask... masks) {
        for (MethodNode mn : (List<MethodNode>) cn.methods) {
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                ArrayList<AbstractInsnNode> result = next(ain, masks);
                if (result != null) return result;
            }
        }
        return null;
    }
    public static ArrayList<AbstractInsnNode> find(MethodNode mn, Mask... masks) {
        if (mn == null) return null;
        for (AbstractInsnNode ain : mn.instructions.toArray()) {
            ArrayList<AbstractInsnNode> result = next(ain, masks);
            if (result != null) return result;
        }
        return null;
    }
    public static ArrayList<AbstractInsnNode> find(MethodNode[] mn, Mask... masks) {
        if (mn == null) return null;
        for (MethodNode men : mn) {
            for (AbstractInsnNode ain : men.instructions.toArray()) {
                ArrayList<AbstractInsnNode> result = next(ain, masks);
                if (result != null) return result;
            }
        }
        return null;
    }
    public static ArrayList<ArrayList<AbstractInsnNode>> findAll(AbstractInsnNode[] ains, Mask mask) {
        ArrayList<ArrayList<AbstractInsnNode>> all = new ArrayList<ArrayList<AbstractInsnNode>>();
        for (AbstractInsnNode ain : ains) {
            ArrayList<AbstractInsnNode> result = next(ain, mask);
            if (result != null) all.add(result);
        }
        return all.isEmpty() ? null : all;
    }
    public static ArrayList<ArrayList<AbstractInsnNode>> findAll(MethodNode mn, Mask... masks) {
        ArrayList<ArrayList<AbstractInsnNode>> all = new ArrayList<ArrayList<AbstractInsnNode>>();
        for (AbstractInsnNode ain : mn.instructions.toArray()) {
            ArrayList<AbstractInsnNode> result = next(ain, masks);
            if (result != null) all.add(result);
        }
        return all;
    }
    public static ArrayList<ArrayList<AbstractInsnNode>> findAll(ClassNode cn, Mask... masks) {
        ArrayList<ArrayList<AbstractInsnNode>> all = new ArrayList<ArrayList<AbstractInsnNode>>();
        for (MethodNode mn : (List<MethodNode>) cn.methods) {
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                ArrayList<AbstractInsnNode> result = next(ain, masks);
                if (result != null) all.add(result);
            }
        }
        return all.isEmpty() ? null : all;
    }
}
