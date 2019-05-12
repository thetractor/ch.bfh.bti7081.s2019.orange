package model.Objective;

import model.Entities.Message;
import model.Entities.Objective;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

public class ObjectiveQuerier extends Querier<Objective> {
    @Override
    public List<Objective> getAll() {
        return transaction.getObjectiveRepo().getAll();
    }

    @Override
    public Objective get(ObjectId id) {
        return transaction.getObjectiveRepo().get(id);
    }
}
