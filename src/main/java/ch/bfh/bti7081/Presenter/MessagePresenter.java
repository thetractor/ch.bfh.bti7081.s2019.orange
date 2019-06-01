package ch.bfh.bti7081.Presenter;

import model.entities.Message;
import model.message.MessageQuerier;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class MessagePresenter {

    private MessageQuerier messageQuerier;

    public MessagePresenter(){
        messageQuerier = new MessageQuerier();
    }

    public List<Message> getMessagesByReportId(ObjectId reportId){
        // TODO: Sort them by sent date eventually
        return messageQuerier.getAll()
                .stream()
                .filter(message -> message.getReportId().equals(reportId))
                .collect(Collectors.toList());
    }


}
