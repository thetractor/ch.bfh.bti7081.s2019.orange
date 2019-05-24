package model.message;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Message;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query dossier related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class MessageQuerier {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Message> getAll() {
        return transaction.getMessageRepo().getAll();
    }

    public Message get(ObjectId id) {
        return transaction.getMessageRepo().get(id);
    }
}
