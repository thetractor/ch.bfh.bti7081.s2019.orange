package model.patient;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Dossier;
import model.entities.Patient;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class for manipulating models
 * Methods for creating, updating & deleting belong in this class
 * Other classes can be as this example
 * @author gian.demarmels@students.bfh.ch
 */
public class PatientManipulator {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Patient build(String name, String surname){
        Patient obj = new Patient(name, surname);
        transaction.getPatientRepo().set(obj);
        transaction.commit();
        return obj;
    }

    public void delete(ObjectId entity) {
        Patient obj = transaction.getPatientRepo().get(entity);
        transaction.getPatientRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Patient> lst = entities.stream().map(x -> transaction.getPatientRepo().get(x)).collect(Collectors.toList());
        transaction.getPatientRepo().deleteMany(lst);
        transaction.commit();
    }
}
