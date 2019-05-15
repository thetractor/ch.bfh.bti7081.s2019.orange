package model.entities;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Patient implements IEntity {
    private ObjectId id;

    private String name;
    private String surname;
    private ObjectId dossierId;

    public Patient(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public Patient(){

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public ObjectId getDossierId() {
        return dossierId;
    }

    public void setDossierId(ObjectId dossierId) {
        this.dossierId = dossierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(name, patient.name) &&
                Objects.equals(surname, patient.surname) &&
                Objects.equals(dossierId, patient.dossierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, dossierId);
    }
}
