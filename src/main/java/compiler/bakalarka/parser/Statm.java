package compiler.bakalarka.parser;

import compiler.bakalarka.tokenTypes;

import java.util.LinkedList;

public class Statm {
    boolean isOK = false;
    boolean negation = false;
    private LinkedList doWHILEcode = new LinkedList();
    private LinkedList doWHILEtokens = new LinkedList();
    private LinkedList conditionInTree = new LinkedList();

    public void Condition(LinkedList code, LinkedList tokens, VariableClass variableClass, boolean isWhile) {
        if (tokens.getFirst().equals(tokenTypes.IF)) {
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }
        if (tokens.getFirst().equals(tokenTypes.LPAR)){
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }

        Comparator(tokens,code, variableClass, isWhile);
            if (tokens.getFirst().equals(tokenTypes.RPAR)) {
                saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }
    }

    public boolean isOK() {
        return isOK;
    }

    public void loop(LinkedList tokens, LinkedList code , VariableClass variableClass, boolean isWhile){
        if (tokens.getFirst().equals(tokenTypes.WHILE)){
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }
        if (tokens.getFirst().equals(tokenTypes.LPAR)){
            saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
            removeToken(tokens,code);
        }
        Comparator(tokens,code, variableClass, isWhile);
            if (tokens.getFirst().equals(tokenTypes.RPAR)) {
                saveWhileToken(isWhile,code,tokens,doWHILEtokens,doWHILEcode);
                removeToken(tokens,code);
            }
    }

    public void Comparator(LinkedList tokens, LinkedList code, VariableClass variableClass, boolean isWhile) {
        Comp comp = new Comp();
        comp.comp(tokens, code, variableClass ,isWhile);
        isOK = comp.isOK();
        if (isWhile) {
            doWHILEcode.addAll(comp.getDoWHILEcode());
            doWHILEtokens.addAll(comp.getDoWHILEtokens());
        } else {
            comp.getDoWHILEcode().clear();
            comp.getDoWHILEtokens().clear();
        }
        conditionInTree.addAll(comp.getConditionInTree());
        comp.getConditionInTree().clear();
    }

    public boolean isNegation() {
        return negation;
    }

    public LinkedList getDoWHILEcode() {
        return doWHILEcode;
    }

    public LinkedList getDoWHILEtokens() {
        return doWHILEtokens;
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

    public LinkedList getConditionInTree() {
        return conditionInTree;
    }
}
