package RE2DFAo;

import RE2DFAo.entity.DFA;
import RE2DFAo.entity.DFAState;
import RE2DFAo.entity.NFA;
import RE2DFAo.entity.NFAState;

import java.util.HashSet;
import java.util.Set;

public class NFA2DFA {

    public static void main(String[] args) {
        NFA2DFA nfa2DFA = NFA2DFA.getNfa2DFA();
        NFAState n1 = new NFAState(1);
        NFAState n2 = new NFAState(2);
        NFAState n3 = new NFAState(3);
        NFAState n4 = new NFAState(4);
        NFAState n5 = new NFAState(5);
        NFAState n6 = new NFAState(6);
        NFAState n7 = new NFAState(7);
        n1.addEdge("ε",n2);
        n1.addEdge("a",n3);
        n2.addEdge("ε",n4);
        n3.addEdge("ε",n5);
        n4.addEdge("a",n6);
        n4.addEdge("ε",n7);
        Set<NFAState> set = nfa2DFA.getEpsilonClosureByNFAState(new HashSet<>(),n1);
        Set<NFAState> set1 = nfa2DFA.getStatesByEpsilonClosure(set,"a");
        for (NFAState i : set1){
            System.out.println(i.getState());
        }

    }

    private static NFA2DFA nfa2DFA;

    public static NFA2DFA getNfa2DFA(){
        if (nfa2DFA == null){
            return new NFA2DFA();
        }
        else{
            return nfa2DFA;
        }
    }

    private NFA2DFA() {
    }


    private Set<String> edges;   // dfa中的边的集合
    private Set<Set<Integer>> dfaStatesSet;  //dfa中的状态的集合

    public DFA nfa2dfa(NFA nfa){
        edges = nfa.getStates();
        NFAState nfaBegin = nfa.getBegin();
        DFAState dfaBegin = new DFAState(1,getEpsilonClosureByNFAState(new HashSet<>(),nfaBegin));
        dfaStatesSet.add(setTranslation(dfaBegin.getStates()));

        return null;
    }

    /**
     * 计算epsilon闭包
     * @param set
     * @param state
     * @return
     */
    private Set<NFAState> getEpsilonClosureByNFAState(Set<NFAState> set,NFAState state){
        set.add(state);
        for (int i = 0; i < state.getEdges().size(); i++){
            if (state.getEdges().get(i).equals("ε")){
                return getEpsilonClosureByNFAState(set,state.getNexts().get(i));
            }
        }
        return set;
    }

    /**
     * 根据epsilon闭包 和 边 来确定状态
     * @param closure
     * @param edge
     * @return
     */
    private Set<NFAState> getStatesByEpsilonClosure(Set<NFAState> closure,String edge){
        Set<NFAState> set = new HashSet<>();
        for (NFAState nfaState : closure){
            for (int i = 0; i < nfaState.getEdges().size(); i++){
                if (nfaState.getEdges().get(i).equals(edge)){
                    set.add(nfaState.getNexts().get(i));
                }
            }
        }
        return set;
    }

    private void constructTable(DFAState begin){

    }

    private Set<Integer> setTranslation(Set<NFAState> nfaStates){
        Set<Integer> integers = new HashSet<>();
        for (NFAState nfaState : nfaStates){
            integers.add(nfaState.getState());
        }
        return integers;
    }

}
