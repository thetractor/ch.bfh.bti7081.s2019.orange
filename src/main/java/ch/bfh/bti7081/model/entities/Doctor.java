package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Doctor entity
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Doctor implements Serializable, IEntity {

    private ObjectId id;
    private String name;
    private String surname;
    private List<ObjectId> patients;

    /**
     * Constructor
     * @param name String
     * @param surname String
     * @param patients List of patient-IDs
     */
    public Doctor(String name, String surname, List<ObjectId> patients){
        this.name = name;
        this.surname = surname;
        this.patients = patients;
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Doctor(){

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
    public void setId(final ObjectId id) {
        this.id = id;
    }

    /**
     * Returns the Doctors name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the doctors name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Doctors surname
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the Doctors name
     * @param surname String
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the doctors full name
     * @return String
     */
    public String getFullName(){
        return getName() + " " + getSurname();
    }

    /**
     * Returns all patients of a doctor
     * @return List of patient-Ids
     */
    public List<ObjectId> getPatients() {
        return patients;
    }

    /**
     * Assigns multiple patients to a doctor
     * @param patients List of patient-Ids
     */
    public void setPatients(List<ObjectId> patients) {
        this.patients = patients;
    }

    /**
     * Assigns a specific patient to a doctor
     * @param patient Patient
     */
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

    @Override
    public String toString(){
        return String.format("Doctor(%s, %s, %s)", name, surname, id);
    }
}
