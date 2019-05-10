package model.Entities;

import org.bson.types.ObjectId;

public class Message implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }
}
