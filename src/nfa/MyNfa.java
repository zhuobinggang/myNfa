package nfa;

import org.junit.Test;

import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by kobako on 2016/12/23.
 * Just a game
 */
public class MyNfa {
    public Set<Character> charSet = null; //如果不是实边就为null
    public MyNfa next1 = null,next2 = null; //如果是虚边有可能一条epsilon边或者两条
    public int id;
    public boolean isEnd = false;

    public boolean isVisted = false; //是否访问过，用于dfs

    @Override
    public String toString() {
        return String .valueOf(id);
    }


    /**
     *
     * @param sets 字母表
     * @param start nfa开始节点
     * @return
     */
    static int[][] getDfa(List<Set<Character>> sets,MyNfa start){

        List<int[]> result = new ArrayList<>(); //最终结果矩阵
        List<Set<MyNfa>> workList = new ArrayList<>(); //最左列
        Set<MyNfa> s0 = new HashSet<>();
        s0.addAll(epsilonClosure(start));
        s0.add(start);
        workList.add(s0); //放入start的epsilon闭包

        int p = 0;//当前状态指针

        do{
            result.add(new int[sets.size()]); //不停填充状态矩阵

            for(int i=0;i<sets.size();i++){ //相当于每列遍历
                Set set = sets.get(i);

                Set<MyNfa> statu = new HashSet<>();
                workList.get(p).forEach((nfa) -> {
                    if(setEqual(nfa.charSet,set)){//有边出去
                        statu.add(nfa.next1);//CLOSURE(J)
                        statu.addAll(epsilonClosure(nfa.next1));//e_CLOSURE
                    }
                });

                result.get(p)[i] = addToWorkListAbsent(workList,statu);
            }
        }while (++p<workList.size());//行号++

        return getArrayFromList(result);
    }
    @Test
    public void testGetDfa(){
        MyNfa[] status = new MyNfa[4];
        for(int i=0;i<4;i++){
            status[i] = new MyNfa();
            status[i].id = i;
        }

        status[3].isEnd = true;

        status[2].next1 = status[1];
        status[2].next2 = status[3];

        status[1].charSet = new HashSet<>();
        for(int i = '0';i<='9';i++)status[1].charSet.add((char)i);
        status[1].next1 = status[2];

        status[0].next1 = status[1];
        status[0].next2 = status[3];

        ArrayList<Set<Character>> sets = new ArrayList<>();
        sets.add(status[1].charSet);
        int[][] dfa = getDfa(sets,status[0]);

        for(int i=0;i<dfa.length;i++){
            for(int j=0;j<dfa[0].length;j++){
                System.out.print(" "+dfa[i][j]);
            }
            System.out.println();
        }
    }



    static int[][] getArrayFromList(List<int[]> result){
        int lenth = result.get(0).length;
        int[][] it = new  int [result.size()][lenth];
        for(int i=0;i<result.size();i++){
            it[i] = result.get(i);
        }
        return it;
    }
    @Test
    public void testGetArrayFromList(){
        List<int[]> result = new ArrayList<>();
        int[] it1 = new int[3];
        it1[0] = 1;
        int[] it2 = new int[3];
        it2[1] = 2;
        int[] it3 = new int[3];
        it3[2] = 3;

        result.add(it1);
        result.add(it2);
        result.add(it3);

        int[][] its = getArrayFromList(result);
        for(int i=0;i<its.length;i++){
            for(int j=0;j<it1.length;j++){
                System.out.print(" "+its[i][j]);
            }
            System.out.println();
        }
    }


    static int addToWorkListAbsent(List<Set<MyNfa>> workList,Set<MyNfa> set){
        for(int i=0;i<workList.size();i++){
            if(workList.get(i).equals(set)){
                return i; //返回序号
            }
        }
        workList.add(set);
        return workList.size()-1;
    }


    static boolean setEqual(Set set1,Set set2){
        if(set1 == null || set2 == null)return false;
        if(set1.size() != set2.size())return false;

//        return set1.containsAll(set2);
        return set1.equals(set2);
    }
    @Test
    public void testSetEqual(){
        HashSet<MyNfa> set1 = new HashSet<>();
        HashSet<MyNfa> set2 = new HashSet<>();
        MyNfa m0 = new MyNfa();
        MyNfa m1 = new MyNfa();
        MyNfa m2 = new MyNfa();
        m0.id = 1;
        m1.id = 2;
        set1.add(m0);
        set1.add(m1);
        set2.add(m0);
        set2.add(m1);

        assertEquals("错误",true,setEqual(set1,set2));
    }


    static Set<MyNfa> epsilonClosure(MyNfa start){
        Set<MyNfa> set = new HashSet<>();
        dfsForEpsilon(start,set);
        set.remove(start);
        return set;
    }

    static void dfsForEpsilon(MyNfa m,Set<MyNfa> nfaSet){
        if(m.isVisted)return;
        nfaSet.add(m);
        if(m.charSet!=null)return; //有实边不可能有epsilon边
        m.isVisted = true;
        if(m.next1!=null)dfsForEpsilon(m.next1,nfaSet);
        if(m.next2!=null)dfsForEpsilon(m.next2,nfaSet);
        m.isVisted = false;
    }
}
