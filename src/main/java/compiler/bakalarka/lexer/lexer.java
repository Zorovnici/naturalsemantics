package compiler.bakalarka.lexer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lexer {

    private String codeThree;
    private LinkedList linkedList = new LinkedList();
    private LinkedList codeAfterLex = new LinkedList();
    private LinkedList whileSEQ = new LinkedList();
    private LinkedList plusWhileSEQ = new LinkedList();
    private LinkedList ifSEQ = new LinkedList();
    private LinkedList plusifSEQ = new LinkedList();
    private LinkedList wasWhile = new LinkedList<>();
    private LinkedList before = new LinkedList();
    private int maxWhileNumber;
    private int whileSEQnumber = 0;
    private int ifSEQnumber = 0;
    private boolean finalTOkenWHILE = false;
    compiler.bakalarka.tokenTypes tokenTypes;

    public void separeCode(String newCode){
        codeThree = newCode;
        String regexParser = "[\\w']+|(&&)|(\\|\\|)|(:=)|(<=)|(!)|(=)|[\\S]";
        Pattern pattern = Pattern.compile(regexParser);
        Matcher matcher = pattern.matcher(newCode);
        boolean isWhile = false;
        boolean isDO = false;
        boolean isRPARwhile = false;
        boolean isRPARif = false;
        boolean isSEMCOLwhile = false;
        boolean isSEMCOLif = false;
        boolean codeENDWHILE = false;
        boolean codeENDIF = false;
        boolean seqWHILE = false;
        boolean moreWHILE = false;
        boolean moreIF = false;
        boolean isIF = false;
        boolean isTHEN = false;
        boolean isELSE = false;
        boolean finalIFEND = false;
        boolean finalIF = false;
        boolean wasELSE = false;
        boolean wasDO = false;
        int whileCount = 0;
        boolean wasDOinDO = false;
        boolean whileENDfirst = false;
        while(matcher.find()) {
            if (matcher.group(0).equals(tokenTypes.IF.getToken())) {
                before.add("if");
                if (isDO) {
                    wasWhile.add(whileCount);
                    System.out.println("whilecount je: " + whileCount);
                    whileCount = 0;
                    isDO = false;
                    wasDO = true;
                }
                isIF = true;
                ifSEQnumber++;
                linkedList.add(tokenTypes.SEQIF);
                linkedList.add(tokenTypes.IF);
            } else if (matcher.group(0).equals(tokenTypes.NEG.getToken())) {
                linkedList.add(tokenTypes.NEG);
            } else if (matcher.group(0).equals(tokenTypes.THEN.getToken())) {
                if (isIF) {
                    isTHEN = true;
                }
                linkedList.add(tokenTypes.THEN);
                linkedList.add(tokenTypes.SEQTHEN);
            } else if (matcher.group(0).equals(tokenTypes.ELSE.getToken())) {
                isELSE = true;
                System.out.println("vložil som SEQENDTHEN");
                linkedList.add(tokenTypes.SEQENDTHEN);
                linkedList.add(tokenTypes.ELSE);
                linkedList.add(tokenTypes.SEQELSE);
            } else if (matcher.group(0).equals(tokenTypes.WHILE.getToken())) {
                whileSEQnumber++;
             /*   if (wasDO && (isTHEN || isELSE) && whileSEQnumber>1){
                    System.out.println("mal by som zmenit poradie" + whileSEQnumber );
                    whileSEQnumber--;
                    plusWhileSEQ.add(whileSEQnumber);
                    if (whileSEQnumber==1) {
                        finalTOkenWHILE = true;
                    }
                }*/
                before.add("while");
                if (wasDOinDO){
                    wasDOinDO = false;
                }
                System.out.println("vložil som SEQWHILE");
                if (!isWhile){
                    isWhile = true;
                }
                if (isELSE){
                    isELSE = false;
                    isRPARif  =false;
                    isSEMCOLif = false;
                    wasELSE = true;
                }
                maxWhileNumber = whileSEQnumber;
                linkedList.add(tokenTypes.SEQWHILE);
                if (!moreWHILE && linkedList.getLast().equals(tokenTypes.SEQWHILE)){
                    moreWHILE = true;
                }
                linkedList.add(tokenTypes.WHILE);
            } else if (matcher.group(0).equals(tokenTypes.DO.getToken())) {
                wasDOinDO = true;
                whileCount++;
                if (!isDO) {
                    isDO = true;
                    isWhile = false;
                }
                if (isELSE){
                    wasELSE = true;
                    isELSE = false;
                }
                if (isDO){
                    wasDO = true;
                }
                linkedList.add(tokenTypes.DO);
                linkedList.add(tokenTypes.SEQDO);
            } else if (matcher.group(0).equals(tokenTypes.TRUE.getToken())) {
                linkedList.add(tokenTypes.TRUE);
            } else if (matcher.group(0).equals(tokenTypes.FALSE.getToken())) {
                linkedList.add(tokenTypes.FALSE);
            } else if (matcher.group(0).equals(tokenTypes.ASSIGN.getToken())) {
                linkedList.add(tokenTypes.ASSIGN);
            } else if (matcher.group(0).equals(tokenTypes.LE.getToken())) {
                linkedList.add(tokenTypes.LE);
            } else if (matcher.group(0).equals(tokenTypes.LPAR.getToken())) {
                linkedList.add(tokenTypes.LPAR);
            } else if (matcher.group(0).equals(tokenTypes.RPAR.getToken())) {
                if (!isRPARwhile && isDO && !isIF && !isELSE && !isTHEN && !isRPARif && !isSEMCOLif){
                    isRPARwhile = true;
                } else
                if (!isRPARif && isELSE){
                    System.out.println("add RPAR");
                    isRPARif = true;
                } else if (!isELSE && isDO) {
                    isRPARwhile = true;
                }
                linkedList.add(tokenTypes.RPAR);
            } else if (matcher.group(0).equals(tokenTypes.PLUS.getToken())) {
                linkedList.add(tokenTypes.PLUS);
            } else if (matcher.group(0).equals(tokenTypes.MINUS.getToken())) {
                linkedList.add(tokenTypes.MINUS);
            } else if (matcher.group(0).equals(tokenTypes.MUL.getToken())) {
                linkedList.add(tokenTypes.MUL);
            } else if (matcher.group(0).equals(tokenTypes.SETVAL.getToken())) {
                linkedList.add(tokenTypes.SETVAL);
                if (linkedList.getLast().equals(tokenTypes.SETVAL) && linkedList.get(linkedList.size()-2).equals(tokenTypes.VAR)) {
                }
            } else if (matcher.group(0).equals(tokenTypes.SEMCOL.getToken())) {
                if (!isSEMCOLwhile && isDO && isRPARwhile){
                    isSEMCOLwhile = true;
                    if (wasDOinDO){
                        wasDOinDO = false;
                        whileENDfirst = true;
                    }
                } else
                if (!isSEMCOLif && isELSE && isRPARif){
                    System.out.println("add SEMCOL");
                    isSEMCOLif = true;
                } else if (!isELSE && isDO && isRPARwhile) {
                    isSEMCOLwhile  =true;
                }
                linkedList.add(tokenTypes.SEMCOL);
            } else if (matcher.group(0).equals(tokenTypes.CON.getToken())) {
                linkedList.add(tokenTypes.CON);
            } else {
                if (Character.isDigit(matcher.group(0).charAt(0))) {
                    linkedList.add(tokenTypes.NUM);
                } else {
                    if (matcher.group(0).equals(tokenTypes.SKIP.getToken())) {
                        linkedList.add(tokenTypes.SKIP);
                    } else {
                        linkedList.add(tokenTypes.VAR);
                    }
                }
            }



            if (isELSE && linkedList.getLast().equals(tokenTypes.IF)){
                isELSE = false;
            }

            if (isWhile && isDO){
                isDO = false;
            }
            if (isTHEN && isIF){
                isIF = false;
            }
            if (isELSE){
                isIF = false;
                isTHEN = false;
            }

            if (isTHEN && isDO && linkedList.getLast().equals(tokenTypes.SEMCOL) && linkedList.get(linkedList.size()-2).equals(tokenTypes.RPAR)) {
                System.out.println("začal som ukončovať som endwhile  v THEN");
                isRPARwhile = false;
                isSEMCOLwhile = false;
                linkedList.add(linkedList.size(),tokenTypes.SEQENDWHILE);
                if (moreWHILE && linkedList.getLast().equals(tokenTypes.SEQENDWHILE)){
                    moreWHILE = false;
                    plusWhileSEQ.add(whileSEQnumber);
                }

                codeENDWHILE = true;
            }

            if (isRPARwhile && isSEMCOLwhile && isDO && !isELSE && !isRPARif && !isSEMCOLif) {
                System.out.println("začal som ukončovať som endwhile");
                isRPARwhile = false;
                isSEMCOLwhile = false;
                linkedList.add(linkedList.size(),tokenTypes.SEQENDWHILE);
                if (moreWHILE && linkedList.getLast().equals(tokenTypes.SEQENDWHILE)){
                    moreWHILE = false;
                    plusWhileSEQ.add(whileSEQnumber);
                }

                codeENDWHILE = true;

            }if (isDO && isELSE && isRPARif && isSEMCOLif) {
                System.out.println("začal som ukončovať som endelse 0 ");
                isRPARif = false;
                isSEMCOLif  = false;
                linkedList.add(linkedList.size(),tokenTypes.SEQENDELSE);
                if (moreIF && linkedList.getLast().equals(tokenTypes.SEQENDELSE)){
                    moreIF = false;
                    plusifSEQ.add(ifSEQnumber);
                }
                codeENDIF = true;
            } else
            if (!isDO && isELSE && isRPARif && isSEMCOLif) {
                System.out.println("začal som ukončovať som endelse 1 ");
                isRPARif = false;
                isSEMCOLif  = false;
                linkedList.add(linkedList.size(),tokenTypes.SEQENDELSE);
                if (moreIF && linkedList.getLast().equals(tokenTypes.SEQENDELSE)){
                    moreIF = false;
                    plusifSEQ.add(ifSEQnumber);
                }
                codeENDIF = true;
            } else if (isRPARif && isSEMCOLif && isELSE && !isIF && !isDO) {
                System.out.println("začal som ukončovať som endelse 2 ");
                isRPARif = false;
                isSEMCOLif = false;
                linkedList.add(linkedList.size(),tokenTypes.SEQENDELSE);
                if (moreIF && linkedList.getLast().equals(tokenTypes.SEQENDELSE)){
                    moreIF = false;
                    plusifSEQ.add(ifSEQnumber);
                }
                codeENDIF = true;
                System.out.println("posledný krát");
            }
            if (matcher.group(0).equals("if")){
                codeAfterLex.add(ifSEQnumber);
                codeAfterLex.add(matcher.group(0));
            } else  if (matcher.group(0).equals("then")){
                codeAfterLex.add(matcher.group(0));
                codeAfterLex.add(ifSEQnumber);
            } else if (matcher.group(0).equals("else")){
                System.out.println("zapísal som else " + isELSE + ifSEQnumber);
                codeAfterLex.add(ifSEQnumber);
                codeAfterLex.add(matcher.group(0));
                codeAfterLex.add(ifSEQnumber);
            } else if (matcher.group(0).equals("while")) {
                System.out.println("zapísal som while " + isELSE);
                codeAfterLex.add(whileSEQnumber);
                codeAfterLex.add(matcher.group(0));
            } else
            if (matcher.group(0).equals("do")) {
                codeAfterLex.add(matcher.group(0));
                codeAfterLex.add(whileSEQnumber);
            }
            else {
                codeAfterLex.add(matcher.group(0));
            }




            if (codeENDWHILE){
                System.out.println("ukončil som endwhile");
                codeAfterLex.add(codeAfterLex.size(),whileSEQnumber);
                codeENDWHILE = false;
                whileSEQnumber--;
                if (whileENDfirst){
                    System.out.println("whilecount je: " + whileCount);
                    wasWhile.add(whileCount);
                    whileCount = 0;
                    whileENDfirst = false;
                }
                if (wasELSE && !wasDO){
                    System.out.println("vraciam sa na else z while");
                    wasELSE = false;
                    isELSE  = true;
                    isWhile = false;
                    isDO = false;
                    isRPARwhile = false;
                    isSEMCOLwhile = false;
                } else if (wasELSE && wasDO){
                    System.out.println("vraciam sa na else z while");
                    wasELSE = false;
                    isELSE  = true;
                    isWhile = false;
                    isDO = false;
                    isRPARwhile = false;
                    isSEMCOLwhile = false;
                }
                if (wasDO){
                    isDO = true;
                    if (whileCount==2){
                        wasDO = false;
                    }
                    if (whileCount>1) {
                        whileCount--;
                        if (whileCount==0){
                            if (wasELSE) {
                                wasELSE = false;
                                isELSE = true;
                            }
                        }
                    }
                    before.removeLast();
                    if (!before.isEmpty() && !isELSE && wasDO && before.getLast().toString().equals("if")){
                        wasDO = true;
                        isDO = false;
                        isTHEN = true;
                    }
                }
                if (linkedList.getLast().equals(tokenTypes.SEQENDWHILE) && codeAfterLex.getLast().toString().equals("1")){
                    System.out.println("som na uplnom konci while" + plusWhileSEQ);
                    whileSEQ.add(plusWhileSEQ);
                    plusWhileSEQ = new LinkedList();
                    System.out.println("whileSEQ je: " + whileSEQ);
                    isWhile = false;
                    isDO = false;
                    isRPARwhile = false;
                    isSEMCOLwhile = false;
                    wasDO = false;
                    System.out.println(" " + isWhile + isDO + isRPARwhile + isSEMCOLwhile);
                    if (wasELSE){
                        wasELSE = false;
                        isELSE  = true;
                    }
                    if (finalTOkenWHILE){
                        finalTOkenWHILE = false;
                        isDO = true;
                        whileSEQnumber++;
                    }
                }
            }
            if (codeENDIF){
                System.out.println("chceme vymazať");
                if (linkedList.getLast().equals(tokenTypes.SEQENDELSE) && codeAfterLex.get(codeAfterLex.size()-3).toString().equals("1")){
                    codeENDIF = false;
                    codeAfterLex.addLast(ifSEQnumber);
                    ifSEQ.add(plusifSEQ);
                    plusifSEQ = new LinkedList();
                    isIF = false;
                    isTHEN = false;
                    isELSE = false;
                    ifSEQnumber = 0;
                    if (wasDO && whileSEQnumber>0){
                        wasDO  = false;
                        isDO = true;
                    }
                    if (wasELSE && ifSEQnumber>0){
                        wasELSE  = false;
                        isELSE = true;
                    }
                    System.out.println("koniec if a konečný znak je: " + codeAfterLex.getLast().toString());
                } else
                if (linkedList.getLast().equals(tokenTypes.SEQENDELSE) && ifSEQnumber>1) {
                    System.out.println(isSEMCOLwhile + " su to veci while  "+ isRPARwhile  + " " + isDO);
                    codeAfterLex.add(codeAfterLex.size(),ifSEQnumber);
                    codeENDIF = false;
                    isELSE = false;
                    ifSEQnumber--;
                    System.out.println("zmenšujem if: " + ifSEQnumber);
                    System.out.println(codeAfterLex);
                    if (wasDO){
                        wasDO  = false;
                        isDO  = true;
                    }
                    if (wasELSE && ifSEQnumber>0){
                        wasELSE  = false;
                        isELSE = true;
                    }
                    System.out.println(isSEMCOLwhile + " su to veci while  "+ isRPARwhile  + " " + isDO);

                } else if (ifSEQnumber==1 && !linkedList.get(linkedList.size()-3).equals(tokenTypes.SEQENDELSE)){
                    codeAfterLex.add(ifSEQnumber);
                    codeENDIF = false;
                    isELSE = false;
                    isIF = false;
                    isTHEN = false;
                    ifSEQnumber = 0;
                    isRPARwhile = false;
                    isSEMCOLwhile  =false;
                    before.removeLast();
                    if (wasDO){
                        System.out.println("idem na while");
                        wasDO  = false;
                        isDO  = true;
                        wasWhile.removeFirst();
                    }
                    System.out.println("ukončujem else 1");
                } else if (ifSEQnumber==1 && linkedList.getLast().equals(tokenTypes.SEQENDELSE) && !codeAfterLex.getLast().toString().equals("1")){
                    codeAfterLex.add(ifSEQnumber);
                    codeENDIF = false;
                    isELSE = false;
                    isIF = false;
                    isTHEN = false;
                    ifSEQnumber = 0;
                    if (wasDO){
                        wasDO  =false;
                        isDO  = true;
                    }
                    System.out.println("ukončujem else 2");
                }
            }
            if (!linkedList.isEmpty() && linkedList.getLast().equals(tokenTypes.SEMCOL) && linkedList.get(linkedList.size()-2).equals(tokenTypes.RPAR) && !isDO && !isWhile && isELSE) {
                if (Integer.parseInt(codeAfterLex.get(codeAfterLex.size() - 3).toString())>=2) {
                    linkedList.add(tokenTypes.SEQENDELSE);
                    codeAfterLex.add(ifSEQnumber);
                    System.out.println(codeAfterLex);
                    System.out.println(linkedList);
                    System.out.println("posledný");
                    ifSEQnumber--;
                } else {
                    codeENDIF = true;
                }
            }


        }
        whileSEQnumber = 0;
        ifSEQnumber = 0;
     /*   System.out.println(" " + whileSEQ + wasWhile);
        whileSEQ = new LinkedList();
        whileSEQ.addFirst(wasWhile);
        System.out.println(" " + whileSEQ);
        System.out.println("wasWhile je: " + wasWhile);*/
        if (!whileSEQ.isEmpty()) {
            System.out.println(" ");
            System.out.println("Všetky while:  " + whileSEQ);
            System.out.println(" ");
        }
        System.out.println(before);
        wasWhile.clear();
        System.out.println("List tokenov");
        for (int i=0;i<linkedList.size();i++){
            System.out.print(" " + linkedList.get(i));
        }
        System.out.println(" ");
        System.out.println("List kódu");
        for (int i=0;i<codeAfterLex.size();i++){
            System.out.print(" " + codeAfterLex.get(i));
        }
        if (!ifSEQ.isEmpty()) {
            System.out.println(" ");
            System.out.println("Všetky if:  " + ifSEQ);
            System.out.println(" ");
        }
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public LinkedList getCodeAfterLex() {
        return codeAfterLex;
    }


    public LinkedList getWhileSEQ() {
        return whileSEQ;
    }
}
