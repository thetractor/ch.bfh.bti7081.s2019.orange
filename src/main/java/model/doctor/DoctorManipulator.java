package model.doctor;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Doctor;
import model.entities.Message;
import model.entities.Objective;
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
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Doctor build(String name, String surname){
        Doctor obj = new Doctor(name,surname, new ArrayList<>());
        transaction.getDoctorRepo().set(obj);
        transaction.commit();
        return obj;
    }

    public void delete(ObjectId entity) {
        Doctor obj = transaction.getDoctorRepo().get(entity);
        transaction.getDoctorRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Doctor> lst = entities.stream().map(x -> transaction.getDoctorRepo().get(x)).collect(Collectors.toList());
        transaction.getDoctorRepo().deleteMany(lst);
        transaction.commit();
    }
}
