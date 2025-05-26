package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.GerantRepository;
import fr.gourmetgo.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GerantControllerTest {

    @Mock
    private GerantRepository gerantRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private GerantController gerantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGerantLogin_Success() {
        String email = "testgerant@mail.com";
        String plainPassword = "motdepasse";
        String hashedPassword = "hashedPass456";

        // Mock du hashage du mot de passe
        when(passwordService.hashPassword(plainPassword)).thenReturn(hashedPassword);

        // Capture de l'objet Gerant sauvegardé
        ArgumentCaptor<Gerant> gerantCaptor = ArgumentCaptor.forClass(Gerant.class);

        // Appel de la méthode à tester
        String result = gerantController.gerantLogin(email, plainPassword);

        // Vérification de la redirection
        assertEquals("redirect:/login", result);

        // Vérifie que save() a bien été appelé avec un gérant qui a le bon email et mot de passe hashé
        verify(gerantRepository).save(gerantCaptor.capture());

        Gerant savedGerant = gerantCaptor.getValue();
        assertEquals(email, savedGerant.getEmail());
        assertEquals(hashedPassword, savedGerant.getMotDePasse());
        assertEquals("", savedGerant.getNom());
        assertEquals("", savedGerant.getPrenom());
    }
}
