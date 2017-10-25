package Impl1;

public class Token {

    private int code;
    private String msg;
    private String error;

    public Token(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Token() {
    }

    public Token(String error) {
        this.error = error;
    }

    public String toString(){
        if (this.error != null){
            return "Error:" + this.error;
        }else {
            return "<" + this.code + "," + this.msg + ">";
        }
    }
}
