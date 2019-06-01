package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.util.UIUtils;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import model.entities.Objective;
import org.bson.types.ObjectId;

@Route(value = "objectives", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Objectives")
public class Objectives extends ViewFrame implements RouterLayout {

    private PatientPresenter presenter;
    private Grid<Objective> grid;

    public Objectives() {
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
        grid = new Grid<>(Objective.class);
        grid.setId("objectives");
        grid.setColumns("content");
        grid.setSizeFull();
        grid.getColumnByKey("content").setFlexGrow(0).setFrozen(true)
            .setHeader("Inhalt").setSortable(true)
            .setWidth(UIUtils.COLUMN_WIDTH_XL)
            .setResizable(true);
        return grid;
    }

    @Override
    protected void onDetach(DetachEvent detachEvent){
        System.out.println("Detached!");
    }

    /**
     * Load the data for the patient overview
     */
    public void updateList() {
        // this is currently hardcoded, as we have no session logic
        ObjectId patientId = new ObjectId("5ceda9592d441018a541f34c");
        if (patientId != null) {
            grid.setItems(presenter.getObjectives(patientId));
        }
    }
}
