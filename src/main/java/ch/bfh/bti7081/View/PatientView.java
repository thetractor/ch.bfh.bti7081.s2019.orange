package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import model.entities.Patient;
import org.bson.types.ObjectId;

/**
 * Implementation of the <code>PatientView</code> class.
 * For further information please refer to the authors.
 *
 * @author matthias.ossola@students.bfh.ch
 */
@Route("patients")
public class PatientView extends VerticalLayout {

    private PatientPresenter presenter;

    private Grid<Patient> grid = new Grid<>(Patient.class);

    public PatientView(){
        presenter = new PatientPresenter();

        initializeGrid();
        setSizeFull();
        updateList();
    }

    /**
     * Initialize the overview of patients and show grid
     */
    private void initializeGrid(){
        grid.setColumns("name", "surname");
        grid.getColumnByKey("name").setHeader("Vorname");
        grid.getColumnByKey("surname").setHeader("Nachname");
        grid.addItemClickListener(e ->
                this.getUI().ifPresent(ui ->
                        {
                            VaadinService.getCurrentRequest().getWrappedSession()
                                    .setAttribute("patientId", e.getItem().getId());
                            ui.navigate("reports");
                        }
                )
        );
        add(grid);
    }

    /**
     * Load the data for the patient overview
     */
    public void updateList() {
        ObjectId doctorId = (ObjectId) VaadinService.getCurrentRequest().getWrappedSession()
                .getAttribute("doctorId");
        if (doctorId != null) {
            grid.setItems(presenter.getPatientsByDoctorId(doctorId));
        }
    }

}
