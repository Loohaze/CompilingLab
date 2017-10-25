package RE2DFAo;

import java.util.LinkedList;
import java.util.List;

public class NFAState {

    private int state;

    private List<String> edges;

    private List<NFAState> nexts;

    public NFAState() {
        this.edges = new LinkedList<>();
        this.nexts = new LinkedList<>();
    }

    public NFAState(int state) {
        this.state = state;
        this.edges = new LinkedList<>();
        this.nexts = new LinkedList<>();
    }


    public void addEdge(String edge, NFAState next){
        edges.add(edge);
        nexts.add(next);
    }

    public int getState() {
        return state;
    }

    public List<String> getEdges() {
        return edges;
    }

    public List<NFAState> getNexts() {
        return nexts;
    }

    public void setState(int state) {
        this.state = state;
    }



}


