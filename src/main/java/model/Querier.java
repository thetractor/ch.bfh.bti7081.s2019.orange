package model;

import model.Entities.Doctor;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class Querier<T extends IEntity> {
    protected UnitOfWork transaction = new UnitOfWork();

    public  abstract List<T> getAll();
    public abstract T get(ObjectId id);
}
