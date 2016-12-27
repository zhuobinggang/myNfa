package nfa;

import org.junit.Test;

import java.util.*;

/**
 * Created by kobako on 2016/12/23.
 * Just a game
 */
public class NfaUnit {


     private static int[][] getDfa(List<Set<Character>> sets, NfaNode start,List<Set<NfaNode>> workList){

        List<int[]> result = new ArrayList<>(); //最终结果矩阵
//        List<Set<NfaNode>> workList = new ArrayList<>(); //最左列
        Set<NfaNode> s0 = new HashSet<>(); //s0 初始集合

         s0.addAll(epsilonClosure(start));
         s0.add(start);
         workList.add(s0); //放入start的epsilon闭包

        int p = 0;//当前状态指针

        do{
            result.add(new int[sets.size()]); //不停填充状态矩阵

            for(int i=0;i<sets.size();i++){ //相当于每列遍历
                Set set = sets.get(i);

                //每一列对应的状态
                Set<NfaNode> statu = new HashSet<>();
                workList.get(p).forEach((nfa) -> {
                    if(setEqual(nfa.getCharSet(),set)){//有边出去
                        statu.add(nfa.getNext1());//CLOSURE(J)
                        statu.addAll(epsilonClosure(nfa.getNext1()));//e_CLOSURE
                    }
                });

                result.get(p)[i] = addToWorkListAbsent(workList,statu);
            }
        }while (++p<workList.size());//行号++

        return getArrayFromList(result);
    }


    /**
     * 通过子集算法从nfa得到dfa
     * @param nfa
     * @return
     */
    public static Dfa getDfa(NfaPair nfa){
        List<Set<NfaNode>> workList = new ArrayList<>();
        int[][] result = getDfa(nfa.getCharSetList(),nfa.getStart(),workList);
        Set<Integer> endStatus = getDfaEndStatus(workList);

        Dfa dfa = new Dfa();
        dfa.setDfa(result);
        dfa.setEndStatus(endStatus);
        return dfa;
    }

    private static Set<Integer> getDfaEndStatus(List<Set<NfaNode>> workList){
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<workList.size();i++){
            Set<NfaNode> nfaSet = workList.get(i);
            Iterator<NfaNode> nfa = nfaSet.iterator();
            while(nfa.hasNext()){
                if(nfa.next().isEnd()){
                    set.add(i);
                    break;
                }
            }
        }
        return set;
    }

    private static int[][] getArrayFromList(List<int[]> result){
        int lenth = result.get(0).length;
        int[][] it = new  int [result.size()][lenth];
        for(int i=0;i<result.size();i++){
            it[i] = result.get(i);
        }
        return it;
    }

    private static int addToWorkListAbsent(List<Set<NfaNode>> workList, Set<NfaNode> set){
        //如果是空集也不放入
        if(set.isEmpty())return -1;

        for(int i=0;i<workList.size();i++){
            if(workList.get(i).equals(set)){
                return i; //返回序号
            }
        }
        workList.add(set);
        return workList.size()-1;
    }

    private static boolean setEqual(Set set1,Set set2){
        if(set1 == null || set2 == null)return false;
        if(set1.size() != set2.size())return false;

//        return set1.containsAll(set2);
        return set1.equals(set2);
    }


    private static Set<NfaNode> epsilonClosure(NfaNode start){
        Set<NfaNode> set = new HashSet<>();
        dfsForEpsilon(start,set);
        set.remove(start);
        return set;
    }

    private static void dfsForEpsilon(NfaNode m, Set<NfaNode> nfaSet){
        if(m.isVisted())return;
        nfaSet.add(m);
        if(m.getCharSet()!=null)return; //有实边不可能有epsilon边
        m.setVisted(true);
        if(m.getNext1()!=null)dfsForEpsilon(m.getNext1(),nfaSet);
        if(m.getNext2()!=null)dfsForEpsilon(m.getNext2(),nfaSet);
        m.setVisted(false);
    }
}
