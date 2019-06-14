package compiler.bakalarka.parser;


import compiler.bakalarka.tokenTypes;

import java.util.LinkedList;


public class Expr {
    int resultMUL = 0;
    int parenthesisResult = 0;
    int result;
    String type = "null";
    int mulvalueFirst = 0;
    private boolean isWhile = false;
    private LinkedList doWHILEcode = new LinkedList();
    private LinkedList doWHILEtokens = new LinkedList();
    private LinkedList latexExpr = new LinkedList();

    public void parsingExpr(LinkedList tokens, LinkedList code, String type, int value , VariableClass variableClass, boolean whileIS){
        doWHILEcode.clear();
        doWHILEtokens.clear();
        while(!tokens.getFirst().equals(tokenTypes.SEMCOL)){
        if (tokens.getFirst().equals(tokenTypes.PLUS)){
            type = code.getFirst().toString();
            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
            latexExpr.add(code.getFirst());
            removeToken(tokens,code);
            if (tokens.getFirst().equals(tokenTypes.LPAR)) {
                value = value + exprParenthesis(tokens, code, value, variableClass, whileIS);
            }
        }
            if (tokens.getFirst().equals(tokenTypes.MINUS)){
                type = code.getFirst().toString();
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
                if (tokens.getFirst().equals(tokenTypes.LPAR)) {
                    value = value - exprParenthesis(tokens, code, value, variableClass, whileIS);
                }
            }
            if (tokens.getFirst().equals(tokenTypes.MUL)){
                type = code.getFirst().toString();
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
                if (tokens.getFirst().equals(tokenTypes.LPAR)) {
                    value = value * exprParenthesis(tokens, code, value, variableClass, whileIS);
                }
            }
        if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("+")){
            if (tokens.getFirst().equals(tokenTypes.NUM)) {
                mulvalueFirst = Integer.parseInt(code.getFirst().toString());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
                if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                    value = value + mulvalueFirst;
                     System.out.println(value);
                }
                if (tokens.getFirst().equals(tokenTypes.MUL)) {
                    value = value + MUL(tokens, code, mulvalueFirst, variableClass, whileIS);
                }

            }
            if (tokens.getFirst().equals(tokenTypes.VAR)){
                for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                    if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                        mulvalueFirst = variableClass.getVariableListFinal().get(i).variableValue;
                        saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                        latexExpr.add(code.getFirst());
                        removeToken(tokens,code);
                        if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                            value = value + mulvalueFirst;
                        }
                        if (tokens.getFirst().equals(tokenTypes.MUL)){
                            value = value + MUL(tokens,code,mulvalueFirst, variableClass,whileIS);
                        }
                    }
                }
            }
            type = "null";
        }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("-")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    mulvalueFirst = Integer.parseInt(code.getFirst().toString());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    latexExpr.add(code.getFirst());
                    removeToken(tokens,code);
                    if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                        value = value - mulvalueFirst;
                    }
                    if (tokens.getFirst().equals(tokenTypes.MUL)){
                        value = value - MUL(tokens,code,mulvalueFirst,variableClass, whileIS);
                    }
                }
                if (tokens.getFirst().equals(tokenTypes.VAR)){
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            mulvalueFirst = variableClass.getVariableListFinal().get(i).variableValue;
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            latexExpr.add(code.getFirst());
                            removeToken(tokens,code);
                            if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                                value = value - mulvalueFirst;
                            }
                            if (tokens.getFirst().equals(tokenTypes.MUL)){
                                value = value - MUL(tokens,code,mulvalueFirst, variableClass, whileIS);
                            }
                        }
                    }
                }
                type = "null";
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("*")){
                    value = MUL(tokens,code,value, variableClass, whileIS);
                type = "null";
            }
            if (tokens.getFirst().equals(tokenTypes.SEMCOL)){
                result = value;
                break;
            }
    }
        if (tokens.getFirst().equals(tokenTypes.SEMCOL)){
            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
            latexExpr.add(code.getFirst());
            removeToken(tokens,code);
        }
    }
    public int getResult() {
        return result;
    }
    public int MUL(LinkedList tokens, LinkedList code, int firstValue, VariableClass variableClass, boolean whileIS){
        boolean first = true;
        while(!tokens.getFirst().equals(tokenTypes.SEMCOL) || !tokens.getFirst().equals(tokenTypes.PLUS) || tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.RPAR)) {
            if (tokens.getFirst().equals(tokenTypes.MUL)) {
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.LPAR) && first){
                resultMUL = firstValue * exprParenthesis(tokens, code, firstValue, variableClass, whileIS);
            }
            if (tokens.getFirst().equals(tokenTypes.NUM) && first) {
                resultMUL = firstValue * Integer.parseInt(code.getFirst().toString());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
                first = false;
            }
            if (tokens.getFirst().equals(tokenTypes.LPAR) && !first){
                resultMUL = resultMUL * exprParenthesis(tokens, code, firstValue, variableClass, whileIS);
            }
            if (tokens.getFirst().equals(tokenTypes.NUM) && !first) {
                resultMUL = resultMUL * Integer.parseInt(code.getFirst().toString());
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.VAR) && first){
                for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                    if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                        resultMUL = firstValue * variableClass.getVariableListFinal().get(i).variableValue;
                    }
                }
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
                first = false;
            }
            if (tokens.getFirst().equals(tokenTypes.VAR) && !first){
                for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                    if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                        resultMUL = resultMUL * variableClass.getVariableListFinal().get(i).variableValue;
                    }
                }
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.PLUS) || tokens.getFirst().equals(tokenTypes.SEMCOL) || tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.RPAR)){
                break;
            }
        }
        return resultMUL;
    }
    public int exprParenthesis(LinkedList tokens, LinkedList code, int firstValue, VariableClass variableClass, boolean whileIS) {
        while(!tokens.isEmpty()) {
            if (tokens.getFirst().equals(tokenTypes.LPAR)) {
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.PLUS)){
                type = code.getFirst().toString();
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.MINUS)){
                type = code.getFirst().toString();
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.MUL)){
                type = code.getFirst().toString();
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.RPAR)){
                saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                latexExpr.add(code.getFirst());
                removeToken(tokens,code);
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("+")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    mulvalueFirst = Integer.parseInt(code.getFirst().toString());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    latexExpr.add(code.getFirst());
                    removeToken(tokens,code);
                    if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                        parenthesisResult = parenthesisResult + mulvalueFirst;
                    }
                    if (tokens.getFirst().equals(tokenTypes.MUL)) {
                        parenthesisResult = parenthesisResult + MUL(tokens, code, mulvalueFirst, variableClass, whileIS);
                    }
                    if (tokens.getFirst().equals(tokenTypes.LPAR)){
                        parenthesisResult = parenthesisResult + exprParenthesis(tokens, code, firstValue, variableClass, whileIS);
                    }

                }
                if (tokens.getFirst().equals(tokenTypes.VAR)){
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            mulvalueFirst = variableClass.getVariableListFinal().get(i).variableValue;
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            latexExpr.add(code.getFirst());
                            removeToken(tokens,code);
                            if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                                parenthesisResult = parenthesisResult + mulvalueFirst;
                            }
                            if (tokens.getFirst().equals(tokenTypes.MUL)){
                                parenthesisResult = parenthesisResult + MUL(tokens,code,mulvalueFirst, variableClass,whileIS);
                            }
                        }
                    }
                }
                type = "null";

            } else if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("-")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    System.out.println(" DELETE NUM MINUS");
                    mulvalueFirst = Integer.parseInt(code.getFirst().toString());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    latexExpr.add(code.getFirst());
                    removeToken(tokens,code);
                    if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                        parenthesisResult = parenthesisResult - mulvalueFirst;
                    }
                    if (tokens.getFirst().equals(tokenTypes.MUL)) {
                        parenthesisResult = parenthesisResult - MUL(tokens, code, mulvalueFirst, variableClass, whileIS);
                    }
                    if (tokens.getFirst().equals(tokenTypes.LPAR)){
                        parenthesisResult = parenthesisResult - exprParenthesis(tokens, code, firstValue, variableClass, whileIS);
                    }

                }
                if (tokens.getFirst().equals(tokenTypes.VAR)){
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            mulvalueFirst = variableClass.getVariableListFinal().get(i).variableValue;
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            latexExpr.add(code.getFirst());
                            removeToken(tokens,code);
                            if (!tokens.getFirst().equals(tokenTypes.MUL)) {
                                parenthesisResult = parenthesisResult - mulvalueFirst;
                            }
                            if (tokens.getFirst().equals(tokenTypes.MUL)){
                                parenthesisResult = parenthesisResult - MUL(tokens,code,mulvalueFirst, variableClass,whileIS);
                            }
                        }
                    }
                }
                type = "null";
            }
            else if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("*")){
                parenthesisResult = MUL(tokens,code, parenthesisResult, variableClass, whileIS);
                type = "null";
            } else  if (tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) {
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    parenthesisResult = Integer.parseInt(code.getFirst().toString());
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    latexExpr.add(code.getFirst());
                    removeToken(tokens,code);
                } else if (tokens.getFirst().equals(tokenTypes.VAR)) {
                    for (int i = 0; i < variableClass.getVariableListFinal().size(); i++) {
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)) {
                            parenthesisResult = variableClass.getVariableListFinal().get(i).variableValue;
                            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                            latexExpr.add(code.getFirst());
                            removeToken(tokens,code);
                        }
                    }
                }
                type = "null";
            }
            if (tokens.getFirst().equals(tokenTypes.RPAR) && (tokens.get(1).equals(tokenTypes.SEMCOL) || tokens.get(1).equals(tokenTypes.PLUS) || tokens.get(1).equals(tokenTypes.MINUS) || tokens.get(1).equals(tokenTypes.MUL))){
                if (tokens.getFirst().equals(tokenTypes.RPAR)){
                    saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
                    latexExpr.add(code.getFirst());
                    removeToken(tokens,code);
                }
                break;
            }
        }


       return parenthesisResult;
    }

    public LinkedList getDoWHILEcode() {
        return doWHILEcode;
    }

    public LinkedList getDoWHILEtokens() {
        return doWHILEtokens;
    }

    public boolean isWhile() {
        return isWhile;
    }

    public LinkedList getLatexExpr() {
        return latexExpr;
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
}


