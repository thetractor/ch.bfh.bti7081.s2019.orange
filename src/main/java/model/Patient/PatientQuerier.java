package model.patient;

import model.entities.Dossier;
import model.entities.Patient;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

public class PatientQuerier extends Querier<Patient> {
    @Override
    public List<Patient> getAll() {
        return transaction.getPatientRepo().getAll();
    }

    @Override
    public Patient get(ObjectId id) {
        return transaction.getPatientRepo().get(id);
    }

    public Dossier getDossier(ObjectId id){
        ObjectId dossierId = this.get(id).getDossierId();
        return transaction.getDossierRepo().get(dossierId);
    }
}
