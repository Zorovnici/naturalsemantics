package compiler.bakalarka.web;

import compiler.bakalarka.lexer.lexer;
import compiler.bakalarka.parser.Parser;
import java.util.*;

public class WebUI {

    private lexer lexer = new lexer();
    private Parser parser = new Parser();
    public void clear(){
        parser.getVariableClass().getVariableListFinal().clear();
        lexer.getCodeAfterLex().clear();
        lexer.getLinkedList().clear();
        parser.getStatm().getDoWHILEcode().clear();
        parser.getStatm().getDoWHILEtokens().clear();
        parser.getVariableInput().getDoWHILEcode().clear();
        parser.getVariableInput().getDoWHILEtokens().clear();
        lexer.getWhileSEQ().clear();
    }

    public String showVariables(String code){
        Formatter sb = new Formatter();
        lexer.separeCode(code);
        parser.checkCode(lexer.getLinkedList(),lexer.getCodeAfterLex(),parser.getVariableClass(),lexer.getCodeAfterLex(),lexer.getWhileSEQ());
        sb.format("<h2>The Values of the Variables</h2>");
        sb.format("<ul class=\"responsive-table\">");
        sb.format("<li class=\"table-header\">");
        sb.format("<div class=\"col col-3\">States</div>");
        sb.format("<div class=\"col col-1\">Variable</div>");
        sb.format("<div class=\"col col-2\">Value</div>");
        sb.format("</li>");
        for (int i=0;i<parser.getVariableClass().getVariableListFinal().size();i++){
            sb.format("<li class=\"table-row\">");
            sb.format("<div class=\"col col-3\" data-label=\"Customer Name\">"+parser.getVariableClass().getVariableListFinal().get(i).getState()+"</div>");
            sb.format("<div class=\"col col-1\" data-label=\"Job Id\">"+parser.getVariableClass().getVariableListFinal().get(i).getVariableName()+"</div>");
            sb.format("<div class=\"col col-2\" data-label=\"Customer Name\">"+parser.getVariableClass().getVariableListFinal().get(i).getVariableValue()+"</div>");
            sb.format("</li>");
        }
        sb.format(" </ul>");
        clear();
        return sb.toString();
    }

    public String addImageToHTML(){
        Formatter sb = new Formatter();
        sb.format("<img id='imageTree' src='/pics/obrazok.png'>");
        return  sb.toString();
    }

    public int returnID(){
        return parser.getGenerateTree().getNumber();
    }


}
