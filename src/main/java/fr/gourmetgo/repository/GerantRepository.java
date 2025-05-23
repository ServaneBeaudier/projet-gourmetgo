package fr.gourmetgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.gourmetgo.entity.Gerant;

/**
 * Interface de dépôt pour gérer les opérations de base de données liées aux gérants.
 * Cette interface étend JpaRepository pour fournir des méthodes CRUD pour l'entité Gerant.
 */
@Repository
public interface GerantRepository extends JpaRepository<Gerant, Long> {
}
