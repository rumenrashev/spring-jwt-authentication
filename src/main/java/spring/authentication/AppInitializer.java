package spring.authentication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.authentication.data.entities.AuthorityEntity;
import spring.authentication.data.entities.AuthorityEnum;
import spring.authentication.data.repositories.AuthorityRepository;

import javax.transaction.Transactional;
import java.util.EnumSet;

@Component
public class AppInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    public AppInitializer(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        EnumSet.allOf(AuthorityEnum.class)
                .stream()
                .filter(a-> !this.authorityRepository.existsByAuthority(a))
                .map(a-> new AuthorityEntity().setAuthority(a))
                .forEach(this.authorityRepository::save);
    }
}
