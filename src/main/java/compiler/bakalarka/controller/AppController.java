package compiler.bakalarka.controller;

import compiler.bakalarka.web.WebUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Controller
public class AppController {

    private WebUI webUI = new WebUI();

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String web(Model model) {
        //model.addAttribute("webUI",webUI);
        return "main";
    }

    @GetMapping("/codeServlet")
    public String servlet(@RequestParam("code")String code, Model model) {
        model.addAttribute("code",code);
        model.addAttribute("web",webUI);
        return "codeServlet";
    }

    @RequestMapping(value = "/pics/obrazok.png",method = RequestMethod.GET)
    public void showImage(HttpServletResponse response) throws Exception {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            File file = new File("src/main/resources/static/pictures/latex" + webUI.returnID() + ".png");
            BufferedImage image = ImageIO.read(file);
            ImageIO.write(image, "png", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        byte[] imgByte = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
