package ch.bfh.bti7081.model.objective;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Objective;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API to Query objective related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class ObjectiveQuerier {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Returns all objectives
     *
     * TODO: Getting all data in memory might be dangerous!
     *
     * @return List of Objectives
     */
    public List<Objective> getAll() {
        return transaction.getObjectiveRepo().getAll();
    }

    /**
     * Get a specific objective by its ID
     * @param id ObjectId
     * @return Objective
     */
    public Objective get(ObjectId id) {
        return transaction.getObjectiveRepo().get(id);
    }

    public List<Objective> getByPatient(ObjectId id){
        return transaction.getObjectiveRepo()
                .getAll()
                .stream()
                .filter(x -> x.getPatientId().equals(id))
                .filter(x -> x.getParent() == null)
                .collect(Collectors.toList());
    }

    public List<Objective> getByPatient(ObjectId id, ObjectId parent){
        System.out.println(parent);
        System.out.println(id);
        return transaction.getObjectiveRepo()
                .getAll()
                .stream()
                .filter(x -> x.getPatientId().equals(id))
                .filter(x -> x.getParent() != null)
                .filter(x -> x.getParent().equals(parent))
                .collect(Collectors.toList());
    }
}
