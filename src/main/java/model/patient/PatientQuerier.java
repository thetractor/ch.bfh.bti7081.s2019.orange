package model.patient;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Dossier;
import model.entities.Patient;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query patient related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class PatientQuerier {
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());
    public List<Patient> getAll() {
        return transaction.getPatientRepo().getAll();
    }

    public Patient get(ObjectId id) {
        return transaction.getPatientRepo().get(id);
    }

    /**
     * returns the dossier from a specific patient
     * @param id from patient
     * @return Dossier from patient
     */
    public Dossier getDossier(ObjectId id){
        ObjectId dossierId = this.get(id).getDossierId();
        return transaction.getDossierRepo().get(dossierId);
    }
}
