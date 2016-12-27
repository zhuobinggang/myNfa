package nfa;

import java.util.Set;

/**
 * Created by kobako on 2016/12/26.
 * Just a game
 */
public class NfaNode {
    private Set<Character> charSet = null; //如果不是实边就为null
    private NfaNode next1 = null,next2 = null; //如果是虚边有可能一条epsilon边或者两条
    private int id; //标记物
    private boolean isEnd = false; //结束节点？

    private boolean isVisted = false; //是否访问过，用于dfs

    public Set<Character> getCharSet() {
        return charSet;
    }

    public void setCharSet(Set<Character> charSet) {
        this.charSet = charSet;
    }

    public NfaNode getNext1() {
        return next1;
    }

    public NfaNode getNext2() {
        return next2;
    }

    public void setNext1(NfaNode next1) {

        this.next1 = next1;
    }

    public void setNext2(NfaNode next2) {
        this.next2 = next2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isVisted() {
        return isVisted;
    }

    public void setVisted(boolean visted) {
        isVisted = visted;
    }

    @Override
    public String toString() {
        return String .valueOf(id);
    }
}
