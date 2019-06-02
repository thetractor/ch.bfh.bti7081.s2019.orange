package model.objective;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Objective;
import model.entities.Report;
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

    public List<Objective> getAll() {
        return transaction.getObjectiveRepo().getAll();
    }

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
