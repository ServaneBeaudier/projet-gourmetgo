package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.repository.ClientRepository;
import fr.gourmetgo.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testClientLogin_Success() {
        String email = "testclient@mail.com";
        String plainPassword = "monMotDePasse";
        String hashedPassword = "hashedPassword123";

        // Quand passwordService hash le mot de passe, on retourne une valeur simulée
        when(passwordService.hashPassword(plainPassword)).thenReturn(hashedPassword);

        // On capture l'objet Client passé à clientRepository.save
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        // Appel de la méthode à tester
        String result = clientController.clientLogin(email, plainPassword);

        // Vérifier que la méthode renvoie bien la redirection attendue
        assertEquals("redirect:/login", result);

        // Vérifier que clientRepository.save a été appelé avec un client qui a bien l'email et le mot de passe hashé
        verify(clientRepository).save(clientCaptor.capture());

        Client savedClient = clientCaptor.getValue();
        assertEquals(email, savedClient.getEmail());
        assertEquals(hashedPassword, savedClient.getMotDePasse());
        assertEquals("", savedClient.getNom());
        assertEquals("", savedClient.getPrenom());
    }
}
