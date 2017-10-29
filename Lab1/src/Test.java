import RE2DFAo.Exception.DFAStatesException;
import RE2DFAo.NFA2DFA;
import RE2DFAo.entity.DFA;
import RE2DFAo.entity.DFAState;
import RE2DFAo.entity.NFA;
import RE2DFAo.entity.NFAState;
import RE2DFAo.RE2NFA;

import java.util.HashMap;

public class Test {



    public static void main(String[] args) throws DFAStatesException {
//        testUnion();
//        testStar();
//        testRE2NFA("abc|c*b···");
//        System.out.println("ε");

        testNFA2DFA();
    }

    public static void testNFA2DFA() throws DFAStatesException {
        NFA nfa = RE2NFA.getRE2NFA().re2nfa("ab|*");
        DFA dfa = NFA2DFA.getNfa2DFA().nfa2dfa(nfa);
        printDFA(dfa);
    }
    /**
     *  '|' 操作
     */
    public static void testUnion(){

        NFAState state1 = new NFAState(1);
        NFAState state2 = new NFAState(2);
        NFAState state3 = new NFAState(3);
        NFAState state4 = new NFAState(4);
        NFAState state5 = new NFAState(5);
        NFAState state6 = new NFAState(6);

        state1.addEdge("a",state2);
        state3.addEdge("b",state4);
        state5.addEdge("ε",state1);
        state5.addEdge("ε",state3);
        state2.addEdge("ε",state6);
        state4.addEdge("ε",state6);

//        System.out.println(RE2NFA.getLastState(state5).getState());
    }

    /**
     *  '*' 操作
     */
    public static void testStar(){
        NFAState state1 = new NFAState(1);
        NFAState state2 = new NFAState(2);
        NFAState state3 = new NFAState(3);
        NFAState state4 = new NFAState(4);

        state1.addEdge("a",state2);
        state3.addEdge("ε",state4);
        state3.addEdge("ε",state1);
        state2.addEdge("ε",state1);
        state2.addEdge("ε",state4);

//        System.out.println(RE2NFA.getLastState(state3).getState());
    }


    public static void testRE2NFA(String re){
        RE2NFA re2NFA = RE2NFA.getRE2NFA();
        HashMap<String,NFAState> map = re2NFA.thompson(re);
//        System.out.println(map.get("BEGIN").getState());
//
//        System.out.println(map.get("END").getState());
        NFAState begin = map.get("BEGIN");
        printState(begin);
    }


    public static void printState(NFAState begin){
        if (begin.getNexts().size() == 1){
            System.out.println(begin.getState() + " " + begin.getEdges().get(0)+ " " + begin.getNexts().get(0).getState());
            printState(begin.getNexts().get(0));
        }else{

        }
    }


    public static void printDFA(DFA dfa){
        DFAState begin = dfa.getBegin();
        for (String edge : dfa.getEdges()){
            System.out.print("\t" + edge);
        }
        System.out.println();

        for (DFAState dfaState : dfa.getAllStates()){
            System.out.print(dfaState.getState());
            for (int i = 0; i < dfaState.getEdges().size(); i++){
                System.out.print("\t" + dfaState.getNexts().get(i).getState());
            }
            System.out.println();
        }
        }
//        while(true){
//            System.out.print(dfa.getBegin());
//        }
}


