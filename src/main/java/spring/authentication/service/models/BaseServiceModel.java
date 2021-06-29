package spring.authentication.service.models;

import java.util.Objects;

public abstract class BaseServiceModel {

    private String id;

    public BaseServiceModel() { }

    public String getId() {
        return id;
    }

    public BaseServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseServiceModel that = (BaseServiceModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Id : " + this.id;
    }
}
