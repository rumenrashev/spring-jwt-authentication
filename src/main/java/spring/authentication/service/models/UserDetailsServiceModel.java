package spring.authentication.service.models;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsServiceModel extends BaseServiceModel implements UserDetails {

    private String username;
    private String password;
    private Set<GrantedAuthorityServiceModel> authorities;

    public UserDetailsServiceModel() { }

    @Override
    public String getUsername() {
        return username;
    }

    public UserDetailsServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserDetailsServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public Set<GrantedAuthorityServiceModel> getAuthorities() {
        return authorities;
    }

    public UserDetailsServiceModel setAuthorities(Set<GrantedAuthorityServiceModel> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDetailsServiceModel that = (UserDetailsServiceModel) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }

    @Override
    public String toString() {
        return String.format("UserDetailsImpl : {%s , username : %s , authorities : %s}",
                super.toString(),
                this.username,
                this.authorities
                        .stream()
                        .map(GrantedAuthorityServiceModel::getAuthority)
                        .collect(Collectors.joining(", ")));
    }
}
