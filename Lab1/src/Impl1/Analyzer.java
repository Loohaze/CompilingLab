package Impl1;

import java.io.*;

public class Analyzer {

    private static char[] input = new char[500];
    private static int code; // token 中的识别码
    private static int p; // input 数组中指针的位置
    private static int m; // word 数组中指针的位置
    private static long sum; // 记录整数的大小
    private static char[] word; // 记录变量名称
    private static final String inputFile = "input.txt";

    private static String[] keywords = {"public","private","protected","final","void","static","strictfp","abstract","transient","synchronized","volatile","native",
                                 "boolean","int","long","short","byte","float","double","char","class","interface",
                                 "if","else","do","while","for","switch","case","default","break","continue","return","try","catch","finally",
                                 "package","import","throw","throws","extends","implements","this","super","instanceof","new",
                                 "true","false","null","goto","const"};

    private static String[] symbols = {"+=","+","-=","-","*=","*/","*","/=","//","/*","/",">=",">","<=","<","==","=","&&","&","||","|","!=","!","(",")","[","]","{","}",
                                         ",",":",";","\'","\""};


    private void parseInput() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        int index = 0;
        String readline = "";
        while ((readline = br.readLine()) != null){
            char[] chars = readline.toCharArray();
            for (char ch : chars){
                if (ch == ' ' || ch == '\t'){     // 预处理过程中把空格消除
                    continue;
                }
                input[index++] = ch;
            }
            input[index++] = '\n';
        }
        input[index] = '~';

        br.close();
    }

    private void scaner(){
        word = new char[20];
        char ch;
        ch = input[p++];

        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' &&  ch <= 'Z')){   //可能是关键字或者变量名
            m = 0;
            while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                word[m++] = ch;
                ch = input[p++];
                word[m] = '\0';
                for (int i = 0; i < keywords.length; i++){
                    if (char2String(word).equals(keywords[i])){   // 关键字
                        code = i + 1;
                        p--;
                        return;
                    }
                }
            }
            word[m] = '\0';
            p--;
            code = 52;                                             //变量名 code = 52
        }else if (ch >= '0' && ch <= '9'){
            sum = 0;
            while (ch >= '0' && ch <= '9'){
                sum = sum * 10 + (ch - '0');
                ch = input[p++];
            }
            p--;
            code = 53;                                             //整数 code = 53
            if (sum > Integer.MAX_VALUE){
                code = -1;                                          //overflow code = -1
            }
        }else {                                         // 因为各种符号可能存在字符上的包含关系，所以用是否存在在数组中不好判断
            m = 0;
            word[m++] = ch;

            switch (ch){
                case '+':
                    ch = input[p++];
                    if (ch == '='){
                        code = 54;                                 // += code = 54
                        word[m++] = ch;
                    }else {
                        code = 55;                                 // +  code = 55
                        p--;
                    }
                    break;
                case '-':
                    ch = input[p++];
                    if (ch == '='){
                        code = 56;                                 // -= code = 56
                        word[m++] = ch;
                    }else if (ch >= '0' && ch <= '9'){
                        sum = 0;
                        while (ch >= '0' && ch <= '9'){
                            sum = sum * 10 + (ch - '0');
                            ch = input[p++];
                        }
                        p--;
                        code = 53;                                 //整数 code = 53
                        if (sum > Integer.MAX_VALUE){
                            code = -1;
                        }
                        sum = (int)(sum*(-1));
                    }else {                                         // -  code = 57
                        code = 57;
                        p--;
                    }
                    break;
                case '*':
                    ch = input[p++];
                    if (ch == '='){
                        code = 58;
                        word[m++] = ch;                             // *= code = 58
                    }else if (ch == '/'){
                        code = 59;                                 // */ code = 59
                        word[m++] = ch;
                    }else {
                        code = 60;                                 // *  code = 60
                        p--;
                    }
                    break;
                case '/':
                    ch = input[p++];
                    if (ch == '='){
                        code = 61;                                 // /= code = 61
                        word[m++] = ch;
                    }else if (ch == '/'){                           // // code = 62
                        code = 62;
                        word[m++] = ch;
                    }else  if (ch == '*'){                          // /* code = 63
                        code = 63;
                        word[m++] = ch;
                    }else {                                         // / code = 64
                        code = 64;
                        p--;
                    }
                    break;
                case '>':
                    ch = input[p++];
                    if (ch == '='){
                        code = 65;
                        word[m++] = ch;                             // >= code = 65
                    }else {
                        code = 66;                                 // > code = 66
                        p--;
                    }
                    break;
                case '<':
                    ch = input[p++];
                    if (ch == '='){
                        code = 67;                                 // <= code = 67
                        word[m++] = ch;
                    }else {
                        code = 68;                                 // < code = 68
                        p--;
                    }
                    break;
                case '=':
                    ch = input[p++];
                    if (ch == '='){
                        code = 69;                                 // == code = 69
                        word[m++] = ch;
                    }else {
                        code = 70;
                        p--;                                        // = code = 70
                    }
                    break;
                case '&':
                    ch = input[p++];
                    if (ch == '&'){
                        code = 71;                                 // && code = 71
                        word[m++] = ch;
                    }else {
                        code = 72;                                 //  & code = 72
                        p--;
                    }
                    break;
                case '|':
                    ch = input[p++];
                    if (ch == '|'){
                        code = 73;                                 // \\ code = 73
                        word[m++] = ch;
                    }else {
                        code = 74;                                 //  | code = 74
                        p--;
                    }
                    break;
                case '!':
                    ch = input[p++];
                    if (ch == '='){
                        code = 75;                                 // != code = 75
                        word[m++] = ch;
                    }else {
                        code = 76;                                 //  ! code = 76
                        p--;
                    }
                    break;
                case '(':
                    code = 77;break;
                case ')':
                    code = 78;break;
                case '[':
                    code = 79;break;
                case ']':
                    code = 80;break;
                case '{':
                    code = 81;break;
                case '}':
                    code = 82;break;
                case ',':
                    code = 83;break;
                case ':':
                    code = 84;break;
                case ';':
                    code = 85;break;
                case '\'':
                    code = 86;break;
                case '\"':
                    code = 87;break;
                case '\n':
                    code = -2;break;
                default:
                    code = -3;break;
            }
        }
    }

    private void output() throws IOException {
//        File outputFile = new File(inputFile.split(".")[0]+".output");
//        if (!outputFile.exists()){
//            outputFile.createNewFile();
//        }
//        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile,false));

    }

    private static String char2String(char[] chars){
        String str = "";
        for (char c : chars){
            if (c != '\0'){                             //去除数组中空位
                str += String.valueOf(c);
            }
        }
        return str;
    }

    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        try {
            analyzer.parseInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        do{
            analyzer.scaner();
            switch (code){
                case 53:
                    System.out.println(new Token(code,String.valueOf(sum)));
                    break;
                case -1:
                    System.out.println(new Token("integer overflow "));
                    break;
                case -2:
                    break;
                case -3:
                    System.out.println(new Token("undefined character "));
                    break;
                default:
                    System.out.println(new Token(code,char2String(word)));
                    break;
            }
        }while (input[p] != '~');
    }
}
