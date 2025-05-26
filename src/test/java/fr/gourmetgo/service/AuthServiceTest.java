package fr.gourmetgo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.gourmetgo.entity.Utilisateur;
import fr.gourmetgo.repository.AuthRepository;

public class AuthServiceTest {

    private AuthRepository authRepository;
    private PasswordService passwordService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authRepository = mock(AuthRepository.class);
        passwordService = mock(PasswordService.class);
        authService = new AuthService(authRepository, passwordService);
    }

    @Test
    void authenticate_Successful() {
        String email = "user@example.com";
        String inputPassword = "password";
        String storedPassword = "hashedPassword";

        Utilisateur utilisateur = new Utilisateur() {
            {
                setEmail(email);
                setMotDePasse(storedPassword);
            }
        };

        when(authRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));
        when(passwordService.checkPassword(inputPassword, storedPassword)).thenReturn(true);

        boolean result = authService.authenticate(email, inputPassword);

        assertTrue(result);
        verify(authRepository).findByEmail(email);
        verify(passwordService).checkPassword(inputPassword, storedPassword);
    }

    @Test
    void authenticate_Fail_UserNotFound() {
        String email = "nouser@example.com";
        String inputPassword = "password";

        when(authRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = authService.authenticate(email, inputPassword);

        assertFalse(result);
        verify(authRepository).findByEmail(email);
        verifyNoInteractions(passwordService);
    }

    @Test
    void authenticate_Fail_WrongPassword() {
        String email = "user@example.com";
        String inputPassword = "wrongpassword";
        String storedPassword = "hashedPassword";

        Utilisateur utilisateur = new Utilisateur() {
            {
                setEmail(email);
                setMotDePasse(storedPassword);
            }
        };

        when(authRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));
        when(passwordService.checkPassword(inputPassword, storedPassword)).thenReturn(false);

        boolean result = authService.authenticate(email, inputPassword);

        assertFalse(result);
        verify(authRepository).findByEmail(email);
        verify(passwordService).checkPassword(inputPassword, storedPassword);
    }

    @Test
    void findUser_UserExists() {
        String email = "user@example.com";

        Utilisateur utilisateur = new Utilisateur() {
            {
                setEmail(email);
            }
        };

        when(authRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));

        Utilisateur found = authService.findUser(email);

        assertNotNull(found);
        assertEquals(email, found.getEmail());
        verify(authRepository).findByEmail(email);
    }

    @Test
    void findUser_UserNotFound() {
        String email = "nouser@example.com";

        when(authRepository.findByEmail(email)).thenReturn(Optional.empty());

        Utilisateur found = authService.findUser(email);

        assertNull(found);
        verify(authRepository).findByEmail(email);
    }
}
