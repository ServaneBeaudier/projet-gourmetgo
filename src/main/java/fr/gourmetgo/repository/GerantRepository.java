package fr.gourmetgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.gourmetgo.entity.Gerant;

@Repository
public interface GerantRepository extends JpaRepository<Gerant, Long> {
}

