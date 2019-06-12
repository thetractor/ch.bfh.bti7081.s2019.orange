package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Implementation of the Dossier entity
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Dossier implements IEntity {
    private ObjectId id;
    private ObjectId patientId;

    public Dossier(ObjectId patientId) {
        this.patientId = patientId;
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Dossier(){

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
     * Gets the ID of the patient assigned to the dossier
     * @return ObjectId of a patient
     */
    public ObjectId getPatientId() {
        return patientId;
    }

    /**
     * Assigns the dossier to a given patient
     * @param patientId ObjectId of a patient
     */
    public void setPatientId(ObjectId patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dossier dossier = (Dossier) o;
        return Objects.equals(patientId, dossier.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }

    @Override
    public String toString(){
        return String.format("Dossier(id=%s, patientID=%s)", id, patientId);
    }
}
