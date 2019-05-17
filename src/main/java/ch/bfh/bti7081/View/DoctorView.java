package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Presenter.HomePresenter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import model.entities.Doctor;

/**
 * Implementation of the <code>DoctorView</code> class.
 * For further information please refer to the authors.
 *
 * @author kevin.riesen@students.bfh.ch
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class DoctorView extends VerticalLayout {

    private HomePresenter presenter;

    private Grid<Doctor> grid = new Grid<>(Doctor.class);

    public DoctorView(){
        presenter = new HomePresenter();

        initializeGrid();
        setSizeFull();
        updateList();
    }


    /**
     * Initialize the overview of doctors and show grid
     */
    private void initializeGrid(){
        grid.setColumns("name", "surname");
        grid.getColumnByKey("name").setHeader("Vorname");
        grid.getColumnByKey("surname").setHeader("Nachname");
        grid.addItemClickListener(e ->
                this.getUI().ifPresent(ui ->
                        {
                            VaadinService.getCurrentRequest().getWrappedSession()
                                    .setAttribute("doctorId", e.getItem().getId());
                            ui.navigate("patients");
                        }
                )
        );
        add(grid);
    }

    /**
     * Load the data for the doctor overview
     */
    public void updateList() {
        grid.setItems(presenter.getDoctors());
    }

}
