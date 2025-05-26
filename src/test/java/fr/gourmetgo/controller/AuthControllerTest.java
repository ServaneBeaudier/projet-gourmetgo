package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.entity.Utilisateur;
import fr.gourmetgo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowLoginForm() {
        String viewName = authController.showLoginForm();
        assertEquals("login", viewName);
    }

    @Test
    void testLoginAsAdmin() {
        String email = "admin@gourmetgo.fr";
        String password = "admin123";
        Utilisateur admin = new Administrateur("Admin", "Root", email, password);

        when(authService.authenticate(email, password)).thenReturn(true);
        when(authService.findUser(email)).thenReturn(admin);

        String view = authController.login(email, password, session, model);

        verify(session).setAttribute("user", admin);
        assertEquals("redirect:/dashboard", view);
    }

    @Test
    void testLoginAsGerant() {
        String email = "gerant@mail.com";
        String password = "gerant123";
        Utilisateur gerant = new Gerant("Gerant", "Dupont", email, password);

        when(authService.authenticate(email, password)).thenReturn(true);
        when(authService.findUser(email)).thenReturn(gerant);

        String view = authController.login(email, password, session, model);

        verify(session).setAttribute("user", gerant);
        assertEquals("redirect:/gerant-dashboard", view);
    }

    @Test
    void testLoginAsClient() {
        String email = "client@mail.com";
        String password = "client123";
        Utilisateur client = new Client("Client", "Martin", email, password);

        when(authService.authenticate(email, password)).thenReturn(true);
        when(authService.findUser(email)).thenReturn(client);

        String view = authController.login(email, password, session, model);

        verify(session).setAttribute("user", client);
        assertEquals("redirect:/client-dashboard", view);
    }

    @Test
    void testLoginWithInvalidCredentials() {
        String email = "fake@mail.com";
        String password = "wrongpass";

        when(authService.authenticate(email, password)).thenReturn(false);

        String view = authController.login(email, password, session, model);

        verify(model).addAttribute(eq("error"), eq("Identifiants invalides !"));
        assertEquals("login", view);
    }

    @Test
    void testDashboardWithNoUserInSession() {
        when(session.getAttribute("user")).thenReturn(null);

        String view = authController.dashboard(session);
        assertEquals("redirect:/login", view);
    }

    @Test
    void testDashboardWithUserInSession() {
        Utilisateur admin = new Administrateur("Admin", "Root", "admin@gourmetgo.fr", "admin123");

        when(session.getAttribute("user")).thenReturn(admin);

        String view = authController.dashboard(session);
        assertEquals("dashboard", view);
    }

    @Test
    void testGerantDashboardWithUserInSession() {
        when(session.getAttribute("user")).thenReturn(new Gerant());
        assertEquals("gerant-dashboard", authController.gerantDashboard(session));
    }

    @Test
    void testClientDashboardWithUserInSession() {
        when(session.getAttribute("user")).thenReturn(new Client());
        assertEquals("client-dashboard", authController.clientDashboard(session));
    }

    @Test
    void testLogout() {
        String view = authController.logout(session);
        verify(session).invalidate();
        assertEquals("redirect:/login", view);
    }
}
