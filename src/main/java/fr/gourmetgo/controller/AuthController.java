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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/auth-login")
    public String login(@RequestParam String email, @RequestParam String motDePasse, HttpSession session, Model model) {
        if (authService.authenticate(email, motDePasse)) {
            session.setAttribute("user", authService.findUser(email));
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Identifiants invalides !");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}