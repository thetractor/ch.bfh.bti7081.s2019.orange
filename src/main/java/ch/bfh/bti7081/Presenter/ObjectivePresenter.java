package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.objective.ObjectiveQuerier;
import ch.bfh.bti7081.model.patient.PatientQuerier;
import org.bson.types.ObjectId;

import java.util.List;

public class ObjectivePresenter {
    private ObjectiveQuerier querier = new ObjectiveQuerier();
    private PatientQuerier patientQuerier = new PatientQuerier();

    public List<Objective> getObjectives(ObjectId id) {
        return  querier.getByPatient(id);
    }

    public List<Objective> getObjectives(ObjectId id, ObjectId parent) {
        return  querier.getByPatient(id,parent);
    }

    public Objective getObjective(ObjectId objectId) {
        return querier.get(objectId);
    }

    public Patient getPatient(ObjectId objectId) {
        return patientQuerier.get(objectId);
    }
}
