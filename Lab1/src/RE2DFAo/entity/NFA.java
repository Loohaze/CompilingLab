package RE2DFAo.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NFA {

    private NFAState begin;

    private ArrayList<NFAState> ends;

    private Set<String> states;


    public NFA(NFAState begin, ArrayList<NFAState> ends,HashSet<String> states) {
        this.begin = begin;
        this.ends = ends;
        this.states = states;
    }

    public NFAState getBegin() {
        return begin;
    }

    public ArrayList<NFAState> getEnds() {
        return ends;
    }

    public Set<String> getStates() {
        return states;
    }

    public void setBegin(NFAState begin) {
        this.begin = begin;
    }


}
