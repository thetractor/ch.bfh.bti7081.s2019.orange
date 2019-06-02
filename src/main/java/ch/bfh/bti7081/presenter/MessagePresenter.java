package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.message.MessageQuerier;
import org.bson.types.ObjectId;

import java.util.List;

public class MessagePresenter {

    private MessageQuerier messageQuerier;

    public MessagePresenter(){
        messageQuerier = new MessageQuerier();
    }

    public List<Message> getMessagesByReportId(ObjectId reportId){
        return messageQuerier.getByReportId(reportId);
    }
}
