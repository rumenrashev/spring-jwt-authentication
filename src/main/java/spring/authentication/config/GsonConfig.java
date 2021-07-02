package spring.authentication.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;

public class GsonConfig {

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .create();
    }

}
