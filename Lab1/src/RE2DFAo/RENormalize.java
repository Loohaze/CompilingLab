package RE2DFAo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RENormalize {

    private static RENormalize reNormalize;

    private String regularExpression;

    private static final ArrayList<Character> OPERATION_PRIORITY = new ArrayList<>(Arrays.asList('|','.','*'));


    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public String getRegularExpression() {
        return infix2postfix();
    }

    private RENormalize(String re) {
        this.regularExpression = re;
    }

    public static RENormalize getRENormalize(String re) {
        if (reNormalize == null){
            return new RENormalize(re);
        }
        return reNormalize;
    }

    private String addConnectDot(){
        StringBuffer sb  = new StringBuffer();
        List<Character> afterCH = new ArrayList<>(Arrays.asList('(','|','.'));  // afterCH中的符号后无连接符
        List<Character> beforeCH = new ArrayList<>(Arrays.asList('|','*','.',')')); // beforeCH中的符号前无连接符
        char[] chars = regularExpression.toCharArray();
        for (int i = 0; i < chars.length; i++){
            if (afterCH.contains(chars[i])){
                sb.append(chars[i]);
                continue;
            }
            if (i == chars.length-1 || beforeCH.contains(chars[i+1])){
                sb.append(chars[i]);
            }else {
                sb.append(chars[i]).append('.');
            }
        }
        return sb.toString();
    }


    /**
     * 遇到操作符，入栈，在入栈前若该操作符优先级较低或与栈中优先级相同则将栈中操作符弹出，输出。
     * 遇到左括号入栈，直到遇到右括号，将左括号之前的操作符弹出，输出。弹出左括号。
     * @return
     */
    private String infix2postfix(){
        String infix = regularExpression = addConnectDot() + "#";
        StringBuffer sb = new StringBuffer();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < infix.length(); i++){
            char c = infix.charAt(i);
            switch (c){
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    while (!stack.isEmpty() && stack.peek() != '('){
                        sb.append(stack.pop());
                    }
                    stack.pop();
                    break;
                case '#':
                    while (!stack.isEmpty()){
                        sb.append(stack.pop());
                    }
                    break;
                default:
                    if (OPERATION_PRIORITY.contains(c)){
                        while(!stack.isEmpty() && OPERATION_PRIORITY.indexOf(stack.peek()) >= OPERATION_PRIORITY.indexOf(c)){
                            sb.append(stack.pop());
                        }
                        stack.push(c);
                    }else {   // 非符号
                        sb.append(c);
                    }
                    break;
            }
        }

        return sb.toString();
    }


}
