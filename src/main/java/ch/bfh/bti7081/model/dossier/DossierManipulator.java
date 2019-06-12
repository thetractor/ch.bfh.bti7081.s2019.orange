package ch.bfh.bti7081.model.dossier;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Dossier;
import org.bson.types.ObjectId;

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

    /**
     * Creates a dossier object for a patient, saves it to the DB and returns the object.
     * @param patientID ObjectId
     * @return Dossier object
     */
    public Dossier build(ObjectId patientID){
        Dossier obj = new Dossier(patientID);
        transaction.getDossierRepo().set(obj);
        transaction.commit();
        return obj;
    }

    /**
     * Deletes a Dossier by a given ID
     * @param entity ObjectId
     */
    public void delete(ObjectId entity) {
        Dossier obj = transaction.getDossierRepo().get(entity);
        transaction.getDossierRepo().delete(obj);
        transaction.commit();
    }

    /**
     * Deletes multiple dossiers
     * @param entities List of dossier-IDs
     */
    public void deleteMany(List<ObjectId> entities) {
        List<Dossier> lst = entities.stream().map(x -> transaction.getDossierRepo().get(x)).collect(Collectors.toList());
        transaction.getDossierRepo().deleteMany(lst);
        transaction.commit();
    }
}
