package nfa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通过一些条件生成nfa pair 包含start和end节点
 */
public class NfaManager {

    /**
     * 通过存在的一个nfa 和一个字母集 创建单边nfa
     * @param nfa
     * @param charSet
     * @return
     */
    public static NfaPair createSingleEdgeNfa(NfaPair nfa,Set<Character> charSet){
        nfa.addCharSetToListAbsent(charSet);

        NfaNode start = nfa.getEnd();
        start.setEnd(false);
        NfaNode end = new NfaNode();

        start.setCharSet(charSet);
        start.setNext1(end);
        start.setId(0);
        end.setId(1);

//        nfa.setStart(start);
        nfa.setEnd(end);
        end.setEnd(true);
        return nfa;
    }

    public static NfaPair createSingleEdgeNfa(Set<Character> charSet){
        return createSingleEdgeNfa(createSimpleNfa(),charSet);
    }

    /**
     * 通过两个nfa创建一个 or nfa
     * 注意：会对传入两nfa节点进行修改
     * @param nfa1
     * @param nfa2
     * @return
     */
    public static NfaPair createOrNfa(NfaPair nfa1,NfaPair nfa2){
        NfaPair result = new NfaPair();
        NfaNode start = new NfaNode(),end = new NfaNode();

        //设置始终节点
        result.setStart(start);
        result.setEnd(end);

        //整合字母表
        result.addcharListToListAbsent(nfa1.getCharSetList());
        result.addcharListToListAbsent(nfa2.getCharSetList());

        //start epsilon edge
        start.setNext1(nfa1.getStart());
        start.setNext2(nfa2.getStart());


        //ends epsilon edge & set End false
        nfa1.getEnd().setEnd(false);
        nfa1.getEnd().setNext1(end);
        nfa2.getEnd().setEnd(false);
        nfa2.getEnd().setNext1(end);

        end.setEnd(true);
        return result;
    }

    /**
     * 通过两个字母集创建 or Nfa
     * @param charSet1
     * @param charSet2
     * @return
     */
    public static NfaPair createOrNfa(Set<Character> charSet1,Set<Character> charSet2){
        NfaPair nfa1 = createSingleEdgeNfa(createSimpleNfa(),charSet1);
        NfaPair nfa2 = createSingleEdgeNfa(createSimpleNfa(),charSet2);
        return createOrNfa(nfa1,nfa2);
    }

    /**
     * 创建仅包含一个空节点的nfa
     * @return
     */
    public static NfaPair createSimpleNfa(){
        NfaPair nfa = new NfaPair();
        NfaNode node = new NfaNode();
        nfa.setStart(node);
        nfa.setEnd(node);
        return nfa;
    }

    /**
     * 对存在的一个nfa创建闭包
     * @param nfa
     * @return
     */
    public static NfaPair createStarNfa(NfaPair nfa){
        NfaPair result = new NfaPair();
        NfaNode start = new NfaNode(),end = new NfaNode();

        result.setStart(start);
        result.setEnd(end);
        result.addcharListToListAbsent(nfa.getCharSetList());

        nfa.getEnd().setEnd(false);
        nfa.getEnd().setNext1(nfa.getStart());
        nfa.getEnd().setNext2(end);

        start.setNext1(nfa.getStart());
        start.setNext2(nfa.getEnd());

        end.setEnd(true);
        return result;
    }

    /**
     * 通过epsilon边链接两个nfa,重设终态同时合并字母表
     * @param nfa1
     * @param nfa2
     * @return
     */
    public static NfaPair combineNfas(NfaPair nfa1,NfaPair nfa2){
        NfaNode end1 = nfa1.getEnd();
        end1.setEnd(false);
        end1.setNext1(nfa2.getStart());
        nfa2.getEnd().setEnd(true);
        nfa1.setEnd(nfa2.getEnd());
        //合并字母表
        nfa1.addcharListToListAbsent(nfa2.getCharSetList());
        return nfa1;
    }
}
