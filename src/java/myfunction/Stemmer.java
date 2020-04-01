/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfunction;

//import java.lang.String.*;

/**
 *
 * @author Cosmas
 */
public class Stemmer {

    private String sword;
    private int numSyllables;
    private int flags;
    private static final int REMOVED_KE = 1;
    private static final int REMOVED_PENG = 2;
    private static final int REMOVED_DI = 4;
    private static final int REMOVED_MENG = 8;
    private static final int REMOVED_TER = 16;
    private static final int REMOVED_BER = 32;
    private static final int REMOVED_PE = 64;

    public String stem(String _word) {
        sword = _word.toLowerCase();

        flags = 0;
        numSyllables = 0;
        for (int i = 0; i < sword.length(); i++) {
            if (isVowel(sword.charAt(i))) {
                numSyllables++;
            }
        }

        if (numSyllables > 2) {
            removeParticle();
        }
        if (numSyllables > 2) {
            removePossessivePronoun();
        }

        int oldLength = sword.length();
        if (numSyllables > 2) {
            removeFirstOrderPrefix();
        }
        if (oldLength != sword.length()) { // a rule is fired
            oldLength = sword.length();
            if (numSyllables > 2) {
                removeSuffix();
            }
            if (oldLength != sword.length()) // a rule is fired
            {
                if (numSyllables > 2) {
                    removeSecondOrderPrefix();
                }
            }
        } else { // fail
            if (numSyllables > 2) {
                removeSecondOrderPrefix();
            }
            if (numSyllables > 2) {
                removeSuffix();
            }
        }

        return sword;
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    private String replaceChar(String s, int index, String repWith) {
        String s1, s2, retVal = "";
        s1 = s.substring(0, index - 1);
        s2 = s.substring(index + 1, s.length());
        retVal = s1 + repWith + s2;

        return retVal;
    }

    private void cutOnEnd(int n) {
        sword = sword.substring(0, sword.length() - n);
    }

    private void cutOnBegin(int n) {
        sword = sword.substring(n, sword.length());
    }

    private void removeParticle() {
        if (sword.endsWith("kah") || sword.endsWith("lah") || sword.endsWith("pun")) {
            cutOnEnd(3);
        }
    }

    private void removePossessivePronoun() {
        if (sword.endsWith("ku") || sword.endsWith("mu")) {
            numSyllables--;
            cutOnEnd(2);
        }

        if (sword.endsWith("nya")) {
            numSyllables--;
            cutOnEnd(3);
        }

    }

    private void removeFirstOrderPrefix() {
        if (sword.startsWith("meng")) {
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(4);
        }

        if (sword.startsWith("meny") && sword.length() > 4 && isVowel(sword.charAt(4))) {
            sword = replaceChar(sword, 3, "s");
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(2);
        }

        if (sword.startsWith("men") && sword.length() > 3 && isVowel(sword.charAt(3))) {
            sword = replaceChar(sword, 2, "t");
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(1);
        }


        if (sword.startsWith("men")) {
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("mem")) {
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("me")) {
            flags |= REMOVED_MENG;
            numSyllables--;
            cutOnBegin(2);
        }
        if (sword.startsWith("peng")) {
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(4);
        }
        if (sword.startsWith("peny") && sword.length() > 4 && isVowel(sword.charAt(4))) {
            sword = replaceChar(sword, 3, "s");
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(2);
        }


        if (sword.startsWith("peny")) {
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(4);
        }

        if (sword.startsWith("pen") && sword.length() > 3 && isVowel(sword.charAt(3))) {
            sword = replaceChar(sword, 2, "t");
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(1);
        }


        if (sword.startsWith("pen")) {
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("pem")) {
            flags |= REMOVED_PENG;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("di")) {
            flags |= REMOVED_DI;
            numSyllables--;
            cutOnBegin(2);
        }

        if (sword.startsWith("ter")) {
            flags |= REMOVED_TER;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("ke")) {
            flags |= REMOVED_KE;
            numSyllables--;
            cutOnBegin(2);
        }

    }

    private void removeSecondOrderPrefix() {
        if (sword.startsWith("ber")) {
            flags |= REMOVED_BER;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.length() == 7 && sword.startsWith("belajar")) {
            flags |= REMOVED_BER;
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("be") && sword.length() > 4 && !isVowel(sword.charAt(2))
                && sword.charAt(3) == 'e' && sword.charAt(4) == 'r') {
            flags |= REMOVED_BER;
            numSyllables--;
            cutOnBegin(2);
        }

        if (sword.startsWith("per")) {
            numSyllables--;
            cutOnBegin(2);
        }

        if (sword.length() == 7 && sword.startsWith("pelajar")) {
            numSyllables--;
            cutOnBegin(3);
        }

        if (sword.startsWith("pe")) {
            flags |= REMOVED_PE;
            numSyllables--;
            cutOnBegin(2);
        }

    }

    private void removeSuffix() {
        if (sword.endsWith("i")
                && !sword.endsWith("si")
                && (flags & REMOVED_BER) == 0
                && (flags & REMOVED_KE) == 0
                && (flags & REMOVED_PENG) == 0) {
            numSyllables--;
            cutOnEnd(1);
        }


        if (sword.endsWith("kan")
                && (flags & REMOVED_KE) == 0
                && (flags & REMOVED_PENG) == 0
                && (flags & REMOVED_PE) == 0) {
            numSyllables--;
            cutOnEnd(3);
        }

        if (sword.endsWith("an")
                && (flags & REMOVED_DI) == 0
                && (flags & REMOVED_MENG) == 0
                && (flags & REMOVED_TER) == 0) {
            numSyllables--;
            cutOnEnd(2);
        }


    }
}
