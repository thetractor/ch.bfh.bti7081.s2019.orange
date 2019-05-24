package model.objective;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Objective;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query objective related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class ObjectiveQuerier {
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Objective> getAll() {
        return transaction.getObjectiveRepo().getAll();
    }

    public Objective get(ObjectId id) {
        return transaction.getObjectiveRepo().get(id);
    }
}
