package org.objectweb.asm;

import org.objectweb.asm.tree.MethodNode;
import org.pf.text.StringPattern;

public class Wildcard {

    private String methodCard;

    /**
     * <p/>
     * (II)V -> (I?)? -> (IB)Z
     *
     * @param methodCard
     */
    public Wildcard(String methodCard) {
        this.methodCard = methodCard;
    }
    public boolean matches(MethodNode md){
    	return matches(md.desc);
    }
    public boolean matches(String s) {
        StringPattern pattern = new StringPattern(methodCard);
        pattern.multiCharWildcardMatchesEmptyString(true);
        return pattern.matches(s); // <== returns true
    }
}