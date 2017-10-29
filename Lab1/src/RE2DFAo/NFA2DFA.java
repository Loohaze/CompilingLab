package RE2DFAo;

import RE2DFAo.Exception.DFAStatesException;
import RE2DFAo.entity.DFA;
import RE2DFAo.entity.DFAState;
import RE2DFAo.entity.NFA;
import RE2DFAo.entity.NFAState;
import com.sun.istack.internal.NotNull;

import java.util.*;

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
        n1.addEdge("ε",n3);
        n2.addEdge("ε",n4);
        n3.addEdge("b",n5);
        n4.addEdge("a",n6);
        n4.addEdge("ε",n7);
        Set<NFAState> set = nfa2DFA.getEpsilonClosureByNFAState(new HashSet<>(),n1);
        Set<NFAState> set1 = nfa2DFA.getStatesByEpsilonClosure(set,"a");
        for (NFAState i : set){
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


    private int index;
    private Set<String> edges;   // dfa中的边的集合
    private Set<Set<Integer>> dfaStatesSet;  //dfa中的状态的nfa集合的集合
    private ArrayList<DFAState> dfaSet; // dfa中各个状态的集合
    private ArrayList<DFAState> dfaEnds;// dfa的终态
    private List<DFAState> startStateSet;
    private List<DFAState> endStateSet;
    private HashMap<Integer,List<DFAState>> equivalentMap;

    public DFA nfa2dfa(NFA nfa) throws DFAStatesException {
        edges = nfa.getStates();
        NFAState nfaBegin = nfa.getBegin();
        DFAState dfaBegin = new DFAState(1,getEpsilonClosureByNFAState(new HashSet<>(),nfaBegin));
        dfaStatesSet = new HashSet<>();
        dfaStatesSet.add(setTranslation(dfaBegin.getStates()));

        dfaSet = new ArrayList<>();
        dfaSet.add(dfaBegin);
        index = 1;
        constructTable(dfaBegin,2,1);

        startStateSet = new ArrayList<>();
        endStateSet = new ArrayList<>();
        dfaEnds = new ArrayList<>();
        getDFAEnds(nfa.getEnds());

        dfaOptimize();  // 最小化DFA
        return new DFA(dfaBegin,dfaEnds,dfaSet,edges,dfaStatesSet);
    }


    public void dfaOptimize(){

        equivalentMap = new HashMap<>();
        equivalentMap.put(1,startStateSet);
        equivalentMap.put(2,endStateSet);
        divideStates(startStateSet,3,equivalentMap);
        divideStates(endStateSet,1000,equivalentMap);

        
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
                getEpsilonClosureByNFAState(set,state.getNexts().get(i));
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

    /**
     * 根据DFA起始状态构造DFA表
     * @param begin DFA起始状态
     * @param count 当前累计状态数
     * @param state_count 剩余未搜索的DFA状态数
     */
    private void constructTable(DFAState begin,int count, int state_count) throws DFAStatesException {
        for (String edge : edges){
            Set<NFAState> beginNext = new HashSet<>();
            Set<NFAState> setByEdge = getStatesByEpsilonClosure(begin.getStates(),edge);  // 根据上个状态集的nfa集合获得对应边的集合
            for (NFAState stateInSet : setByEdge){
                Set<NFAState> temp = getEpsilonClosureByNFAState(new HashSet<>(),stateInSet);
                beginNext.addAll(temp);
            }
            if (beginNext.isEmpty()){
                begin.getEdges().add(edge);
                begin.getNexts().add(new DFAState(-1,new HashSet<>()));  // -1 代表为空集
            }
            else if ((!isdfaStatesSetContains(dfaStatesSet,beginNext)) && (!beginNext.isEmpty())){
                dfaStatesSet.add(setTranslation(beginNext));
                DFAState dfaBeginNext = new DFAState(count++,beginNext);
                begin.addEdge(edge,dfaBeginNext);
                dfaSet.add(dfaBeginNext);
                state_count++;
            }
            else if (isdfaStatesSetContains(dfaStatesSet,beginNext)){
                begin.addEdge(edge,getDFAStateByStatesInSet(beginNext));
            }

        }
        state_count--;

        if (state_count >= 1){
            constructTable(dfaSet.get(index++),count,state_count);
        }
    }

    /**
     * 根据NFA的终态确定DFA的终态
     * @param nfaEnds
     */
    private void getDFAEnds(ArrayList<NFAState> nfaEnds){
        for (DFAState dfaState : dfaSet){
            for (NFAState nfaState : nfaEnds){
                if (dfaState.getStates().contains(nfaState) && !dfaEnds.contains(dfaState)){
                    endStateSet.add(dfaState);
                    dfaEnds.add(dfaState);
                }else {
                    startStateSet.add(dfaState);
                }
            }
        }
    }

    private void divideStates(List<DFAState> toBeDivided,int index,HashMap<Integer,List<DFAState>> equivalentMap){
        List<DFAState> subjectsStates1 = new ArrayList<>();
        List<DFAState> subjectsStates2 = new ArrayList<>();
        if (toBeDivided.size() > 1){
            DFAState base = toBeDivided.get(0);
            subjectsStates1.add(base);
            for (int i = 1; i < toBeDivided.size(); i++){
                if (isEquivalent(base,toBeDivided.get(i),equivalentMap)){
                    subjectsStates1.add(toBeDivided.get(i));
                }
                else{
                    subjectsStates2.add(toBeDivided.get(i));
                }
            }
            if (equivalentMap.containsValue(subjectsStates1) || equivalentMap.containsValue(subjectsStates2)){
                return;
            }
            equivalentMap.remove(getIndex(base,equivalentMap));
            equivalentMap.put(index++,subjectsStates1);
            equivalentMap.put(index++,subjectsStates2);
        }else{
            return;
        }
        if (subjectsStates1.size() > 1){
            divideStates(subjectsStates1,index,equivalentMap);
        }
        if (subjectsStates2.size() > 1){
            divideStates(subjectsStates2,index,equivalentMap);
        }
    }

    /**
     * 判断两个DFAState是否等价
     * @param state1
     * @param state2
     * @param map
     * @return
     */
    private boolean isEquivalent(DFAState state1, DFAState state2, HashMap<Integer,List<DFAState>> map){
        boolean flag = true;
        for (int i = 0; i < state1.getEdges().size(); i++){
            DFAState next1 = state1.getNexts().get(i);
            DFAState next2 = state2.getNexts().get(i);
            if (getIndex(next1,map)!=getIndex(next2,map)){
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 判断DFAState在哪个状态集中
     * @param state
     * @param map
     * @return
     */
    private int getIndex(DFAState state, HashMap<Integer,List<DFAState>> map){
        for (Map.Entry<Integer,List<DFAState>> entry : map.entrySet()){
            if (entry.getValue().contains(state)){
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * 根据NFAState状态集转换成NFAState中的数字集
     * @param nfaStates
     * @return
     */
    private Set<Integer> setTranslation(Set<NFAState> nfaStates){
        Set<Integer> integers = new HashSet<>();
        for (NFAState nfaState : nfaStates){
            integers.add(nfaState.getState());
        }
        return integers;
    }

    /**
     * 根据NFAState状态集判断它是属于哪个DFA
     * @param set
     * @return
     * @throws DFAStatesException
     */
    private DFAState getDFAStateByStatesInSet(Set<NFAState> set) throws DFAStatesException {
        for (DFAState dfaState : dfaSet){
            if (isSetEqual(setTranslation(set),setTranslation(dfaState.getStates()))){
                return dfaState;
            }
        }
        throw new DFAStatesException();
    }

    /**
     * 判断dfaStatesSet中是否包含某一个 NFAState状态集
     * @param dfaStatesSet
     * @param nfaStates
     * @return
     */
    private boolean isdfaStatesSetContains(Set<Set<Integer>> dfaStatesSet, Set<NFAState> nfaStates){
        for (Set<Integer> integers : dfaStatesSet){
            if (isSetEqual(integers,setTranslation(nfaStates))){
                return true;
            }
        }
        return false;
    }


    /**
     * 判断两个Set中元素是否相同
     * @param set1
     * @param set2
     * @return
     */
    private static boolean isSetEqual(Set set1, Set set2) {

        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        Iterator ite1 = set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
            }
        }

        return isFullEqual;
    }
}

