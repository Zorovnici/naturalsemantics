package compiler.bakalarka.parser;

import java.util.ArrayList;
import java.util.LinkedList;

public class VariableClass {
    private LinkedList<VariablesList> variableListFinal = new LinkedList<>();
    public class VariablesList{
        String variableName;
        int variableValue;
        int state;
        public VariablesList(String variableName,int variableValue, int state){
            this.variableName = variableName;
            this.variableValue = variableValue;
            this.state = state;
          }

        public String getVariableName() {
            return variableName;
        }

        public int getVariableValue() {
            return variableValue;
        }

        public int getState() {
            return state;
        }
    }

    public void addVariable(String name,int value, int state){
        variableListFinal.add(new VariablesList(name,value,state));
    }

    public LinkedList<VariablesList> getVariableListFinal() {
        return variableListFinal;
    }
}
