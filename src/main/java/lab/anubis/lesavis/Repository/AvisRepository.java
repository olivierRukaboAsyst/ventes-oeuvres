package lab.anubis.lesavis.Repository;

import lab.anubis.lesavis.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
}
