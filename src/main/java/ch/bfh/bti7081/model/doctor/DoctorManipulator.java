package ch.bfh.bti7081.model.doctor;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Doctor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for manipulating models
 * Methods for creating, updating & deleting belong in this class
 * Other classes can be as this example
 * @author gian.demarmels@students.bfh.ch
 */
public class DoctorManipulator {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Creates a doctor object, saves it to the DB and returns the object.
     * @param name String
     * @param surname String
     * @return Doctor object
     */
    public Doctor build(String name, String surname){
        Doctor obj = new Doctor(name,surname, new ArrayList<>());
        transaction.getDoctorRepo().set(obj);
        transaction.commit();
        return obj;
    }

    /**
     * Deletes a Doctor by a given ID
     * @param entity ObjectId
     */
    public void delete(ObjectId entity) {
        Doctor obj = transaction.getDoctorRepo().get(entity);
        transaction.getDoctorRepo().delete(obj);
        transaction.commit();
    }

    /**
     * Deletes multiple doctors
     * @param entities List of doctor-IDs
     */
    public void deleteMany(List<ObjectId> entities) {
        List<Doctor> lst = entities.stream().map(x -> transaction.getDoctorRepo().get(x)).collect(Collectors.toList());
        transaction.getDoctorRepo().deleteMany(lst);
        transaction.commit();
    }
}
