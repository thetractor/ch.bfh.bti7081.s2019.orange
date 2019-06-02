package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Presenter.ReportPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import model.entities.Patient;
import model.entities.Report;
import org.bson.types.ObjectId;

/**
 * Implementation of the <code>ReportView</code> class.
 * For further information please refer to the authors.
 *
 * @author adrian.berger@students.bfh.ch
 * @author lars.peyer@students.bfh.ch
 */
@Route("reports")
public class ReportView extends VerticalLayout {

    private ReportPresenter presenter;

    private Grid<Report> grid = new Grid<>(Report.class);

    public ReportView(){
        presenter = new ReportPresenter();
        showPatientData();
        initializeGrid();
        setSizeFull();
        updateList();
    }

    /**
     * Show name and surname of patient above the forms
     */
    private void showPatientData() {
        HorizontalLayout box = new HorizontalLayout();
        TextField nameField = new TextField("Vorname");
        TextField surnameField = new TextField("Nachname");

        ObjectId patientId = (ObjectId) VaadinService.getCurrentRequest()
                .getWrappedSession().getAttribute("patientId");

        if (patientId != null){
            Patient patient = presenter.getPatientById(patientId);
            nameField.setValue(patient.getName());
            surnameField.setValue(patient.getSurname());
        } else {
            // TODO: Indicate if this happened
        }

        box.add(nameField);
        box.add(surnameField);
        add(box);
    }
    /**
     * Initialize the detailView of reports and show grid
     */
    private void initializeDetailView(ObjectId reportId){
        Report report = presenter.getReportById(reportId);
        Button saveButton = new Button("Speichern");
        Button sendMessageButton = new Button("Nachricht versenden");
        TextArea reportField = new TextArea("Report");
        reportField.setValue(report.getContent());
        Text messagePlaceholder = new Text("This is a placeholder for the messages");
        add(reportField);
        add(messagePlaceholder);
        add(messagePlaceholder);
        add(sendMessageButton);
        add(saveButton);
    }


    /**
     * Initialize the overview of reports and show grid
     */
    private void initializeGrid(){
        grid.setColumns("content");
        grid.getColumnByKey("content").setHeader("Inhalt");
        grid.addItemClickListener(e ->
                this.getUI().ifPresent(ui ->
                        {
                            VaadinService.getCurrentRequest().getWrappedSession()
                                    .setAttribute("reportId", e.getItem().getId());
                            remove(grid);
                            initializeDetailView(e.getItem().getId());
                        }
                )
        );
        add(grid);
    }

    /**
     * Load the data for the report overview
     */
    public void updateList() {
        ObjectId patientId = (ObjectId) VaadinService.getCurrentRequest()
                .getWrappedSession().getAttribute("patientId");
        if (patientId != null) {
            grid.setItems(presenter.getReportsByPatentId(patientId, 10));
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent){
        // Redirects client to homepage if there is no doctorId in the session
        if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("doctorId") == null){
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }
    }

}
