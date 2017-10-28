package RE2DFAo.Exception;

public class DFAStatesException extends Exception {

    public DFAStatesException() {
        super("应该找到已存在的DFA状态集，但未找到");
    }

}
