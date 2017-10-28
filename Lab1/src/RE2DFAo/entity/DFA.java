package RE2DFAo.entity;

import java.util.ArrayList;
import java.util.Set;

public class DFA {

    private DFAState begin;

    private ArrayList<DFAState> ends;

    private Set<String> edges;

    private Set<Set<Integer>> dfaStatesSet;


    public DFA(DFAState begin, ArrayList<DFAState> ends, Set<String> edges, Set<Set<Integer>> dfaStatesSet) {
        this.begin = begin;
        this.ends = ends;
        this.edges = edges;
        this.dfaStatesSet = dfaStatesSet;
    }

    public Set<String> getEdges() {
        return edges;
    }

    public Set<Set<Integer>> getDfaStatesSet() {
        return dfaStatesSet;
    }

    public DFAState getBegin() {
        return begin;
    }

    public ArrayList<DFAState> getEnds() {
        return ends;
    }
}
