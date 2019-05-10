package model.Entities;

import model.IEntity;
import org.bson.types.ObjectId;

public class Report implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }

}
