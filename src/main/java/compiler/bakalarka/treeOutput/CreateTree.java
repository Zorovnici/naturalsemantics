package compiler.bakalarka.treeOutput;


import java.util.LinkedList;

public class CreateTree {

    private String dfrac = "\\dfrac";
    private String LPAR = "{";
    private String RPAR = "}";
    private String rightArrow = "\\rightarrow";
    private String startState = "\\langle";
    private String endState = "\\rangle";
    private String comma = ",";
    private String mathscrB = "\\mathscr"+LPAR+"B"+RPAR+"";
    private String DoubleLPAR = "[\\![";
    private String DoubleRPAR = "]\\!]";
    private String ASSIGN = "=";
    private String TRUEcondition = "$\\mathscr{tt}$";
    private String FALSEcondition = "$\\mathscr{ff}$";
    private String SetStateStart = "$s_{";
    private String SetStateEnd = "}$";

    public LinkedList addTrueCOndition(LinkedList condition){
        condition.addFirst(DoubleLPAR);
        condition.addFirst(mathscrB);
        condition.add(DoubleRPAR);
        condition.add(ASSIGN);
        condition.add(TRUEcondition);
        return condition;
    }
    public LinkedList addFalseCOndition(LinkedList condition){
        condition.addFirst(DoubleLPAR);
        condition.addFirst(mathscrB);
        condition.add(DoubleRPAR);
        condition.add(ASSIGN);
        condition.add(FALSEcondition);
        return condition;
    }
    public LinkedList createVARorEXPR(LinkedList variable, int numberOfState , boolean isWhile , boolean isIF){
        variable.addFirst(startState);
        variable.addFirst(LPAR);
        variable.addFirst(RPAR);
        variable.add(comma);
        variable.add(SetStateStart);
        variable.add(numberOfState);
        variable.add(SetStateEnd);
        variable.add(endState);
        variable.add(rightArrow);
        variable.add(SetStateStart);
        variable.add(numberOfState+1);
        variable.add(SetStateEnd);
        variable.add(RPAR);
        if (isWhile || isIF){
            variable.add(dfrac);
            variable.add(LPAR);
            variable.add(RPAR);
            variable.add(LPAR);
            variable.add("cond");
            variable.add(RPAR);
        }
        return variable;
    }
    public LinkedList startCodeCreate(LinkedList code, int stateNumber){
        code.addFirst(startState);
        code.addFirst(LPAR);
        code.addFirst(RPAR);
        code.add(comma);
        code.add(SetStateStart);
        code.add(stateNumber);
        code.add(SetStateEnd);
        code.add(endState);
        code.add(rightArrow);
        code.add(SetStateStart);
        code.add(SetStateEnd);
        code.add(RPAR);
        return code;
    }
    public LinkedList endCodeCreate(LinkedList code){
        code.addFirst(LPAR);
        code.addFirst(dfrac);
        return  code;
    }
    public LinkedList endCodeCreateTree(LinkedList code){
        code.addFirst("next");
        code.addFirst(LPAR);
        code.addFirst(dfrac);
        return  code;
    }
    public LinkedList endConditionInLatex(LinkedList cond){
        cond.addFirst(LPAR);
        cond.addFirst(RPAR);
        cond.addFirst(LPAR);
        cond.addFirst(dfrac);
        cond.add(RPAR);
        return  cond;
    }

}
