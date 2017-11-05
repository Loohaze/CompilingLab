package RE2DFAo;

import RE2DFAo.Exception.DFAStatesException;
import RE2DFAo.entity.DFA;
import RE2DFAo.entity.DFAState;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public HashMap<String,DFA> map;
    private static final String reFile = "impl2sources/re.txt";
    private static final String inputFile = "impl2sources/input.txt";
    private static final String outputFIle = "impl2sources/output.txt";

    public void readREs() throws IOException, DFAStatesException {
        map = new HashMap<>();
        BufferedReader bf = new BufferedReader(new FileReader(reFile));
        String line = "";
        while ((line = bf.readLine()) != null){
            String token = line.split("\\s+")[0];
            String re = RENormalize.getRENormalize(line.split("\\s+")[1]).getRegularExpression();
            DFA dfa = NFA2DFA.getNfa2DFA().nfa2dfa(RE2NFA.getRE2NFA().re2nfa(re));
            map.put(token,dfa);
        }
    }

    public ArrayList<String> readInputFile() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String line = "";
        while ((line = bf.readLine()) != null){
            String[] datas = line.split("\\s+");
            for (String data : datas){
                list.add(data);
            }
        }
        return list;
    }

    public String getToken(String input){
        if (isMatch(input,map.get("DataType"))){
            return "DataType";
        }else if (isMatch(input,map.get("Keyword"))){
            return "Keyword";
        }else if (isMatch(input,map.get("Number"))){
            return "Number";
        }else if (isMatch(input,map.get("String"))){
            return "String";
        }else if (isMatch(input,map.get("Identity"))){
            return "Identity";
        }else if (isMatch(input,map.get("Character"))){
            return "Character";
        }else if (isMatch(input,map.get("Operator"))){
            return "Operator";
        }else if (isMatch(input,map.get("Other"))){
            return "Other";
        }
        return "ERROR";
    }

    public boolean isMatch(String input,DFA dfa){

        DFAState state = dfa.getBegin();
        List<Integer> list = new ArrayList<>();
        for (DFAState dfaState : dfa.getEnds()){
            list.add(dfaState.getState());
        }

        for (int i = 0; i < input.length(); i++){
            for (int j = 0; j < state.getEdges().size(); j++){
                if (String.valueOf(input.charAt(i)).equals(state.getEdges().get(j))){
                    if (i == input.length() -1 && input.length() != 1){
                        if (state.getNexts().get(j).getState() == -1){
                            return true;
                        }
                    }
                    if (state.getNexts().get(j).getState() == -1){
                        return false;
                    }else {
                        state = state.getNexts().get(j);
                        break;
                    }
                }else{
                    continue;
                }
            }
        }

        return list.contains(state.getState());
    }

    public static void main(String[] args) throws IOException, DFAStatesException {
        Main main = new Main();
        main.readREs();
        ArrayList<String> input = main.readInputFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFIle));
        for (String data : input){
            if (!data.equals("")){
                bw.write("<" + main.getToken(data) + " : " + data + ">" + "\n");
                bw.flush();
            }
        }
    }
}
