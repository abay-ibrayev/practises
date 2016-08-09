package kz.gbk.eprocurement.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("version", "version");
        return "admin";
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("version", "version");
        return "main";
    }
}
