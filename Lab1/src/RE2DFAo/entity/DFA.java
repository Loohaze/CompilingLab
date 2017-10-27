package RE2DFAo.entity;

import java.util.ArrayList;

public class DFA {

    private DFAState begin;

    private ArrayList<DFAState> ends;

    public DFA(DFAState begin, ArrayList<DFAState> ends) {
        this.begin = begin;
        this.ends = ends;
    }

    public DFAState getBegin() {
        return begin;
    }

    public ArrayList<DFAState> getEnds() {
        return ends;
    }
}
