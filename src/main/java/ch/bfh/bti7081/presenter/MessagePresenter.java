package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.message.MessageQuerier;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Implementation of the message presenter
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
public class MessagePresenter {

    private MessageQuerier messageQuerier;

    /**
     * MessagePresenter constructor
     */
    public MessagePresenter(){
        messageQuerier = new MessageQuerier();
    }

    /**
     * Returns all messages corresponding to a given report
     * @param reportId ObjectId
     * @return List of message objects
     */
    public List<Message> getMessagesByReportId(ObjectId reportId){
        return messageQuerier.getByReportId(reportId);
    }
}
