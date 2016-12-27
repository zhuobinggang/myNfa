package nfa;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kobako on 2016/12/26.
 * Just a game
 */
public class NfaManagerTest {
    @Test
    //test "alpha (alpha | beta) *"
    public void testRegex(){
        Set<Character> alphaSet = new HashSet<>();
        Set<Character> betaSet = new HashSet<>();
        for(int i='0';i<='9';i++)betaSet.add((char)i);
        for(int i='a';i<='z';i++)alphaSet.add((char)i);

//        alphaSet.forEach(System.out::println);
        NfaPair alpha = NfaManager.createSingleEdgeNfa(alphaSet);
        NfaPair beta = NfaManager.createSingleEdgeNfa(betaSet);
        NfaPair alphaOrBeta = NfaManager.createOrNfa(alpha,beta);
        NfaPair alphaOrBetaStar = NfaManager.createStarNfa(alphaOrBeta);
        alpha = NfaManager.createSingleEdgeNfa(alphaSet);
        NfaPair result = NfaManager.combineNfas(alpha,alphaOrBetaStar);

        Dfa dfa = NfaUnit.getDfa(result);

        int[][] dfaReact = dfa.getDfa();
        for(int i=0;i<dfaReact.length;i++){
            for(int j=0;j<dfaReact[0].length;j++){
                System.out.print(" "+dfaReact[i][j]);
            }
            System.out.println();
        }

        System.out.println("终态集:");
        dfa.getEndStatus().forEach(System.out::println);
    }

    @Test
    public void testgetOrNfa(){
        Set<Character> orCharSet1 = new HashSet<>();
        Set<Character> orCharSet2 = new HashSet<>();
        orCharSet1.add('a');
        orCharSet2.add('b');

        NfaPair n1 = NfaManager.createOrNfa(orCharSet1,orCharSet2);

        //NfaPair orNfa = NfaManager.createOrNfa(orCharSet1,orCharSet2);
        Dfa dd = NfaUnit.getDfa(n1);
        int[][] dfa = dd.getDfa();
        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }

        //dd.getEndStatus().forEach(System.out::println);
    }

    @Test
    public void testCombineNfa(){
        Set<Character> singleCharSet = new HashSet<>();
        Set<Character> orCharSet1 = new HashSet<>();
        Set<Character> orCharSet2 = new HashSet<>();
        singleCharSet.add('c');
        orCharSet1.add('a');
        orCharSet2.add('b');
        NfaPair pair2 = NfaManager.createSingleEdgeNfa(singleCharSet);
        NfaPair pair1 = NfaManager.createOrNfa(orCharSet1,orCharSet2);
        NfaPair combined = NfaManager.combineNfas(pair1,pair2);

        int[][] dfa = NfaUnit.getDfa(combined).getDfa();

        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    public void testSingleNfa(){
        Set<Character> orCharSet1 = new HashSet<>();
        Set<Character> orCharSet2 = new HashSet<>();
        orCharSet1.add('a');
        orCharSet2.add('b');
        NfaPair n1 = NfaManager.createOrNfa(orCharSet1,orCharSet2);

        Set<Character> singleSet = new HashSet<>();
        singleSet.add('c');

        NfaPair nfa = NfaManager.createSingleEdgeNfa(n1,singleSet);

        int[][] dfa = NfaUnit.getDfa(nfa).getDfa();

        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    public void testCreateStarNfa(){
        Set<Character> charSet = new HashSet<>();
        charSet.add('a');
        NfaPair nfa = NfaManager.createSingleEdgeNfa(charSet);
        nfa = NfaManager.createStarNfa(nfa);

        int[][] dfa = NfaUnit.getDfa(nfa).getDfa();

        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    public void testCreateStar2(){
        Set<Character> set1 = new HashSet<>();
        set1.add('a');
        Set<Character> set2 = new HashSet<>();
        set1.add('b');

        NfaPair aEdge = NfaManager.createSingleEdgeNfa(set1);
        NfaPair bEdge = NfaManager.createSingleEdgeNfa(set2);
        NfaPair orNfa = NfaManager.createOrNfa(aEdge,bEdge);

        NfaPair nfa = NfaManager.createStarNfa(orNfa);

        int[][] dfa = NfaUnit.getDfa(nfa).getDfa();

        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }
    }
}
