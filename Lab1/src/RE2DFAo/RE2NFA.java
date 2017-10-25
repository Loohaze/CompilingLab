package RE2DFAo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class RE2NFA {

    public static void main(String[] args) {
        RE2NFA re2NFA = RE2NFA.getRE2NFA();
        HashMap<String,NFAState> map = re2NFA.thompson("abc|c*b···");
        System.out.println(map.get("BEGIN").getState());
        System.out.println(map.get("END").getState());
    }

    private static RE2NFA re2NFA;

    private NFA nfa;

    private RE2NFA(){
        nfa = new NFA(null,new ArrayList<>());
    }

    public static RE2NFA getRE2NFA() {
        if (re2NFA == null){
            return new RE2NFA();
        }
        else {
            return re2NFA;
        }
    }

    public NFA re2NFA(String re){
        return null;
    }


    public HashMap<String,NFAState> thompson(String re){

        HashMap<String,NFAState> map = new HashMap<>();

        Stack<NFAState> stack = new Stack<>();

        char[] chars = re.toCharArray();

        int state_count = 1;
        int count = 0;
        while (true){
            if (count == re.length()){
                break;
            }

            char ch = chars[count];

            NFAState beginState = new NFAState();
            NFAState endState = new NFAState();

            if (ch != '·'){
                beginState.setState(state_count++);
                endState.setState(state_count++);
            }

            // '|' and '·' are all binary operation
            if (ch == '|' || ch == '·'){
                NFAState second = stack.pop();
                NFAState first = stack.pop();
                NFAState second_end = getLastState(second);
                NFAState first_end = getLastState(first);

                // '|' 操作
                if (ch == '|'){
                    beginState.addEdge("ε",second);
                    beginState.addEdge("ε",first);

                    second_end.addEdge("ε",endState);
                    first_end.addEdge("ε",endState);

                    stack.push(beginState);
                }
                // '·'
                else{
                    first_end.addEdge("ε",second);
                    stack.push(first);
                }
            }
            // '*' 操作
            else if(ch == '*'){
                NFAState last = stack.pop();
                NFAState last_end = getLastState(last);

                beginState.addEdge("ε",endState); // 保证 getLastState 函数获得的是正确的终态
                beginState.addEdge("ε",last);
                last_end.addEdge("ε",last);
                last_end.addEdge("ε",endState);

                stack.push(beginState);
            }
            // ch 是一个 character
            else {
                beginState.addEdge(String.valueOf(ch),endState);

                stack.push(beginState);
            }

            count++;
        }

        NFAState re_start_state = stack.pop();
        map.put("BEGIN",re_start_state);
        map.put("END",getLastState(re_start_state));
        return map;
    }


    // 在构造re2nfa时用到， 默认终态只有一个, 如果是*操作，则终态为最右的状态
    private static NFAState getLastState(NFAState begin){
        NFAState next = begin;
        while (next.getNexts().size() != 0){
            next = next.getNexts().get(0);
        }
        return next;
    }
}
