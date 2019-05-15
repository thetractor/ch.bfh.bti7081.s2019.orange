package model.doctors;

import model.entitiess.Doctor;
import model.entitiess.Patient;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorQuerier extends Querier<Doctor> {
    @Override
    public List<Doctor> getAll() {
        return transaction.getDoctorRepo().getAll();
    }

    @Override
    public Doctor get(ObjectId id) {
        return transaction.getDoctorRepo().get(id);
    }

    public List<Patient> getPatients(ObjectId id){
        return this.get(id)
                .getPatients()
                .stream()
                .map(x -> transaction.getPatientRepo().get(x))
                .collect(Collectors.toList());
    }
}
