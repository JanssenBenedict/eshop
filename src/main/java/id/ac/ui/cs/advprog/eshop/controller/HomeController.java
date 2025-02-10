package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "ADV Shop");
        model.addAttribute("welcomeMessage", "Welcome");
        return "homePage"; // This will look for home.html in templates
    }
}