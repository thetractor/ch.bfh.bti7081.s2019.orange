package model.entities;

import org.bson.types.ObjectId;

/**
 * All entities need to have a method to getId (Contract based design)
 * @author gian.demarmels@students.bf.ch
 */
public interface IEntity {
    ObjectId getId();
}
