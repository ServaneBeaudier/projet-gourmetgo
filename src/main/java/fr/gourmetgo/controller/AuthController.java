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

/**
 * Contrôleur pour gérer les opérations d'authentification des utilisateurs.
 * Ce contrôleur gère les requêtes liées à la connexion, la déconnexion et l'accès aux tableaux de bord des utilisateurs.
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Affiche le formulaire de connexion.
     *
     * @return Le nom de la vue du formulaire de connexion.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Authentifie un utilisateur avec l'email et le mot de passe fournis.
     *
     * @param email L'email de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param session La session HTTP pour stocker les informations de l'utilisateur.
     * @param model Le modèle pour ajouter des attributs à la vue.
     * @return Une redirection vers le tableau de bord approprié ou le formulaire de connexion en cas d'échec.
     */
    @PostMapping("/auth-login")
    public String login(@RequestParam String email, @RequestParam String motDePasse, HttpSession session, Model model) {
        if (authService.authenticate(email, motDePasse)) {
            Utilisateur user = authService.findUser(email);
            session.setAttribute("user", user);

            // Rediriger en fonction du type d'utilisateur
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

    /**
     * Affiche le tableau de bord de l'utilisateur.
     *
     * @param session La session HTTP pour vérifier si l'utilisateur est connecté.
     * @return Le nom de la vue du tableau de bord ou une redirection vers le formulaire de connexion.
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }

    /**
     * Affiche le tableau de bord de l'administrateur.
     *
     * @param session La session HTTP pour vérifier si l'utilisateur est connecté.
     * @return Le nom de la vue du tableau de bord de l'administrateur ou une redirection vers le formulaire de connexion.
     */
/*     @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }
 */
    /**
     * Affiche le tableau de bord du gérant.
     *
     * @param session La session HTTP pour vérifier si l'utilisateur est connecté.
     * @return Le nom de la vue du tableau de bord du gérant ou une redirection vers le formulaire de connexion.
     */
    @GetMapping("/gerant-dashboard")
    public String gerantDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "gerant-dashboard";
    }

    /**
     * Affiche le tableau de bord du client.
     *
     * @param session La session HTTP pour vérifier si l'utilisateur est connecté.
     * @return Le nom de la vue du tableau de bord du client ou une redirection vers le formulaire de connexion.
     */
    @GetMapping("/client-dashboard")
    public String clientDashboard(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "client-dashboard";
    }

    /**
     * Déconnecte l'utilisateur en invalidant la session.
     *
     * @param session La session HTTP à invalider.
     * @return Une redirection vers le formulaire de connexion.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
