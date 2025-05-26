package fr.gourmetgo.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Création d'un Validator à partir d'un ValidatorFactory par défaut
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Vérifie qu'une instance de Restaurant correctement renseignée ne génère aucune violation.
     */
    @Test
    public void testValidRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");  // Doit contenir 10 chiffres et commencer par 0
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        // imageResto et autreType ne sont pas obligatoires ici
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        assertTrue(violations.isEmpty(), "Aucune violation ne doit être présente pour un Restaurant valide");

        // Vérification des valeurs (et test des getters/setters)
        assertEquals("Restaurant A", restaurant.getNomResto(), "Le nom du restaurant doit être 'Restaurant A'");
        assertEquals("123", restaurant.getNumRue(), "Le numéro de rue doit être '123'");
        assertEquals("Rue de Paris", restaurant.getNomRue(), "Le nom de la rue doit être 'Rue de Paris'");      
        assertEquals("75001", restaurant.getCodePostal(), "Le code postal doit être '75001'");
        assertEquals("Paris", restaurant.getVille(), "La ville doit être 'Paris'");
        assertEquals("0123456789", restaurant.getTelResto(), "Le numéro de téléphone doit être '0123456789'");
        assertEquals("Sushi", restaurant.getTypeResto(), "Le type de restaurant doit être 'Sushi'");
        assertEquals(LocalTime.of(9, 0), restaurant.getHeureOuverture(), "L'heure d'ouverture doit être 09:00");
        assertEquals(LocalTime.of(22, 0), restaurant.getHeureFermeture(), "L'heure de fermeture doit être 22:00");
        
        
    }

    /**
     * Vérifie qu'une violation est générée si le nom du restaurant est vide.
     */
    @Test
    public void testNomRestoBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto(""); // Invalide en raison de @NotBlank
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        assertFalse(violations.isEmpty(), "Une violation doit être générée lorsque le nom du restaurant est vide");

        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nomResto")
                        && v.getMessage().equals("Veuillez renseigner le nom du restaurant"));
        assertTrue(found, "La contrainte sur 'nomResto' n'a pas été détectée correctement");
    }
    
    /**
     * Vérifie qu'une violation est générée si le champ numRue est vide.
     */
    @Test
    public void testNumRueBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue(""); // Invalide
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("numRue")
                        && v.getMessage().equals("Veuillez renseigner le numéro de rue"));
        assertTrue(found, "La contrainte sur 'numRue' n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée si le champ nomRue est vide.
     */
    @Test
    public void testNomRueBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue(""); // Invalide
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nomRue")
                        && v.getMessage().equals("Veuillez renseigner la rue"));
        assertTrue(found, "La contrainte sur 'nomRue' n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée si le code postal est vide.
     */
    @Test
    public void testCodePostalBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal(""); // Invalide
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("codePostal")
                        && v.getMessage().equals("Veuillez renseigner le code postal"));
        assertTrue(found, "La contrainte sur 'codePostal' n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée si la ville est vide.
     */
    @Test
    public void testVilleBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille(""); // Invalide
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("ville")
                        && v.getMessage().equals("Veuillez renseigner la ville"));
        assertTrue(found, "La contrainte sur 'ville' n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée pour un numéro de téléphone au format invalide.
     * Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.
     */
    @Test
    public void testTelRestoInvalidFormat() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        // Numéro invalide : moins de 10 chiffres ou ne commençant pas par 0
        restaurant.setTelResto("123456789"); 
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        assertFalse(violations.isEmpty(), "Une violation doit être générée pour un numéro de téléphone invalide");
        
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("telResto")
                        && v.getMessage().equals("Le numéro de téléphone doit contenir 10 chiffres et commencer par 0."));
        assertTrue(found, "La contrainte sur 'telResto' (pattern) n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée si le type du restaurant est vide.
     */
    @Test
    public void testTypeRestoBlank() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto(""); // Invalide
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("typeResto")
                        && v.getMessage().equals("Veuillez renseigner le type du restaurant."));
        assertTrue(found, "La contrainte sur 'typeResto' n'a pas été détectée");
    }
    
    /**
     * Vérifie qu'une violation est générée si l'heure d'ouverture est null.
     */
    @Test
    public void testHeureOuvertureNull() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(null); // Invalide
        restaurant.setHeureFermeture(LocalTime.of(22, 0));
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("heureOuverture")
                        && v.getMessage().equals("Veuillez renseigner l'horaire d'ouverture"));
        assertTrue(found, "La contrainte sur 'heureOuverture' n'a pas été respectée lorsque null");
    }
    
    /**
     * Vérifie qu'une violation est générée si l'heure de fermeture est null.
     */
    @Test
    public void testHeureFermetureNull() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(null); // Invalide
        
        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant);
        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("heureFermeture")
                        && v.getMessage().equals("Veuillez renseigner l'horaire de fermeture"));
        assertTrue(found, "La contrainte sur 'heureFermeture' n'a pas été respectée lorsque null");
    }
}
