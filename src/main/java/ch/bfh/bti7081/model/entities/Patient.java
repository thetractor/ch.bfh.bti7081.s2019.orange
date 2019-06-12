package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Implementation of the Patient entity
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Patient implements IEntity {

    private ObjectId id;
    private String name;
    private String surname;
    private ObjectId dossierId;

    public Patient(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Patient(){

    }

    /**
     * Returns the entities Id
     * @return ObjectId
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets the entities ID
     * This method is needed in order to create an object out of the database, it has to be public to do that.
     *
     * TODO: Encapsulate this so the method is not publicly accessible
     *
     * @param id ObjectId
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * gets the patients name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * sets the patients name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the patients surname
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * sets the patients surname
     * @param surname String
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * get the dossier id, which is assigned to the patient
     * @return ObjectId
     */
    public ObjectId getDossierId() {
        return dossierId;
    }

    /**
     * set patients dossier
     * @param dossierId ObjectId
     */
    public void setDossierId(ObjectId dossierId) {
        this.dossierId = dossierId;
    }

    /**
     * returns the patients full name
     * @return
     */
    public String getFullName(){
        return getName() + " " + getSurname();
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

    @Override
    public String toString(){
        return String.format("Patient(%s, %s, %s)", name, surname, id);
    }
}
