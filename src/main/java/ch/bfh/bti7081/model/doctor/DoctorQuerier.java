package ch.bfh.bti7081.model.doctor;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Doctor;
import ch.bfh.bti7081.model.entities.Patient;
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

    /**
     * Returns all doctors
     *
     * TODO: Getting all data in memory might be dangerous!
     *
     * @return List of Doctors
     */
    public List<Doctor> getAll() {
        return transaction.getDoctorRepo().getAll();
    }

    /**
     * Get a specific doctor by its ID
     * @param id ObjectId
     * @return Doctor object
     */
    public Doctor get(ObjectId id) {
        return transaction.getDoctorRepo().get(id);
    }

    /**
     * Returns a list of patients, who the doctor cares for
     * @param id doctor ObjectId
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
