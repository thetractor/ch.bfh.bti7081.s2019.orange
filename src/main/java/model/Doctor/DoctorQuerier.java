package model.Doctor;

import model.Entities.Doctor;
import model.Querier;
import model.UnitOfWork;
import org.bson.types.ObjectId;

import java.util.List;

public class DoctorQuerier extends Querier<Doctor> {
    @Override
    public List<Doctor> getAll() {
        return transaction.getDoctorRepo().getAll();
    }

    @Override
    public Doctor get(ObjectId id) {
        return transaction.getDoctorRepo().get(id);
    }
}
