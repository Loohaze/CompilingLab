package RE2DFAo.entity;

import java.util.*;

public class DFAState {

    private int state;

    private Set<NFAState> states;

    private List<String> edges;

    private List<DFAState> nexts;

    public DFAState(int state, Set<NFAState> states) {
        this.state = state;
        this.states = states;
        this.edges = new LinkedList<>();
        this.nexts = new LinkedList<>();
    }

    public DFAState() {
        this.states = new HashSet<>();
        this.edges = new LinkedList<>();
        this.nexts = new LinkedList<>();
    }

    public void addEdge(String edge,DFAState next){
        edges.add(edge);
        nexts.add(next);
    }

    public void setEdges(List<String> edges) {
        this.edges = edges;
    }

    public void setNexts(List<DFAState> nexts) {
        this.nexts = nexts;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Set<NFAState> getStates() {
        return states;
    }

    public List<String> getEdges() {
        return edges;
    }

    public List<DFAState> getNexts() {
        return nexts;
    }

    public void setStates(Set<NFAState> states) {
        this.states = states;
    }
}
