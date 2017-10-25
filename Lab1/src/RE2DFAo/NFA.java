package RE2DFAo;

import java.util.ArrayList;

public class NFA {

    public NFAState begin;

    public ArrayList<NFAState> ends;

    public NFA(NFAState begin, ArrayList<NFAState> ends) {
        this.begin = begin;
        this.ends = ends;
    }
}
