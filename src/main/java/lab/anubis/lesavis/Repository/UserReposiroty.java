package lab.anubis.lesavis.Repository;

import lab.anubis.lesavis.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserReposiroty extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
