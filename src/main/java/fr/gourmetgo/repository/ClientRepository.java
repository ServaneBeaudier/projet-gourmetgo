package fr.gourmetgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.gourmetgo.entity.Client;

/**
 * Interface de dépôt pour gérer les opérations de base de données liées aux clients.
 * Cette interface étend JpaRepository pour fournir des méthodes CRUD pour l'entité Client.
 * Elle facilite l'accès et la manipulation des données client dans la base de données.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
