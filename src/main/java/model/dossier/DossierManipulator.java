package model.dossier;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Doctor;
import model.entities.Dossier;
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
public class DossierManipulator {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Dossier build(ObjectId patientID){
        Dossier obj = new Dossier(patientID);
        transaction.getDossierRepo().set(obj);
        transaction.commit();
        return obj;
    }

    public void delete(ObjectId entity) {
        Dossier obj = transaction.getDossierRepo().get(entity);
        transaction.getDossierRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Dossier> lst = entities.stream().map(x -> transaction.getDossierRepo().get(x)).collect(Collectors.toList());
        transaction.getDossierRepo().deleteMany(lst);
        transaction.commit();
    }
}
