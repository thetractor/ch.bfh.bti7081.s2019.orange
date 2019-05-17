package model;

import model.entities.IEntity;
import org.bson.types.ObjectId;
import java.util.List;


/**
 *
 * @param <T>   Generic of type entity.
 *
 * @author gian.demarmelsz@students.bfh.ch
 */
public abstract class Querier<T extends IEntity> {
    // Todo: Be able to set unitOfWork for dependency injection (e.g to be testable)
    protected UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public abstract List<T> getAll();
    public abstract T get(ObjectId id);
}
