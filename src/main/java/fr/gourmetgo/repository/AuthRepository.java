package fr.gourmetgo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gourmetgo.entity.Utilisateur;

/**
 * Interface de dépôt pour gérer les opérations de base de données liées à l'authentification des utilisateurs.
 * Cette interface étend JpaRepository pour fournir des méthodes CRUD et des requêtes personnalisées pour l'entité Utilisateur.
 */
@Repository
public interface AuthRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Trouve un utilisateur par son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     * @return Un objet Optional contenant l'utilisateur trouvé, ou vide si aucun utilisateur n'est trouvé.
     */
    Optional<Utilisateur> findByEmail(String email);
}
