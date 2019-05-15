package model.entities;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Doctor implements Serializable, IEntity {
    public Doctor(String name, String surname, List<ObjectId> patients){
        this.name = name;
        this.surname = surname;
        this.patients = patients;
    }

    public Doctor(){

    }

    private ObjectId id;
    private String name;
    private String surname;
    private List<ObjectId> patients;

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

    public List<ObjectId> getPatients() {
        return patients;
    }

    public void setPatients(List<ObjectId> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient){
        patients.add(patient.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(name, doctor.name) &&
                Objects.equals(surname, doctor.surname) &&
                Objects.equals(patients, doctor.patients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patients);
    }
}
