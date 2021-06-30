package spring.authentication.web.models;

import java.util.Set;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private Set<String> roles;

    public JwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public JwtResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getType() {
        return type;
    }

    public JwtResponse setType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public JwtResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public JwtResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JwtResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public JwtResponse setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }
}
