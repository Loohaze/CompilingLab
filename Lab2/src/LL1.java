import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class LL1 {

    private static String[] generations = {
            // 自定义文法
            "S->id=E;",
            "S->if(C){S}else{S}",
            "S->while(C){S}",
            "E->TE'",
            "E'->+TE'",
            "E'->ε",
            "T->FT'",
            "T'->*FT'",
            "T'->ε",
            "F->(E)",
            "F->num",
            "F->id",
            "C->DC'",
            "C'->||DC'",
            "C'->ε",
            "D->(C)",
            "D->id==num"
    };
    private static ArrayList<Token> tokens;
    private static ArrayList<String> output = new ArrayList<>();
    private static Stack<Token> stack;
    private static int[][] table = {
            //   id	=	;	if	(	)	{	}	e	w	+	*	n	||	==	$
            {0,	-1,	-1,	1,	-1,	-1,	-1,	-1,	-1,	2,	-1,	-1,	-1,	-1,	-1,	-1},//S
            {3,	-1,	-1,	-1,	3,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	3,	-1,	-1,	-1},//E
            {-1,-1,	5,	-1,	-1,	5,	-1,	-1,	-1,	-1,	4,	-1,	-1,	-1,	-1,	5},//E'
            {6,	-1,	-1,	-1,	6,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	6,	-1,	-1,	-1},//T
            {-1,-1,	8,	-1,	-1,	8,	-1,	-1,	-1,	-1,	8,	7,	-1,	-1,	-1,	8},//T'
            {11,-1,	-1,	-1,	9,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	10,	-1,	-1,	-1},//F
            {12,-1,	-1,	-1,	12,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1},//C
            {-1,-1,	-1,	-1,	-1,	14,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	13,	-1,	14},//C'
            {16,-1,	-1,	-1,	15,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1}//D
    };

    private static void parse(){
        Token top = null; // 栈顶
        Token readHead = null; // 读头

        stack = new Stack<>();
        stack.push(new Token(Token.S,"S"));
        while(tokens.get(0).getCode() != Token.END){
            top = stack.peek();
            readHead = tokens.get(0);
            if (top.getCode() > 99){ // Vn
                if (!shift(top,readHead.getCode())){
                    System.out.println("没有对应文法");
                    return;
                }
            }else {
                if (top.getCode() == readHead.getCode()){
                    stack.pop();
                    tokens.remove(0);
                    if (tokens.size() == 1){
                        break;
                    }
                }
                else{
                    System.out.println("终结符不匹配");
                    return;
                }
            }
        }

        System.out.println("文法分析完成");
    }


    private static boolean shift(Token token,int vtCode){
        PPT ppt = new PPT();
        int shift = table[token.getCode()-100][ppt.getHeadIndex(vtCode)];
        if (shift < 0){
            System.out.println("未定义该种表达式");
            return false;
        }
        output.add(generations[shift]);
        stack.pop();
        return pushProduce(shift);
    }

    private static boolean pushProduce(int code){
        PPT ppt = new PPT();
        if (ppt.getPPTElement(code) == null){
            return false;
        }
        if (ppt.getPPTElement(code).size() > 0){
            for (Token t : ppt.getPPTElement(code)){
                stack.push(t);
            }
        }
        return true;
    }

    private static void output(){
        try {
            File outputFile = new File("output");
            if (!outputFile.exists())
                outputFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
            for(String s:output){
                System.out.println(s);
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        tokens = Analyzer.getTokens();
        parse();
        output();
    }
}
