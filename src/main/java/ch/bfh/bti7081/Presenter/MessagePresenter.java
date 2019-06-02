package ch.bfh.bti7081.Presenter;

import model.entities.Message;
import model.message.MessageQuerier;
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
