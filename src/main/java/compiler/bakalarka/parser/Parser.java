package compiler.bakalarka.parser;
import compiler.bakalarka.tokenTypes;
import compiler.bakalarka.treeOutput.CreateTree;
import compiler.bakalarka.treeOutput.GenerateTree;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


public class Parser {
    private int StatesOfVariables = 0;
    private Expr expr = new Expr();
    private Statm statm = new Statm();
    private VariableClass variableClass = new VariableClass();
    private VariableInput variableInput = new VariableInput();
    private GenerateTree generateTree = new GenerateTree();
    private CreateTree createTree = new CreateTree();
    private int valueOfVar;
    private String nameOfVar;
    private String type = "null";
    private boolean negationWhile;
    private boolean negationIF;
    private String tree = "";
    private boolean isWhile = false;
    private boolean isIf = false;
    private boolean andWhile = false;
    private boolean firsttime = false;
    private LinkedList whileSEQ =  new LinkedList();
    private int startWhile = 0;
    private int actualWHILE;
    private int ifposition = 0;
    private LinkedList actualIF = new LinkedList();
    private LinkedList latexTree = new LinkedList();
    private LinkedList latexVar = new LinkedList();
    private LinkedList helpTokens = new LinkedList();
    private LinkedList whileLatexTree = new LinkedList();
    private LinkedList finalLatexTree = new LinkedList();
    private LinkedList helpTree = new LinkedList();
    private int varSizeToTree;
    private boolean isNextElementToTree = false;
    private int nextELementPositionInTree;
    private int condELementPositionInTree;
    private int addCondPosition;
    private int actualWhileBefoerIF;
    private int getActualWHILEAfterIF;
    private int countofwhileSEQ = 0;
    private int wasWhile;
    public void checkCode(LinkedList tokens,LinkedList code, VariableClass variableClass, LinkedList codeThree, LinkedList whileSEQnum){
        helpTokens.addAll(tokens);
        latexTree.addAll(codeThree);
        helpTree.addAll(latexTree);
        createTree.endCodeCreate(finalLatexTree);
        System.out.println("strom sa začína: " + finalLatexTree);
        if (!whileSEQnum.isEmpty()) {
            whileSEQ.addAll((Collection) whileSEQnum.getFirst());
            countofwhileSEQ = whileSEQ.size();
            whileSEQnum.remove();
            if (!whileSEQ.isEmpty()) {
                actualWHILE = Integer.parseInt(whileSEQ.getFirst().toString());
                whileSEQ.remove();
            }
        }

        while(!tokens.isEmpty()){
         //   System.out.println(code + "\n" + tokens);
            if (isWhile && tokens.getFirst().equals(tokenTypes.RPAR) && tokens.get(1).equals(tokenTypes.SEQENDTHEN)){
                isIf  =true;
            }
            if(tokens.getFirst().equals(tokenTypes.SKIP)){
                saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                removeToken(tokens,code);
            }
            if(tokens.getFirst().equals(tokenTypes.VAR)){
                nameOfVar = code.getFirst().toString();
                latexVar.add(code.getFirst());
                saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                removeToken(tokens,code);
            }
            if (tokens.getFirst().equals(tokenTypes.SETVAL)){
                latexVar.add(code.getFirst());
                saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                removeToken(tokens,code);
            }
            else{
            }
            if ((tokens.getFirst().equals(tokenTypes.NUM) || tokens.getFirst().equals(tokenTypes.VAR)) && type.equals("null")){
                if (tokens.getFirst().equals(tokenTypes.NUM)) {
                    valueOfVar = Integer.parseInt(code.getFirst().toString());
                    latexVar.add(code.getFirst());
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                }
                boolean latexVarFirst = true;
                if (tokens.getFirst().equals(tokenTypes.VAR)) {
                    for (int i=0;i<variableClass.getVariableListFinal().size();i++){
                        if (code.getFirst().equals(variableClass.getVariableListFinal().get(i).variableName)){
                            valueOfVar = variableClass.getVariableListFinal().get(i).variableValue;
                            if (latexVarFirst) {
                                latexVarFirst = false;
                                latexVar.add(code.getFirst());
                            }
                        }
                    }
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                }
            }
            else{
            }

            if (isWhile && isIf){
                //System.out.println("isIf aj isWhile su zapnute");
                if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.LPAR)){
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                }
                if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.RPAR)){
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                    if (tokens.getFirst().equals(tokenTypes.SEQENDTHEN)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                    if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEMCOL)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQENDELSE)){
                            if (code.getFirst().toString().equals("1") && tokens.getFirst().equals(tokenTypes.SEQENDELSE) && !isWhile){
                                isIf = false;
                                System.out.println("ukončil som if");
                            }
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);

                            if (!actualIF.isEmpty() && ifposition>1) {
                                actualIF.removeLast();
                                if (!actualIF.isEmpty()) {
                                    negationIF = Boolean.parseBoolean(actualIF.getLast().toString());
                                }
                            }
                        }

                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQENDWHILE)){
                            System.out.println("skončil som while " + code.getFirst());
                            andWhile = true;
                          /*  if (code.getFirst().toString().equals("1")){
                                System.out.println("skončil som");
                                isWhile = false;
                            }*/
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                        if (andWhile) {
                            code.addAll(0, statm.getDoWHILEcode());
                            tokens.addAll(0, statm.getDoWHILEtokens());
                            System.out.println("ideme znova kým neni koniec " + code);

                            andWhile = false;
                            statm.getDoWHILEtokens().clear();
                            statm.getDoWHILEcode().clear();
                        }
                    }
                }
            }


            if (isIf && !isWhile){
                if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.LPAR)){
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                }
                if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.RPAR)) {
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                    if (tokens.getFirst().equals(tokenTypes.SEQENDTHEN)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }

                    if (tokens.getFirst().equals(tokenTypes.SEMCOL)) {
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQENDELSE)){
                            statm.getConditionInTree().clear();
                            if (!code.isEmpty() && code.getFirst().toString().equals("1") && tokens.getFirst().equals(tokenTypes.SEQENDELSE)){
                                isIf = false;
                            }
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            if (!tokens.isEmpty() && !code.isEmpty()) {
                                removeToken(tokens,code);
                            }

                            if (!actualIF.isEmpty() && ifposition>1) {
                                actualIF.removeLast();
                                if (!actualIF.isEmpty()) {
                                    negationIF = Boolean.parseBoolean(actualIF.getLast().toString());
                                }
                            }

                        }
                    }
                }
            }

            if (!tokens.isEmpty()){
                if (tokens.getFirst().equals(tokenTypes.SEQIF)){
                    ifposition = Integer.parseInt(code.getFirst().toString());
                    if (tokens.get(1).equals(tokenTypes.IF)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    }
                    removeToken(tokens,code);
                }
                if (tokens.getFirst().equals(tokenTypes.IF)) {
                    System.out.println("aktuálny while je: " + actualWHILE + " " + wasWhile);
                  /*  if (actualWHILE>1 && isWhile){
                        actualWHILE = wasWhile;
                    } else {
                        actualWHILE++;
                        *//*startWhile = 0;
                        firsttime = false;*//*
                    }*/
                    isIf = true;
                    doBexpIF(tokens, code, isWhile);
                    if (statm.isOK && !statm.isNegation()){
                        negationIF = true;
                    } else
                    if (!statm.isOK && !statm.isNegation()){
                        negationIF = false;
                    } else
                    if (statm.isOK && statm.isNegation()){
                        negationIF = false;
                    } else
                    if (!statm.isOK && statm.isNegation()){
                        negationIF = true;
                    }
                    actualIF.addLast(negationIF);

                }

                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.THEN) && negationIF){
                    System.out.println("ideme THEN");
                    if (tokens.getFirst().equals(tokenTypes.THEN)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                    if (tokens.getFirst().equals(tokenTypes.SEQTHEN)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                }

                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.THEN) && !negationIF) {
                    System.out.println("nejdeme THEN");

                    while(!tokens.isEmpty()){
                        if (tokens.getFirst().equals(tokenTypes.SEQENDTHEN) && Integer.parseInt(code.getFirst().toString())==ifposition){
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                            break;
                        }
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                }
                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.ELSE) && negationIF){
                    System.out.println("ideme ELSE");
                    while(!tokens.isEmpty()){
                        if (!code.isEmpty()) {
                            System.out.println(code.getFirst());
                        }
                        if (!code.isEmpty() && !tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQENDELSE)){
                            statm.getConditionInTree().clear();
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                            break;
                        }
                        if (!tokens.isEmpty() && !code.isEmpty()) {
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                    }
                }
                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.ELSE) && !negationIF){
                    System.out.println("Ideme ELSE");
                    if (tokens.getFirst().equals(tokenTypes.ELSE)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                    if (tokens.getFirst().equals(tokenTypes.SEQELSE)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                    }
                }
            }

            if (!tokens.isEmpty()){

                if (tokens.getFirst().equals(tokenTypes.SEQWHILE)){
                    System.out.println(code + "\n" + tokens);
                    actualWhileBefoerIF = Integer.parseInt(code.getFirst().toString());
                    wasWhile = actualWhileBefoerIF;

                    if (tokens.get(1).equals(tokenTypes.WHILE)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    }
                    removeToken(tokens,code);
                }
                if (tokens.getFirst().equals(tokenTypes.WHILE)) {
                    isWhile = true;
                    doBexpWHILE(tokens, code, isWhile);
                    if (statm.isOK() && !statm.isNegation()){
                        negationWhile = true;
                    } else
                    if (!statm.isOK() && !statm.isNegation()){
                        negationWhile = false;
                    } else
                    if (statm.isOK() && statm.isNegation()){
                        negationWhile = false;
                    } else
                    if (!statm.isOK() && statm.isNegation()){
                        negationWhile = true;
                    }
                }
                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.DO) && negationWhile){
                    System.out.println("vykonávam while a actualWhile je: " + actualWHILE);

                    if (Integer.parseInt(code.get(1).toString())<actualWHILE && firsttime){
                        System.out.println("ideme prvý krat");
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                        while (!tokens.isEmpty()){
                            if ((tokens.getFirst().equals(tokenTypes.SEQWHILE) && code.getFirst().toString().equals(String.valueOf(actualWHILE)))){
                                break;
                            }
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                    } else {
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.DO)) {
                            System.out.println("ideme posledný");
                            if (!firsttime){
                                startWhile++;
                            }
                            if (actualWHILE == startWhile){
                                firsttime = true;
                            }
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQDO)) {
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQWHILE)) {
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                    }
                }
                if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.DO) && !negationWhile){
                    System.out.println("mažem while " + actualWHILE);

                    if (actualWHILE>0) {
                        actualWHILE--;
                    }
                    if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.DO)){
                        removeToken(tokens,code);
                    }
                    if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQDO)){
                        removeToken(tokens,code);
                    }
                    System.out.println(" ");
                    while (!tokens.isEmpty()){
                        if (tokens.getFirst().equals(tokenTypes.SEQENDWHILE)){
                            for (int i=0;i<finalLatexTree.size();i++){
                                if (finalLatexTree.get(i).equals("next")){
                                    nextELementPositionInTree = i;
                                    isNextElementToTree = true;
                                    finalLatexTree.remove(i);
                                }
                            }
                            finalLatexTree.addAll(condELementPositionInTree,addContionToLinkedList(statm.getConditionInTree(), negationWhile));
                            statm.getConditionInTree().clear();

                            if (tokens.getFirst().equals(tokenTypes.SEQENDWHILE) && code.getFirst().toString().equals("1")) {
                                removeWhile(tokens, code , countofwhileSEQ);
                                addNextElementToTree(helpTree, varSizeToTree);
                                startWhile = 0;
                                firsttime = false;
                                if (!whileSEQnum.isEmpty()) {
                                    whileSEQ = new LinkedList();
                                    whileSEQ.addAll((Collection) whileSEQnum.getFirst());
                                    whileSEQnum.remove();
                                    actualWHILE = Integer.parseInt(whileSEQ.getFirst().toString());
                                    whileSEQ.remove();
                                }
                                isWhile = false;
                            }

                            if (!tokens.isEmpty() && !code.isEmpty()) {
                                removeToken(tokens, code);
                            }
                            if (!whileSEQ.isEmpty() && !tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQWHILE) && Integer.parseInt(code.getFirst().toString())>1) {
                                startWhile = actualWHILE;
                                actualWHILE = Integer.parseInt(whileSEQ.getFirst().toString());
                                whileSEQ.remove();
                                firsttime = false;
                            }
                            break;
                        }

                        removeToken(tokens,code);
                    }
                }
            }

            if (!tokens.isEmpty()) {
                if (tokens.getFirst().equals(tokenTypes.MINUS) || tokens.getFirst().equals(tokenTypes.PLUS) || tokens.getFirst().equals(tokenTypes.MUL)){
                    doExpr(tokens,code,valueOfVar, isWhile);
                    if (isWhile && !expr.isWhile()){
                        statm.getDoWHILEtokens().addAll(expr.getDoWHILEtokens());
                        statm.getDoWHILEcode().addAll(expr.getDoWHILEcode());
                    }
                }
            }
            if (!tokens.isEmpty()) {
                if (tokens.getFirst().equals(tokenTypes.SEMCOL)) {
                    parsingVar(tokens, code, isWhile);
                    if (isWhile){
                        statm.getDoWHILEtokens().addAll(variableInput.getDoWHILEtokens());
                        statm.getDoWHILEcode().addAll(variableInput.getDoWHILEcode());
                    }
                }
            }
            if (!tokens.isEmpty() && isWhile && !isIf){
                System.out.println("končím iba while");
                if(tokens.getFirst().equals(tokenTypes.LPAR)){
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                }

                if(!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.RPAR) && !tokens.get(1).equals(tokenTypes.SEQENDTHEN)){
                    saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                    removeToken(tokens,code);
                    if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEMCOL)){
                        saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                        removeToken(tokens,code);
                        if (!tokens.isEmpty() && tokens.getFirst().equals(tokenTypes.SEQENDWHILE)){
                            statm.getConditionInTree().clear();
                            andWhile = true;
                            saveWhileToken(isWhile,code,tokens,statm.getDoWHILEtokens(),statm.getDoWHILEcode());
                            removeToken(tokens,code);
                        }
                        if (andWhile) {
                            code.addAll(0, statm.getDoWHILEcode());
                            tokens.addAll(0, statm.getDoWHILEtokens());
                            andWhile = false;
                            statm.getDoWHILEtokens().clear();
                            statm.getDoWHILEcode().clear();
                        }
                    }
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e){}
            }
        isWhile = false;
        StatesOfVariables=0;
        firsttime = false;
        andWhile = false;
        startWhile = 0;
        isIf = false;
        actualIF.clear();
        startCodeInLatex();
        finalLatexTree.addAll(createTree.startCodeCreate(latexTree,StatesOfVariables));
        for (int i=0;i<finalLatexTree.size();i++){
            tree+=finalLatexTree.get(i) + " ";
        }
        generateTree.ImageGenerate(tree);
        tree = "";
        /*latexTree.clear();*/
        latexTree = new LinkedList();
        /*latexVar.clear();*/
        latexVar = new LinkedList();
        /*helpTokens.clear();*/
        helpTokens = new LinkedList();
        /*whileLatexTree.clear();*/
        whileLatexTree = new LinkedList();
        /*finalLatexTree.clear();*/
        finalLatexTree = new LinkedList();
        helpTree = new LinkedList();
        whileSEQ = new LinkedList();
        ifposition = 0;
        condELementPositionInTree = 0;
        nextELementPositionInTree = 0;
        isNextElementToTree = false;
    }

    public void parsingVar(LinkedList tokens,LinkedList code, boolean whileIS) {
        variableInput.parsingVar(code, tokens, whileIS);
        variableClass.addVariable(nameOfVar, valueOfVar, StatesOfVariables+1);
        for (int i=0;i<finalLatexTree.size();i++){
            if (finalLatexTree.get(i).equals("next")){
                nextELementPositionInTree = i;
                isNextElementToTree = true;
                finalLatexTree.remove(i);
            }
            if (finalLatexTree.get(i).equals("cond")){
                condELementPositionInTree = i+1;
                finalLatexTree.remove(i);
                isNextElementToTree = true;
            }
        }

        if (!isNextElementToTree) {
            latexVar.add(variableInput.getFinalSEMCOL());
            varSizeToTree = latexVar.size();
            createTree.createVARorEXPR(latexVar, StatesOfVariables, isWhile, isIf);
            createTree.endCodeCreate(latexVar);
            finalLatexTree.addAll(latexVar);
            addNextElementToTree(helpTree, varSizeToTree);
            if (helpTree.size() > 0) {
                StatesOfVariables++;
                LinkedList newTree = new LinkedList();
                newTree.addAll(helpTree);
                createTree.createVARorEXPR(newTree, StatesOfVariables , isWhile, isIf);
                createTree.endCodeCreateTree(newTree);
                finalLatexTree.addAll(newTree);
                newTree.clear();
                latexVar.clear();
            }
        } else if (isNextElementToTree) {
            latexVar.addAll(expr.getLatexExpr());
            createTree.createVARorEXPR(latexVar, StatesOfVariables, isWhile, isIf);
            createTree.endCodeCreate(latexVar);
            finalLatexTree.addAll(nextELementPositionInTree,latexVar);
            addNextElementToTree(helpTree, varSizeToTree);
            if (helpTree.size() > 0) {
                StatesOfVariables++;
                LinkedList newTree = new LinkedList();
                newTree.addAll(helpTree);
                createTree.createVARorEXPR(newTree, StatesOfVariables, isWhile, isIf);
                createTree.endCodeCreateTree(newTree);
                finalLatexTree.addAll(nextELementPositionInTree + latexVar.size(),newTree);
                newTree.clear();
                latexVar.clear();
            }
            if (isWhile) {
                for (int i=0;i<finalLatexTree.size();i++){
                    if (finalLatexTree.get(i).equals("cond")){
                        condELementPositionInTree = i;
                        finalLatexTree.remove(i);
                    }
                }
                finalLatexTree.addAll(condELementPositionInTree,addContionToLinkedList(statm.getConditionInTree(), negationWhile));

            }
            if (isIf) {
                for (int i=0;i<finalLatexTree.size();i++){
                    if (finalLatexTree.get(i).equals("cond")){
                        condELementPositionInTree = i;
                        finalLatexTree.remove(i);
                    }
                }
                finalLatexTree.addAll(condELementPositionInTree,addContionToLinkedList(statm.getConditionInTree(), negationIF));

            }
            isNextElementToTree = false;
        }
        latexVar.clear();
        expr.getLatexExpr().clear();
    }

    public void doExpr(LinkedList tokens,LinkedList code , int value , boolean whileIS){
        expr.parsingExpr(tokens,code,type, value , variableClass, whileIS);
        variableClass.addVariable(nameOfVar,expr.getResult(),StatesOfVariables+1);
     for (int i=0;i<finalLatexTree.size();i++){
            if (finalLatexTree.get(i).equals("next")){
                nextELementPositionInTree = i;
                isNextElementToTree = true;
                finalLatexTree.remove(i);
            }
           if (finalLatexTree.get(i).equals("cond")){
               condELementPositionInTree = i+1;
               finalLatexTree.remove(i);
               isNextElementToTree = true;
           }
        }

        if (!isNextElementToTree) {

            latexVar.addAll(expr.getLatexExpr());
            varSizeToTree = latexVar.size();
            createTree.createVARorEXPR(latexVar, StatesOfVariables, isWhile, isIf);
            createTree.endCodeCreate(latexVar);
            finalLatexTree.addAll(latexVar);
            addNextElementToTree(helpTree, varSizeToTree);
            if (helpTree.size() > 0) {
                StatesOfVariables++;
                LinkedList newTree = new LinkedList();
                newTree.addAll(helpTree);
                createTree.createVARorEXPR(newTree, StatesOfVariables , isWhile, isIf);
                createTree.endCodeCreateTree(newTree);
                finalLatexTree.addAll(newTree);
                newTree.clear();
                latexVar.clear();
            }
        } else if (isNextElementToTree) {
            latexVar.addAll(expr.getLatexExpr());
            createTree.createVARorEXPR(latexVar, StatesOfVariables, isWhile, isIf);
            createTree.endCodeCreate(latexVar);
            finalLatexTree.addAll(nextELementPositionInTree,latexVar);
            addNextElementToTree(helpTree, varSizeToTree);

            if (helpTree.size() > 0) {
                StatesOfVariables++;
                LinkedList newTree = new LinkedList();
                newTree.addAll(helpTree);
                createTree.createVARorEXPR(newTree, StatesOfVariables, isWhile, isIf);
                createTree.endCodeCreateTree(newTree);
                finalLatexTree.addAll(nextELementPositionInTree + latexVar.size(),newTree);
                newTree.clear();
                latexVar.clear();
            }
            if (isWhile) {
                for (int i=0;i<finalLatexTree.size();i++){
                    if (finalLatexTree.get(i).equals("cond")){
                        condELementPositionInTree = i;
                        finalLatexTree.remove(i);
                    }
                }
                    finalLatexTree.addAll(condELementPositionInTree,addContionToLinkedList(statm.getConditionInTree(), negationWhile));

            }
           if (isIf) {
                for (int i=0;i<finalLatexTree.size();i++){
                    if (finalLatexTree.get(i).equals("cond")){
                        condELementPositionInTree = i;
                        finalLatexTree.remove(i);
                    }
                }
                finalLatexTree.addAll(condELementPositionInTree,addContionToLinkedList(statm.getConditionInTree(), negationIF));

            }
            isNextElementToTree = false;
        }
        latexVar.clear();
        expr.getLatexExpr().clear();
    }

    public void doBexpIF(LinkedList tokens, LinkedList code, boolean isWhile){
        statm.Condition(code, tokens , variableClass, isWhile);
    }
    public void doBexpWHILE(LinkedList tokens, LinkedList code, boolean isWhile){
        statm.loop(tokens,code,variableClass, isWhile);
    }

    public VariableClass getVariableClass() {
        return variableClass;
    }

    public String getTree() {
        return tree;
    }

    public Statm getStatm() {
        return statm;
    }

    public VariableInput getVariableInput() {
        return variableInput;
    }

    public GenerateTree getGenerateTree() {
        return generateTree;
    }


    public void startCodeInLatex(){
        Iterator tokenIterator = helpTokens.iterator();
        Iterator codeIterator = latexTree.iterator();
        while (tokenIterator.hasNext() && codeIterator.hasNext()) {
            Object token = tokenIterator.next();
            Object code = codeIterator.next();
            if (token.equals(tokenTypes.SEQWHILE)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQDO)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQENDWHILE)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQIF)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQTHEN)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQENDTHEN)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQELSE)){
                tokenIterator.remove();
                codeIterator.remove();
            } if (token.equals(tokenTypes.SEQENDELSE)){
                tokenIterator.remove();
                codeIterator.remove();
            }
        }
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

    public void addNextElementToTree(LinkedList tree, int elememtNumber){
        int elementNumber = elememtNumber;
        if (!isIf && !isWhile) {
            while (elementNumber != 0) {
                if (!tree.isEmpty()) {
                    tree.removeFirst();
                    helpTokens.removeFirst();
                    elementNumber--;
                }
            }
        }
        if (isWhile && !negationWhile){
            if (!helpTokens.isEmpty() && helpTokens.getFirst().equals(tokenTypes.SEQWHILE)) {
                while (!helpTokens.isEmpty()){
                    if (helpTokens.getFirst().equals(tokenTypes.SEQENDWHILE)){
                        helpTokens.remove();
                        tree.remove();
                        break;
                    }
                    helpTokens.remove();
                    tree.remove();
                }
            }
        }
        if (isIf){
            if (!helpTokens.isEmpty() && helpTokens.getFirst().equals(tokenTypes.SEQIF)) {
                while (!helpTokens.isEmpty()){
                    if (helpTokens.getFirst().equals(tokenTypes.SEQENDELSE)){
                        helpTokens.remove();
                        tree.remove();
                        if (!helpTokens.isEmpty() && helpTokens.getFirst().equals(tokenTypes.RPAR) && helpTokens.get(1).equals(tokenTypes.SEMCOL)){
                            tree.remove();
                            helpTokens.remove();
                            tree.remove();
                            helpTokens.remove();
                        }
                        break;
                    }
                    helpTokens.remove();
                    tree.remove();
                }
            }
        }
    }

    public LinkedList addContionToLinkedList(LinkedList condition , boolean negation) {
        if (negation) {
            createTree.addTrueCOndition(condition);
        } else if (!negation){
            createTree.addFalseCOndition(condition);
        }
        return  condition;
    }

    public void removeWhile(LinkedList tokens , LinkedList code , int count) {
        while (!tokens.isEmpty()) {
            if(tokens.getFirst().equals(tokenTypes.SEQENDWHILE) && code.getFirst().equals("1")){
                tokens.remove();
                code.remove();
                count--;
                if (count==0){
                    break;
                }
            }
            tokens.remove();
            code.remove();
        }
    }
}
