package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.objective.ObjectiveQuerier;
import ch.bfh.bti7081.model.patient.PatientQuerier;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Implementation of the ObjectivePresenter
 * @author gian.demarmelsz@students.bfh.ch
 */
public class ObjectivePresenter {
    private ObjectiveQuerier querier = new ObjectiveQuerier();
    private PatientQuerier patientQuerier = new PatientQuerier();

    /**
     * Get objectives corresponding to a patient
     * @param patientId ObjectId
     * @return List of objective objects
     */
    public List<Objective> getObjectives(ObjectId patientId) {
        return  querier.getByPatient(patientId);
    }

    /**
     * Overloaded method.
     * Get sub-objectives by parent corresponding to a patient
     * @param patientId ObjectId
     * @param parent ObjectId
     * @return List of objective objects
     */
    public List<Objective> getObjectives(ObjectId patientId, ObjectId parent) {
        return  querier.getByPatient(patientId,parent);
    }

    /**
     * Get objective by its id
     * @param objectId ObjectId
     * @return Objective object
     */
    public Objective getObjective(ObjectId objectId) {
        return querier.get(objectId);
    }

    /**
     * Get the patient the objective is assigned to.
     * @param objectId ObjectId
     * @return Patient
     */
    public Patient getPatient(ObjectId objectId) {
        return patientQuerier.get(objectId);
    }
}
