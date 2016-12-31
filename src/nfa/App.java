package nfa;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kobako on 2016/12/31.
 * Just a game
 */
public class App {
    public static void main(String[] args) {
        Set<Character> charSet1 = new HashSet<>();
        for(int i='a';i<='z';i++)charSet1.add((char)i);
        NfaPair alpha = NfaManager.createSingleEdgeNfa(charSet1);

        Set<Character> charSet2 = new HashSet<>();
        for(int i='0';i<='9';i++)charSet2.add((char)i);
        NfaPair alphaOrBeta = NfaManager.createOrNfa(charSet1,charSet2);

        NfaPair starAlphaOrBeta = NfaManager.createStarNfa(alphaOrBeta);

        NfaPair need = NfaManager.combineNfas(alpha,starAlphaOrBeta);

        Dfa dfa = NfaUnit.getDfa(need);
        dfa.wordType = WordType.IDENTIFIER;

        DfaDriver driver = new DfaDriver(dfa,"12aabb11abc#2123");

        Token token;
        while((token = driver.getNextToken()).available!=false){
            System.out.println(token.value);
        }
    }
}
