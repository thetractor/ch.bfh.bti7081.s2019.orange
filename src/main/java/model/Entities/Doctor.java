package model.Entities;

import model.IEntity;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class Doctor implements Serializable, IEntity {

    private ObjectId id;
    private String name;
    private String surname;

    public Doctor(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

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



}
