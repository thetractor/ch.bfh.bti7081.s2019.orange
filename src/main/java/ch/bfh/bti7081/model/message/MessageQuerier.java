package ch.bfh.bti7081.model.message;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Message;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API to Query dossier related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class MessageQuerier {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Returns all messages
     *
     * TODO: Getting all data in memory might be dangerous!
     *
     * @return List of Messages
     */
    public List<Message> getAll() {
        return transaction.getMessageRepo().getAll();
    }

    /**
     * Get a specific message by its ID
     * @param id ObjectId
     * @return Message object
     */
    public Message get(ObjectId id) {
        return transaction.getMessageRepo().get(id);
    }

    /**
     * Returns all messages corresponding to a given report
     * @param reportId ObjectId
     * @return List of messages
     */
    public List<Message> getByReportId(ObjectId reportId){
        return getAll()
                .stream()
                .filter(message -> message.getReportId().equals(reportId))
                .collect(Collectors.toList());
    }
}
