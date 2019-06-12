package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.presenter.PatientPresenter;
import ch.bfh.bti7081.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import ch.bfh.bti7081.model.entities.Patient;
import org.bson.types.ObjectId;

/**
 * Class to render a list with all patients of a
 * logged in doctor.
 *
 * @author lars.peyer@students.bfh.ch
 */

@Route(value = "patients", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Patients")
public class Patients extends ViewFrame implements RouterLayout {

    private static final long serialVersionUID = 1L;
    private transient PatientPresenter presenter;
    private Grid<Patient> grid;

    public Patients() {
        setViewContent(createContent());
    }

    private Component createContent() {
        presenter = new PatientPresenter();
        Div content = new Div(createGrid());
        content.addClassName("grid-view");
        updateList();
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>(Patient.class);
        grid.setId("patients");
        grid.setColumns("id", "name", "surname");
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::viewDetails));
        grid.setSizeFull();

        grid.getColumnByKey("id").setFlexGrow(0).setFrozen(true)
            .setHeader("SSN").setSortable(true)
            .setWidth("30%");
        grid.getColumnByKey("name").setFlexGrow(0).setFrozen(true)
            .setHeader("First Name").setSortable(true)
            .setWidth("35%");
        grid.getColumnByKey("surname").setFlexGrow(0).setFrozen(true)
            .setHeader("Last Name").setSortable(true)
            .setWidth("35%");

        return grid;
    }

    private void viewDetails(Patient patient) {
        UI.getCurrent().navigate(PatientDetail.class, patient.getId().toString());
    }

    /**
     * Load the data for the patient overview
     */
    public void updateList() {
        ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");

        if (doctorId != null) {
            grid.setItems(presenter.getPatientsByDoctorId(doctorId));
        }
    }
}
