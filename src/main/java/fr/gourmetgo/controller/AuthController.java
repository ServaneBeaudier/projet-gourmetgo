package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.gourmetgo.service.AuthService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home() {
    return "home";
    }

    @GetMapping("/login2")
    public String loginForm() {
    return "login2";
    }

    @PostMapping("/login2")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (authService.authenticate(username, password)) {
        session.setAttribute("user", authService.findUser(username));
        return "redirect:/dashboard";
    } else {
        model.addAttribute("error", "Identifiants invalides !");
        return "login2";
        }
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
        return "redirect:/login2";
        }
        return "dashboard";
        }
        
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login2";
    }
}