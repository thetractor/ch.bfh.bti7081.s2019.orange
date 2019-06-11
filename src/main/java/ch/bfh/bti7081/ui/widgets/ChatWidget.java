package ch.bfh.bti7081.ui.widgets;

import ch.bfh.bti7081.presenter.MessagePresenter;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.FlexDirection;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Implementation of the ChatWidget
 * This class sends message-strings to the message handler and also registers to MessageDispatcher.
 * So it gets updated every time a new message was sent for the same report.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 * @author lars.peyer@students.bfh.ch
 */
@Push
@Route("push")
public class ChatWidget extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private transient MessagePresenter messagePresenter;
    private Div content;
    private transient Report report;
    private transient DoctorQuerier doctorQuerier;
    private transient MessageHandler messageHandler;

    private Registration messageDispatchRegistration;


    public ChatWidget(Report report) {
        this.report = report;
        messagePresenter = new MessagePresenter();
        messageHandler = new MessageHandler();
        content = new Div();
        doctorQuerier = new DoctorQuerier();

        addContent();
    }
    private void addContent(){
        // Make sure to use 100% height of the drawer
        this.setHeight("100%");

        // Main div container that holds the messages
        content.setWidth("100%");

        // Add all messages to the layout
        messagePresenter.getMessagesByReportId(report.getId())
                .forEach(message -> content.add(createMessageLabel(message)));

        Div widgetContent = new Div(content);
        Div formContent = new Div(createMessageForm());

        FlexBoxLayout messageContent = new FlexBoxLayout(widgetContent, formContent);
        messageContent.setFlexDirection(FlexDirection.COLUMN);
        messageContent.setHeight("100%");
        messageContent.setWidth("100%");
        messageContent.setJustifyContentMode(JustifyContentMode.BETWEEN);

        add(messageContent);
    }

    private Component createMessageForm() {
        Div formDivider = new Div(new Divider("1px"));
        formDivider.addClassName(LumoStyles.Padding.Responsive.Vertical.L);

        TextField messageField = new TextField();
        Button sendButton = new Button("Send", e -> {
            messageHandler.handleSentMessage(messageField.getValue(), report);
            messageField.setValue("");
        });

        FlexBoxLayout messageFormLayout = new FlexBoxLayout(messageField, sendButton);
        messageFormLayout.setFlexDirection(FlexDirection.ROW);
        messageFormLayout.setWidth("100%");
        messageFormLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        FlexBoxLayout messageComponentLayout = new FlexBoxLayout(formDivider, messageFormLayout);
        messageComponentLayout.setFlexDirection(FlexDirection.COLUMN);

        return messageComponentLayout;
    }

    private Component createMessageLabel(Message message){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        ListItem item = new ListItem(
            UIUtils.createTertiaryIcon(VaadinIcon.ENVELOPE_O),
            doctorQuerier.get(message.getFromDoctorId()).getFullName() + " - " + dateFormat.format(message.getSentDate()),
            message.getContent()
            );
        Divider divider = new Divider("1px");

        FlexBoxLayout content = new FlexBoxLayout(item, divider);
        content.setFlexDirection(FlexDirection.COLUMN);

        return content;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        UI ui = attachEvent.getUI();
        // Register a lambda function to the MessageDispatcher, which defines what to do in dispatch()
        // Register returns a lambda, used to remove the registration
        messageDispatchRegistration = MessageDispatcher.register(newMessage -> {
            ui.access(() -> content.add(createMessageLabel(newMessage)));
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
