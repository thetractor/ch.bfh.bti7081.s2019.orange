package ch.bfh.bti7081.model.message;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Message;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for manipulating models
 * Methods for creating, updating & deleting belong in this class
 * Other classes can be as this example
 * @author gian.demarmels@students.bfh.ch
 */
public class MessageManipulator {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Message build(String content, ObjectId doctor, ObjectId report, Date sentDate){
        Message obj = new Message(content, doctor,report, sentDate);
        transaction.getMessageRepo().set(obj);
        transaction.commit();
        return obj;
    }

    public void delete(ObjectId entity) {
        Message obj = transaction.getMessageRepo().get(entity);
        transaction.getMessageRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Message> lst = entities.stream().map(x -> transaction.getMessageRepo().get(x)).collect(Collectors.toList());
        transaction.getMessageRepo().deleteMany(lst);
        transaction.commit();
    }
}
