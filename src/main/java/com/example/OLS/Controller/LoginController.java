package com.example.OLS.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class LoginController {

    @GetMapping("/OLS/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }
        return "login"; // login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // home.html
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/home";   // this will load templates/admin/home.html
    }
}
