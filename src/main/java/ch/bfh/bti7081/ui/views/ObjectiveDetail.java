package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.*;
import model.entities.Objective;
import ch.bfh.bti7081.Presenter.ObjectivePresenter;
import model.entities.Patient;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Route(value = "objective", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Objective")
public class ObjectiveDetail extends ViewFrame implements RouterLayout, HasUrlParameter<String> {

    private Objective objective;
    private List<Objective> children = new ArrayList<>();

    private Patient patient;

    private ObjectivePresenter presenter = new ObjectivePresenter();


    public ObjectiveDetail() {
    }

    private Grid<Objective> grid;

    private Component createContent() {
        Grid grid = createGrid();

        updateList();
        Div labeldiv = new Div(new Label(objective.getTitle()));
        Div contdiv = new Div(new Text(objective.getContent()));

        Div text = new Div(new Text("Progress: "));

        ProgressBar progressBar = new ProgressBar();
        progressBar.setMax(100);
        progressBar.setMin(0);
        progressBar.setValue(calculateTaskProgress());

        Div content = new Div(labeldiv, contdiv, text, progressBar, grid);
        content.addClassName("grid-view");
        return content;
    }

    private Double calculateTaskProgress(){
        double totalWeight = children.stream().mapToDouble(Objective::getWeight).sum();
        double currentWeightDone = children.parallelStream().mapToDouble(x -> (x.getProgress()/100) * x.getWeight()).sum();
        return totalWeight * currentWeightDone;
    }

    private Grid createGrid() {
        grid = new Grid<>(Objective.class);
        grid.setId("objective");
        grid.setColumns("id", "title", "content", "progress", "weight");
        grid.setSizeFull();

        grid.getColumnByKey("id").setFlexGrow(0).setFrozen(true)
                .setHeader("id").setSortable(true)
                .setWidth("12%");
        grid.getColumnByKey("title").setFlexGrow(0).setFrozen(true)
                .setHeader("title").setSortable(true)
                .setWidth("22%");
        grid.getColumnByKey("content").setFlexGrow(0).setFrozen(true)
                .setHeader("content").setSortable(true)
                .setWidth("22%");
        grid.getColumnByKey("progress").setFlexGrow(0).setFrozen(true)
                .setHeader("progress").setSortable(true)
                .setWidth("22%");
        grid.getColumnByKey("weight").setFlexGrow(0).setFrozen(true)
                .setHeader("weight").setSortable(true)
                .setWidth("22%");
        return grid;
    }

    public void updateList() {
            children = presenter.getObjectives(patient.getId(), objective.getId());
            grid.setItems(children);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        String[] params = s.split(";");

        objective = presenter.getObjective(new ObjectId(params[0]));
        if(params.length > 1)
            patient = presenter.getPatient(new ObjectId(params[1]));
        setViewContent(createContent());
    }
}
