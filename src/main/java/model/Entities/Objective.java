package model.Entities;

import org.bson.types.ObjectId;

public class Objective implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }
}
