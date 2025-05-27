package fr.gourmetgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import fr.gourmetgo.entity.Gerant;

/**
 * Interface de dépôt pour gérer les opérations de base de données liées aux gérants.
 * Cette interface étend JpaRepository pour fournir des méthodes CRUD pour l'entité Gerant.
 * Elle facilite l'accès et la manipulation des données gérant dans la base de données.
 */
@Repository
public interface GerantRepository extends JpaRepository<Gerant, Long> {

    /**
     * Trouve un gérant par son email.
     *
     * @param email L'email du gérant à rechercher.
     * @return Un objet Optional contenant le gérant trouvé, ou vide si aucun gérant n'est trouvé.
     */
    Optional<Gerant> findByEmail(String email);
}
