package fr.gourmetgo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PasswordServiceTest {

    private final PasswordService passwordService = new PasswordService();

    @Test
    void hashPassword_ShouldReturnNonNullHash() {
        String plainPassword = "monMotDePasse123";
        String hashed = passwordService.hashPassword(plainPassword);

        assertNotNull(hashed);
        assertNotEquals(plainPassword, hashed);  // Le hash ne doit pas être identique au mot de passe clair
    }

    @Test
    void checkPassword_ShouldReturnTrueForMatchingPasswords() {
        String plainPassword = "motDePasseTest";
        String hashed = passwordService.hashPassword(plainPassword);

        assertTrue(passwordService.checkPassword(plainPassword, hashed));
    }

    @Test
    void checkPassword_ShouldReturnFalseForNonMatchingPasswords() {
        String plainPassword = "motDePasseTest";
        String hashed = passwordService.hashPassword(plainPassword);

        assertFalse(passwordService.checkPassword("mauvaisMotDePasse", hashed));
    }
}
