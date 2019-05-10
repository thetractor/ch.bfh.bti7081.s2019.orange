package model.Entities;

import org.bson.types.ObjectId;

public class Patient implements IEntity {
    private ObjectId id;

    private String name;
    private String surname;

    public Patient(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    public ObjectId getId() {
        return id;
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
