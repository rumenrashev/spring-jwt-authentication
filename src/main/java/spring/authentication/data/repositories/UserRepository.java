package spring.authentication.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.authentication.data.entities.AuthorityEnum;
import spring.authentication.data.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
