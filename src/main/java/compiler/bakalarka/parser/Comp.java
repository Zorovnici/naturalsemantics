package compiler.bakalarka.parser;

import compiler.bakalarka.tokenTypes;
import java.util.LinkedList;


public class Comp {
    boolean isOK;
    boolean con;
    private LinkedList compareList = new LinkedList();
    private LinkedList doWHILEcode = new LinkedList();
    private LinkedList doWHILEtokens = new LinkedList();
    private LinkedList conditionInTree = new LinkedList();
    boolean NEG = false;
    private Expr expr;
    int MUL;
    int firstvalue = 0;
    int result;
    public boolean comp(LinkedList tokens, LinkedList code, VariableClass variableClass, boolean isWhile){
        int valueI = 0;
        int valueII = 0;
        String typeComp = "null";
        if (tokens.getFirst().equals(tokenTypes.NEG)){
            NEG = true;
            condtionTreeSet(conditionInTree,code.getFirst());
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }
        if (tokens.getFirst().equals(tokenTypes.NUM)){
            valueI = Integer.parseInt(code.getFirst().toString());
            //   System.out.println("number 1 " + valueI);
        } else if (tokens.getFirst().equals(tokenTypes.VAR)){
            for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                    valueI = variableClass.getVariableListFinal().get(i).variableValue;
                }
            }
        }
        condtionTreeSet(conditionInTree,code.getFirst());
        saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
        removeToken(tokens,code);
        if (!tokens.isEmpty() && (tokens.getFirst().equals(tokenTypes.PLUS) || tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.MUL))){
            valueI = parsingExpr(tokens,code,code.getFirst().toString(), valueI , variableClass, false);
            System.out.println("hodnota valueI je: " + valueI);
        }
        if (tokens.getFirst().equals(tokenTypes.ASSIGN)){
            typeComp = tokenTypes.ASSIGN.toString();
            //  System.out.println("ASSIGN");
        }else if (tokens.getFirst().equals(tokenTypes.LE)){
            typeComp = tokenTypes.LE.toString();
            //   System.out.println("LE");
        }
        condtionTreeSet(conditionInTree,code.getFirst());
        saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
        removeToken(tokens,code);
        if (tokens.getFirst().equals(tokenTypes.NUM)){
            valueII = Integer.parseInt(code.getFirst().toString());
            //   System.out.println("number 1 " + valueI);
        } else if (tokens.getFirst().equals(tokenTypes.VAR)){
            for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                    valueII = variableClass.getVariableListFinal().get(i).variableValue;
                }
            }
        }
        condtionTreeSet(conditionInTree,code.getFirst());
        saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
        removeToken(tokens,code);
        if (!tokens.isEmpty() && (tokens.getFirst().equals(tokenTypes.PLUS) || tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.MUL))){
            valueII = parsingExpr(tokens,code,code.getFirst().toString(), valueII , variableClass, false);
            System.out.println("hodnota valueI je: " + valueII);
        }
        if (typeComp.equals(tokenTypes.ASSIGN.toString())){
            if (valueI == valueII){
                if (!NEG)
                    isOK = true;
                if (NEG)
                    isOK = false;
                //   System.out.println("rovna sa");
                compareList.add(isOK);
            } else {
                if (!NEG)
                    isOK = false;
                if (NEG)
                    isOK = true;
                //   System.out.println("nerovna sa");
                compareList.add(isOK);
            }
        } else if (typeComp.equals(tokenTypes.LE.toString())){
            if (valueI <= valueII){
                isOK = true;
                //  System.out.println("je menšie-rovne");
                compareList.add(isOK);
            } else {
                isOK = false;
                //    System.out.println("je ine");
                compareList.add(isOK);
            }
        }
        NEG = false;
        if (tokens.getFirst().equals(tokenTypes.CON)){
            con = true;
            //   System.out.println("AND" + isOK);
            condtionTreeSet(conditionInTree,code.getFirst());
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
           // System.out.println(tokens.getFirst() + "ideme druhy krat ");
            comp(tokens,code,variableClass, isWhile);
        }
      //  System.out.println(isOK);
        for (int i=0;i<compareList.size();i++){
            if (compareList.get(i).equals(false)){
                isOK = false;
                break;
            }
        }
        return isOK;

    }

    public boolean isOK() {
        return isOK;
    }

    public LinkedList getDoWHILEcode() {
        return doWHILEcode;
    }

    public LinkedList getDoWHILEtokens() {
        return doWHILEtokens;
    }

    public int parsingExpr(LinkedList tokens, LinkedList code, String type, int value , VariableClass variableClass, boolean whileIS){
            doWHILEcode.clear();
            doWHILEtokens.clear();
        while(!tokens.getFirst().equals(tokenTypes.SEMCOL)){
            if (tokens.getFirst().equals(tokenTypes.PLUS)){
                System.out.println("PLUS");
                type = code.getFirst().toString();
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }else{
                // System.out.println("Missing PLUS token !!!");
            }
            if (tokens.getFirst().equals(tokenTypes.MINUS)){
                System.out.println("MINUS");
                type = code.getFirst().toString();
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }else{
                // System.out.println("Missing MINUS token !!!");
            }
            if (tokens.getFirst().equals(tokenTypes.MUL)){
                System.out.println("MUL");
                type = code.getFirst().toString();
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }else{
                // System.out.println("Missing MUL token !!!");
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("+")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    firstvalue = Integer.parseInt(code.getFirst().toString());
                    //   System.out.println(value);
                    condtionTreeSet(conditionInTree,code.getFirst());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    removeToken(tokens,code);
                    if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                        value = value + firstvalue;
                        //  System.out.println(value);
                    }
                    if (tokens.getFirst().equals(tokenTypes.MUL)){
                        value = value + MUL(tokens,code,firstvalue, variableClass, whileIS);
                        //   System.out.println(value);
                    }
                }
                if (tokens.getFirst().equals(tokenTypes.VAR)){
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            firstvalue = variableClass.getVariableListFinal().get(i).variableValue;
                            //    System.out.println(value + " value in *");
                            condtionTreeSet(conditionInTree,code.getFirst());
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            removeToken(tokens,code);
                            if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                                value = value + firstvalue;
                                //    System.out.println(value);
                            }
                            if (tokens.getFirst().equals(tokenTypes.MUL)){
                                value = value + MUL(tokens,code,firstvalue, variableClass,whileIS);
                                //    System.out.println(value);
                            }
                        }

                    }

                    // System.out.println("VAR");
                }
                type = "null";
            }else{
                //System.out.println("Missing NUM or VAR token + !!!");
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("-")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    firstvalue = Integer.parseInt(code.getFirst().toString());
                    //  System.out.println(value);
                    condtionTreeSet(conditionInTree,code.getFirst());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    removeToken(tokens,code);
                    if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                        value = value - firstvalue;
                        //  System.out.println(value);
                    }
                    if (tokens.getFirst().equals(tokenTypes.MUL)){
                        value = value - MUL(tokens,code,firstvalue,variableClass, whileIS);
                        //   System.out.println(value);
                    }
                }
                if (tokens.getFirst().equals(tokenTypes.VAR)){
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            firstvalue = variableClass.getVariableListFinal().get(i).variableValue;
                            condtionTreeSet(conditionInTree,code.getFirst());
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            removeToken(tokens,code);
                            if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                                value = value - firstvalue;
                                //   System.out.println(value);
                            }
                            if (tokens.getFirst().equals(tokenTypes.MUL)){
                                value = value - MUL(tokens,code,firstvalue, variableClass, whileIS);
                                // System.out.println(value);
                            }
                        }

                    }
                }
                type = "null";
            }else{
                //  System.out.println("Missing NUM or VAR token EXPR - !!!");
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("*")){
                System.out.println("MUL ideme urobit");
                value = MUL(tokens,code,value, variableClass, whileIS);
                type = "null";
            }else{
                // System.out.println("Missing NUM or VAR token EXPR * !!!");
            }
            if (tokens.getFirst().equals(tokenTypes.RPAR) || tokens.getFirst().equals(tokenTypes.ASSIGN) || tokens.getFirst().equals(tokenTypes.CON)){
                result = value;
                break;
            }
        }
        return result;
    }
    public int MUL(LinkedList tokens, LinkedList code, int firstValue, VariableClass variableClass, boolean whileIS){
        boolean first = true;
        while(!tokens.getFirst().equals(tokenTypes.PLUS)  || !tokens.getFirst().equals(tokenTypes.MINUS) || !tokens.getFirst().equals(tokenTypes.RPAR) || !tokens.getFirst().equals(tokenTypes.ASSIGN) || !tokens.getFirst().equals(tokenTypes.LE) || !tokens.getFirst().equals(tokenTypes.CON)) {
            if (tokens.getFirst().equals(tokenTypes.MUL)) {
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.NUM) && first) {
                MUL = firstValue * Integer.parseInt(code.getFirst().toString());
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
                first = false;
            }
            if (tokens.getFirst().equals(tokenTypes.NUM) && !first) {
                MUL = MUL * Integer.parseInt(code.getFirst().toString());
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.VAR) && first){
                for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                    if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                        MUL = firstValue * variableClass.getVariableListFinal().get(i).variableValue;
                    }
                }
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
                first = false;
            }
            if (tokens.getFirst().equals(tokenTypes.VAR) && !first){
                for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                    if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                        MUL = MUL * variableClass.getVariableListFinal().get(i).variableValue;
                    }
                }
                condtionTreeSet(conditionInTree,code.getFirst());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.PLUS)  || tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.RPAR) || tokens.getFirst().equals(tokenTypes.ASSIGN) || tokens.getFirst().equals(tokenTypes.LE) || tokens.getFirst().equals(tokenTypes.CON)){
                break;
            }
        }
        return MUL;
    }

    public void removeToken(LinkedList token, LinkedList code){
        token.remove();
        code.remove();
    }

    public void saveWhileToken(boolean isWhile, LinkedList code, LinkedList tokens, LinkedList whileTokens, LinkedList whileCode){
        if (isWhile) {
            whileTokens.add(tokens.getFirst());
            whileCode.add(code.getFirst());
        }
    }

    public void condtionTreeSet(LinkedList conditionInTree, Object code){
        conditionInTree.add(code);
    }

    public LinkedList getConditionInTree() {
        return conditionInTree;
    }
}
