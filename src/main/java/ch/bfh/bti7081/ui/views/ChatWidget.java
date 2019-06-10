package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.presenter.MessagePresenter;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.util.LumoStyles;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import ch.bfh.bti7081.model.doctor.DoctorQuerier;
import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.entities.Report;
import ch.bfh.bti7081.model.message.MessageDispatcher;
import ch.bfh.bti7081.model.message.MessageHandler;


/**
 * Implementation of the ChatWidget
 * This class sends message-strings to the message handler and also registers to MessageDispatcher.
 * So it gets updated every time a new message was sent for the same report.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
@Push
@Route("push")
public class ChatWidget extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private transient MessagePresenter messagePresenter;
    private VerticalLayout messageLayout;
    private transient Report report;
    private transient DoctorQuerier doctorQuerier;
    private transient MessageHandler messageHandler;

    private Registration messageDispatchRegistration;


    public ChatWidget(Report report) {
        this.report = report;
        messagePresenter = new MessagePresenter();
        messageLayout = new VerticalLayout();
        messageHandler = new MessageHandler();

        doctorQuerier = new DoctorQuerier();

        addContent();
    }
    private void addContent(){

        Div header = new Div(new Label("Messages"));
        Div headerDivider = new Div(new Divider("1px"));
        headerDivider.addClassName(LumoStyles.Padding.Responsive.Vertical.M);

        // Add all messages to the layout
        messagePresenter.getMessagesByReportId(report.getId())
                .forEach(message -> messageLayout.add(createMessageLabel(message)));

        TextField messageField = new TextField();
        Div formDivider = new Div(new Divider("1px"));
        Button sendButton = new Button("Send", e -> {
            messageHandler.handleSentMessage(messageField.getValue(), report);
            messageField.setValue("");
        });

        Div widgetContent = new Div(header, headerDivider, messageLayout, formDivider, messageField, sendButton);
        widgetContent.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        add(widgetContent);
    }

    private Html createMessageLabel(Message message){
        // TODO: A frontend dev would be very welcome to implement this properly... ;)
        return new Html(String.format(
                "<div style=\"background-color:lightblue;border-radius:20px;padding:5px;\">%n" +
                "<p><b>%s</b></p>%n" +
                "<p>%s</p>%n" +
                "<p><i>%s</i></p>%n" +
                "</div>",
                doctorQuerier.get(message.getFromDoctorId()).getFullName(),
                message.getContent(),
                message.getSentDate().toString()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        UI ui = attachEvent.getUI();
        // Register a lambda function to the MessageDispatcher, which defines what to do in dispatch()
        // Register returns a lambda, used to remove the registration
        messageDispatchRegistration = MessageDispatcher.register(newMessage -> {
            ui.access(() -> messageLayout.add(createMessageLabel(newMessage)));
        }, report.getId());
    }

    /**
     * <code>onDetach</code> gets called on leaving the page (e.g if the client gets redirected to another page)
     * Does not get called if the window/ tab gets closed or the "previous page button" is clicked!
     *
     * @param detachEvent
     */
    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if(messageDispatchRegistration != null){
            messageDispatchRegistration.remove();
            messageDispatchRegistration = null;
        }
    }
}
