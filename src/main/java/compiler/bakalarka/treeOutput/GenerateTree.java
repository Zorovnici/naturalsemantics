package compiler.bakalarka.treeOutput;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;

public class GenerateTree {

    private int number = 0;
    public void ImageGenerate(String code) {
        number++;
        String formula = code;
        System.out.println("formula kodu je: " + formula);
        TeXFormula teXFormula = new TeXFormula(formula);
        TeXIcon teXIcon = teXFormula.createTeXIcon(TeXConstants.STYLE_TEXT,13);
        teXIcon.setInsets(new Insets(5, 5, 5, 5));
        teXFormula.createPNG(TeXConstants.STYLE_DISPLAY,17,"C:\\Users\\Lenovo\\IdeaProjects\\bakalarka\\src\\main\\resources\\static\\pictures\\latex"+number+".png", Color.WHITE, Color.BLACK);
    }
    public int getNumber() {
        return number;
    }
}
