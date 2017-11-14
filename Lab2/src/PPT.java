import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存产生式右部 以从右向左方向保存 用于压栈
 */
public class PPT {

    private Map<Integer,List<Token>> PPT = new HashMap<>();

    public PPT() {
        List<Token> production0 = new ArrayList<>();
        production0.add(new Token(Token.SEMICOLON,";"));
        production0.add(new Token(Token.E,"E"));
        production0.add(new Token(Token.EQUAL,"="));
        production0.add(new Token(Token.ID,"id"));
        PPT.put(0,production0);

        List<Token> production1 = new ArrayList<>();
        production1.add(new Token(Token.RIGHT_BRACE,"}"));
        production1.add(new Token(Token.S,"S"));
        production1.add(new Token(Token.LEFT_BRACE,"{"));
        production1.add(new Token(Token.ELSE,"else"));
        production1.add(new Token(Token.RIGHT_BRACE,"}"));
        production1.add(new Token(Token.S,"S"));
        production1.add(new Token(Token.LEFT_BRACE,"{"));
        production1.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production1.add(new Token(Token.C,"C"));
        production1.add(new Token(Token.LEFT_PARENTHESE,"("));
        production1.add(new Token(Token.IF,"if"));
        PPT.put(1,production1);

        List<Token> production2 = new ArrayList<>();
        production2.add(new Token(Token.RIGHT_BRACE,"}"));
        production2.add(new Token(Token.S,"S"));
        production2.add(new Token(Token.LEFT_BRACE,"{"));
        production2.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production2.add(new Token(Token.C,"C"));
        production2.add(new Token(Token.LEFT_PARENTHESE,"("));
        production2.add(new Token(Token.WHILE,"while"));
        PPT.put(2,production2);

        List<Token> production3 = new ArrayList<>();
        production3.add(new Token(Token.E1,"E'"));
        production3.add(new Token(Token.T,"T"));
        PPT.put(3,production3);

        List<Token> production4 = new ArrayList<>();
        production4.add(new Token(Token.E1,"E'"));
        production4.add(new Token(Token.T,"T"));
        production4.add(new Token(Token.ADD,"+"));
        PPT.put(4,production4);

        List<Token> production5 = new ArrayList<>();
        PPT.put(5,production5);

        List<Token> production6 = new ArrayList<>();
        production6.add(new Token(Token.T1,"T'"));
        production6.add(new Token(Token.F,"F"));
        PPT.put(6,production6);

        List<Token> production7 = new ArrayList<>();
        production7.add(new Token(Token.T1,"T'"));
        production7.add(new Token(Token.F,"F"));
        production7.add(new Token(Token.MUL,"*"));
        PPT.put(7,production7);

        List<Token> production8 = new ArrayList<>();
        PPT.put(8,production8);

        List<Token> production9 = new ArrayList<>();
        production9.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production9.add(new Token(Token.E,"E"));
        production9.add(new Token(Token.LEFT_PARENTHESE,"("));
        PPT.put(9,production9);

        List<Token> production10 = new ArrayList<>();
        production10.add(new Token(Token.NUMBER,"num"));
        PPT.put(10,production10);

        List<Token> production11 = new ArrayList<>();
        production11.add(new Token(Token.ID,"id"));
        PPT.put(11,production11);

        List<Token> production12 = new ArrayList<>();
        production12.add(new Token(Token.C1,"C'"));
        production12.add(new Token(Token.D,"D"));
        PPT.put(12,production12);

        List<Token> production13 = new ArrayList<>();
        production13.add(new Token(Token.C1,"C'"));
        production13.add(new Token(Token.D,"D"));
        production13.add(new Token(Token.DOUBLE_OR,"||"));
        PPT.put(13,production13);

        List<Token> production14 = new ArrayList<>();
        PPT.put(14,production14);

        List<Token> production15 = new ArrayList<>();
        production15.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production15.add(new Token(Token.C,"C"));
        production15.add(new Token(Token.LEFT_PARENTHESE,"("));
        PPT.put(15,production15);

        List<Token> production16 = new ArrayList<>();
        production16.add(new Token(Token.NUMBER,"num"));
        production16.add(new Token(Token.DOUBLE_EQUAL,"=="));
        production16.add(new Token(Token.ID,"id"));
        PPT.put(16,production16);

    }


    public Map<Integer, List<Token>> getPPT() {
        return PPT;
    }

    public void setPPT(Map<Integer, List<Token>> PPT) {
        this.PPT = PPT;
    }

    public List<Token> getPPTElement(int num){
        return this.PPT.get(num);
    }

    /**
     * 根据输入流读头所读非终结符Token的种别码 返回对应列号
     * @param code
     * @return
     */
    public int getHeadIndex(int code){
        switch (code){
            case 56: return 0;
            case 30: return 1;
            case 53: return 2;
            case 12: return 3;
            case 45: return 4;
            case 46: return 5;
            case 49: return 6;
            case 50: return 7;
            case 13: return 8;
            case 15: return 9;
            case 22: return 10;
            case 26: return 11;
            case 57: return 12;
            case 35: return 13;
            case 31: return 14;
            case 0: return 15;
            default: return -1;
        }
    }
}