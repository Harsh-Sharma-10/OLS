package com.example.OLS.Controller;


import com.example.OLS.Model.User;
import com.example.OLS.services.UserservicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/OLS")
public class SignUpController {


    @Autowired
    private UserservicesImpl uServices;

    // Serve the signup form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // empty object for form binding
        return "register"; // Thymeleaf template name -> register.html
    }
    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        boolean exists = uServices.usernameExists(username);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username not available");
        } else {
            return ResponseEntity.ok(" Username available");
        }
    }


    // Handle form submit
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        // 🔹 Save user to DB (via repository)
        uServices.registerUser(user);

        // after saving redirect to success page or home
        return "redirect:/OLS/login";
    }

}
