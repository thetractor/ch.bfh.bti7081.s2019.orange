package model;

import model.entities.IEntity;
import org.bson.types.ObjectId;
import java.util.List;

public abstract class Querier<T extends IEntity> {
    protected UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public abstract List<T> getAll();
    public abstract T get(ObjectId id);
}
