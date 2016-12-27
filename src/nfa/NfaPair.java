package nfa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by kobako on 2016/12/26.
 * Just a game
 */
public class NfaPair {
    private NfaNode start,end; //开始跟结束节点
    //字母表
    private List<Set<Character>> charSetList = new ArrayList<>();

    public void addCharSetToListAbsent(Set<Character> charSet){
        if(!this.charSetList.contains(charSet)){
            this.charSetList.add(charSet);
        }
    }

    public void addcharListToListAbsent(List<Set<Character>> charSetList){
        charSetList.forEach((set) -> {
            if(!this.charSetList.contains(set)){
                this.charSetList.add(set);
            }
        });
    }

    public List<Set<Character>> getCharSetList() {
        return charSetList;
    }

    public NfaNode getStart() {
        return start;
    }

    public void setStart(NfaNode start) {
        this.start = start;
    }

    public NfaNode getEnd() {
        return end;
    }

    public void setEnd(NfaNode end) {
        this.end = end;
    }
}
