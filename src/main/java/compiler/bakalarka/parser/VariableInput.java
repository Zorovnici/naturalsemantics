package compiler.bakalarka.parser;

import java.util.LinkedList;
import compiler.bakalarka.tokenTypes;


public class VariableInput {
    private LinkedList doWHILEcode = new LinkedList();
    private LinkedList doWHILEtokens = new LinkedList();
    private String finalSEMCOL;
    public void parsingVar(LinkedList code, LinkedList tokens, boolean whileIS){
        doWHILEtokens.clear();
        doWHILEcode.clear();
        if (tokens.getFirst().equals(tokenTypes.SEMCOL)){
            finalSEMCOL = code.getFirst().toString();
            saveWhileToken(whileIS,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }
    }

    public LinkedList getDoWHILEcode() {
        return doWHILEcode;
    }

    public LinkedList getDoWHILEtokens() {
        return doWHILEtokens;
    }

    public String getFinalSEMCOL() {
        return finalSEMCOL;
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
