package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Presenter.ReportPresenter;
import model.message.MessageDispatcher;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.shared.Registration;
import org.bson.types.ObjectId;

@Push
@Route("tryout")
public class TryoutMessageDispatchView extends Div {
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout messageLayout = new VerticalLayout();

    Registration messageDispatchRegistration;

    private ObjectId doctorId;
    private ReportPresenter presenter;


    /**
     * Object gets created if the given URL (@Route(..)) is accessed
     * Creates the basic view which shall be shown.
     *
     */
    public TryoutMessageDispatchView() {
        System.out.println("TryoutMessageDispatchView started....");
        presenter = new ReportPresenter();
        doctorId = (ObjectId) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("doctorId");
        initializeHeader();
        initializeMessagePart();

        add(verticalLayout);
    }

    private void initializeHeader(){
        Label loggedInDoctorField = new Label();

        if (doctorId != null){
            System.out.println(doctorId);
            String fullName = presenter.getDoctorFullName(doctorId);
            loggedInDoctorField.setText(fullName);
        } else {
            loggedInDoctorField.setText("No doctor logged in");
        }
        verticalLayout.add(loggedInDoctorField);
    }

    private void initializeMessagePart(){
        TextField messageField = new TextField();

        Button sendButton = new Button("Send", e -> {
            // Sends value of the text field to the broadcaster, which dispatches it
            MessageDispatcher.dispatch(messageField.getValue(), doctorId);
            messageField.setValue("");
        });
        Button tryoutButton = new Button("Back Home", e -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        HorizontalLayout sendBar = new HorizontalLayout(messageField, sendButton);
        verticalLayout.add(sendBar, messageLayout, tryoutButton);
    }


    /**
     * Gets called when the page is entered/ reloaded
     *
     * @param attachEvent
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        // Redirects client to homepage if there is no doctorId in the session
        if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("doctorId") == null){
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

        UI ui = attachEvent.getUI();
        // Register a lambda function to the Broadcaster, which defines what to do in dispatch()
        // Register returns a lambda, used to remove the registration
        messageDispatchRegistration = MessageDispatcher.register(newMessage -> {
            ui.access(() -> messageLayout.add(new Span(newMessage)));
        }, doctorId);
    }

    /**
     * <code>onDetach</code> gets called on leaving the page (e.g if the client gets redirected to another page)
     * Does not get called if the window/ tab gets closed or the "previous page button" is clicked!
     *
     * @param detachEvent
     */
    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // ToDo: be also able to remove the registration if the window gets closed.
        if(messageDispatchRegistration != null){
            messageDispatchRegistration.remove();
            messageDispatchRegistration = null;
        }

    }
}