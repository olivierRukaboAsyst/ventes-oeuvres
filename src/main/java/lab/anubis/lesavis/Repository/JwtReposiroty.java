package lab.anubis.lesavis.Repository;

import lab.anubis.lesavis.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtReposiroty extends JpaRepository<Jwt, Integer> {
}
