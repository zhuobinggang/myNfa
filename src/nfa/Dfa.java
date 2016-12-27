package nfa;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述dfa的类
 * 包含结构有：终态集，状态矩阵
 */
public class Dfa {
    private Set<Integer> endStatus = new HashSet<>();
    private int[][] dfa;

    public Set<Integer> getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(Set<Integer> endStatus) {
        this.endStatus = endStatus;
    }

    public int[][] getDfa() {
        return dfa;
    }

    public void setDfa(int[][] dfa) {
        this.dfa = dfa;
    }
}
