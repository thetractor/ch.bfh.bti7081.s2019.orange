package model.Entities;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

public class Doctor implements Serializable, IEntity {
    public Doctor(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    public Doctor(){

    }
    private ObjectId id;
    private String name;
    private String surname;

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(name, doctor.name) &&
                Objects.equals(surname, doctor.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
