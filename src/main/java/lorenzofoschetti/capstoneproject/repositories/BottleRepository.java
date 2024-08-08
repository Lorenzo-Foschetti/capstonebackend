package lorenzofoschetti.capstoneproject.repositories;

import lorenzofoschetti.capstoneproject.entities.Bottle;
import lorenzofoschetti.capstoneproject.enums.BottleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BottleRepository extends JpaRepository<Bottle, UUID> {
    @Query("SELECT b FROM Bottle b WHERE b.bottleCategory = :bottleCategory ")
    Page<Bottle> filterByBottleCategory(@Param("bottleCategory") BottleCategory bottleCategory, Pageable pageable);
}
