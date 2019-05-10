package model.Entities;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Dossier implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }

    public Dossier(ObjectId patientId) {
        this.patientId = patientId;
    }

    public Dossier(){

    }

    private ObjectId patientId;

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public ObjectId getPatientId() {
        return patientId;
    }

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
}
