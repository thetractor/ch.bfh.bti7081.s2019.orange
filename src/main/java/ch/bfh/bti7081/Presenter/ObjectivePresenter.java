package ch.bfh.bti7081.Presenter;

import model.entities.Objective;
import model.entities.Patient;
import model.objective.ObjectiveQuerier;
import model.patient.PatientQuerier;
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
