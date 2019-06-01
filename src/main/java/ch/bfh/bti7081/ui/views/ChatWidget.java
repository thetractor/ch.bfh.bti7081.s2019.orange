package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.MessagePresenter;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.layout.size.Vertical;
import ch.bfh.bti7081.ui.util.LumoStyles;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import model.doctor.DoctorQuerier;
import model.entities.Message;
import model.entities.Report;
import model.message.MessageManipulator;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class ChatWidget extends VerticalLayout {

    private MessagePresenter messagePresenter;
    private VerticalLayout messageLayout;
    private Report report;
    DoctorQuerier doctorQuerier;
    MessageManipulator messageManipulator;

    public ChatWidget(Report report) {
        this.report = report;
        messagePresenter = new MessagePresenter();
        messageLayout = new VerticalLayout();
        messageManipulator = new MessageManipulator();

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

        //Div form = new Div(new Label("Form"));
        TextField messageField = new TextField();
        Div formDivider = new Div(new Divider("1px"));
        // TODO: Replace e -> with injected event listener?
        Button sendButton = new Button("Send", e -> {
            saveMessage(messageField.getValue());
            messageLayout.add(new Label(messageField.getValue())); // TODO: change this, DISPATCH message
            messageField.setValue("");
        });

        Div widgetContent = new Div(header, headerDivider, messageLayout, formDivider, messageField, sendButton);
        widgetContent.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        add(widgetContent);
    }

    private Html createMessageLabel(Message message){
        // TODO: A frontend dev would be very welcome to implement this properly... ;)
        Html messageLabel = new Html(String.format(
                "<div style=\"background-color:lightblue;border-radius:20px;padding:5px;\">\n" +
                "<p><b>%s</b></p>\n" +
                "<p>%s</p>\n" +
                "<p><i>%s</i></p>\n" +
                "</div>",
                doctorQuerier.get(message.getFromDoctorId()).getFullName(),
                message.getContent(),
                message.getSentDate().toString()));
        return messageLabel;
    }

    private void saveMessage(String messageText){
        ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
        messageManipulator.build(messageText, doctorId, report.getId(), new Date());
    }
}
