package fr.gourmetgo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Utilisateur;


@Repository
public interface AuthRepository extends JpaRepository<Administrateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
}