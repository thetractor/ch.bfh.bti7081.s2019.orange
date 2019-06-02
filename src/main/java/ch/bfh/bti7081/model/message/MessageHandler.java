package ch.bfh.bti7081.model.message;

import com.vaadin.flow.server.VaadinSession;
import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.entities.Report;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Handles the message being sent by a view.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 * @author kevin.riesen@students.bfh.ch
 */
public class MessageHandler {
    private MessageManipulator messageManipulator;


    public MessageHandler(){
        messageManipulator = new MessageManipulator();
    }

    public void handleSentMessage(String messageContent, Report affectedReport){
        Message message = saveMessage(messageContent, affectedReport);
        MessageDispatcher.dispatch(message);
        MessageNotificationDispatcher.dispatch(message);
    }

    private Message saveMessage(String messageText, Report affectedReport){
        ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
        return messageManipulator.build(messageText, doctorId, affectedReport.getId(), new Date());
    }



}
