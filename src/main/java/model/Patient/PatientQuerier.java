package model.Patient;

import model.Entities.Doctor;
import model.Entities.Patient;
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
}
