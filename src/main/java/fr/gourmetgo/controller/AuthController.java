package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.gourmetgo.service.AuthService;
import fr.gourmetgo.entity.Utilisateur;
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
            Utilisateur user = authService.findUser(email);
            session.setAttribute("user", user);

            // Rediriger en fonction du type d'utilisateur
            // Supposons que Utilisateur a une méthode getTypeUtilisateur()
            switch (user.getTypeUtilisateur()) {
                case "ADMIN":
                    return "redirect:/dashboard";
                case "GERANT":
                    return "redirect:/gerant-dashboard";
                case "CLIENT":
                    return "redirect:/client-dashboard";
                default:
                    return "redirect:/login";
            }
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

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "admin-dashboard";
    }

    @GetMapping("/gerant-dashboard")
    public String gerantDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "gerant-dashboard";
    }

    @GetMapping("/client-dashboard")
    public String clientDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "client-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
