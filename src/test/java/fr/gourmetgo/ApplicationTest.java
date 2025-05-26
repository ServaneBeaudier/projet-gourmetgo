package fr.gourmetgo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.AuthRepository;
import fr.gourmetgo.service.PasswordService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private Application application;

    @Test
    public void testInit() {
    // Arrange
    when(passwordService.hashPassword("admin123")).thenReturn("hashedAdminPassword");
    when(passwordService.hashPassword("dede123")).thenReturn("hashedGerantPassword");
    when(passwordService.hashPassword("vincent123")).thenReturn("hashedClientPassword");

    // Act
    CommandLineRunner commandLineRunner = application.init(authRepository, passwordService);
    try {
        commandLineRunner.run();
    } catch (Exception e) {
        // Gérer l'exception ou simplement l'ignorer si ce n'est pas nécessaire pour le test
        e.printStackTrace();
    }

    // Assert
    verify(authRepository, times(1)).save(new Administrateur("ad", "min", "admin@gg.com", "hashedAdminPassword"));
    verify(authRepository, times(1)).save(new Gerant("ge", "rant", "dede@gg.com", "hashedGerantPassword"));
    verify(authRepository, times(1)).save(new Client("cl", "ient", "vincent@gg.com", "hashedClientPassword"));
    }

}
