package spring.authentication.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class AuthorityEntity extends BaseEntity {

    @Column(nullable = false,unique = true)
    private AuthorityEnum authority;

    public AuthorityEntity() { }

    public AuthorityEnum getAuthority() {
        return authority;
    }

    public AuthorityEntity setAuthority(AuthorityEnum authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthorityEntity that = (AuthorityEntity) o;
        return authority == that.authority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authority);
    }

    @Override
    public String toString() {
        return String.format("AuthorityEntity : {%s , authority : %s}",
                super.toString(),
                this.authority);
    }
}
