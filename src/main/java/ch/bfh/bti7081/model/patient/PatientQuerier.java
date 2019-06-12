package ch.bfh.bti7081.model.patient;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.dossier.DossierQuerier;
import ch.bfh.bti7081.model.entities.Dossier;
import ch.bfh.bti7081.model.entities.Patient;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query patient related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class PatientQuerier {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Returns all patients
     *
     * TODO: Getting all data in memory might be dangerous!
     *
     * @return List of Patients
     */
    public List<Patient> getAll() {
        return transaction.getPatientRepo().getAll();
    }

    /**
     * Get a specific patient by its ID
     * @param id ObjectId
     * @return Patient
     */
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

    public Patient getByReport(ObjectId reportId, DossierQuerier dossierQuerier){
        return getAll()
                .stream()
                .filter(x -> dossierQuerier.getReports(x.getDossierId(), 10).stream().anyMatch(y -> y.getId().equals(reportId)))
                .findFirst()
                .get();
    }
}
