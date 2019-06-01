package model.message;

import com.vaadin.flow.server.VaadinSession;
import model.entities.Message;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.Date;

public class MessageHandler {
    private MessageManipulator messageManipulator;


    public MessageHandler(){
        messageManipulator = new MessageManipulator();
    }

    public void handleSentMessage(String messageContent, Report affectedReport){
        Message message = saveMessage(messageContent, affectedReport);
        MessageDispatcher.dispatch(message, affectedReport.getId());
        // TODO: @Kevin add your Notification-dispatching code here ;)
    }

    private Message saveMessage(String messageText, Report affectedReport){
        ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
        return messageManipulator.build(messageText, doctorId, affectedReport.getId(), new Date());
    }



}
