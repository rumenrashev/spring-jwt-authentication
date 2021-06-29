package spring.authentication.service.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class GrantedAuthorityServiceModel extends BaseServiceModel implements GrantedAuthority {

    private String id;
    private String authority;

    public GrantedAuthorityServiceModel() { }

    public String getId() {
        return id;
    }

    public GrantedAuthorityServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public GrantedAuthorityServiceModel setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantedAuthorityServiceModel that = (GrantedAuthorityServiceModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }

    @Override
    public String toString() {
        return String.format("GrantedAuthorityImpl{%s , authority : %s }",
                super.toString(),
                this.authority);
    }
}
