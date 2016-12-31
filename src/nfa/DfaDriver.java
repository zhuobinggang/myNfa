package nfa;

import java.util.Stack;

/**
 * Created by kobako on 2016/12/31.
 * Just a game
 */
public class DfaDriver {
    Dfa dfa = null; //传入dfa
    String line; //传入需要识别的字符串

    int index = 0; //字符指针


    public DfaDriver(Dfa dfa, String line) {
        this.dfa = dfa;
        this.line = line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setDfa(Dfa dfa) {
        this.dfa = dfa;
    }

    public Token getNextToken(){
        Token token = new Token();
        token.type = dfa.wordType;
        if(index>=line.length()){
            token.available = false;
            return token;
        }

        int state = 0; //初始状态
        int start = index; //这个token的开头
        Stack<Integer> stateStack = new Stack<>(); //状态栈
        char c;

        int col;//列号
        int colNum = dfa.getCharSetList().size();

        while(state!= -1){
            c = getChar();
            col = -1;
            for(int i=0;i<colNum;i++){
                if(dfa.getCharSetList().get(i).contains(c)){
                    col = i;
                    break;
                }
            }
            if(col!=-1) state = dfa.getDfa()[state][col];
            else state = -1;

            if(dfa.getEndStatus().contains(state)){//终态集合清栈
                stateStack.clear();
            }

            stateStack.add(state);
        }

        //回滚
        state = stateStack.pop();
        while(!dfa.getEndStatus().contains(state)){
            rollBack();
            if(stateStack.isEmpty())break;
            state = stateStack.pop();
        }


        //System.out.println("表演结束：index = "+index+" start = "+start);

        if(index-start<=0){ //如果根本没有读进字符
            token.available = false;
        }else{
            token.value = line.substring(start,index-start);
        }
        return token;
    }

    private char getChar(){
        if(index>=line.length()){
            index++;
            return '#';
        }
        return line.charAt(index++);
    }

    private void rollBack(){
        index--;
    }
}
