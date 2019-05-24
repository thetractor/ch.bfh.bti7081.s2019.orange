package model.doctor;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Doctor;
import model.entities.Patient;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API to Query doctor related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class DoctorQuerier{
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Doctor> getAll() {
        return transaction.getDoctorRepo().getAll();
    }

    public Doctor get(ObjectId id) {
        return transaction.getDoctorRepo().get(id);
    }

    /**
     *
     * @param id doctor Objectid
     * @return list of patients from a doctor
     */
    public List<Patient> getPatients(ObjectId id){
        return this.get(id)
                .getPatients()
                .stream()
                .map(x -> transaction.getPatientRepo().get(x))
                .collect(Collectors.toList());
    }
}
