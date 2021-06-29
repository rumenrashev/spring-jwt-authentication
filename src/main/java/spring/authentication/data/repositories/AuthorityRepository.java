package spring.authentication.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.authentication.data.entities.AuthorityEntity;
import spring.authentication.data.entities.AuthorityEnum;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity,String> {

    Optional<AuthorityEntity> getByAuthority(AuthorityEnum authority);

    boolean existsByAuthority(AuthorityEnum authority);

}
