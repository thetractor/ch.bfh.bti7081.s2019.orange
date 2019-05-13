package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Presenter.DossiersPresenter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import model.Entities.Patient;

@Route("dossiers")
public class DossiersView extends VerticalLayout {

    private DossiersPresenter presenter;

    private Grid<Patient> grid = new Grid<>(Patient.class);
    private TextField txtFilter = new TextField();

    public DossiersView(){
        presenter = new DossiersPresenter();

        initializeFilter();
        initializeGrid();
        setSizeFull();
        UpdateList();
    }

    private void initializeFilter(){
        txtFilter.setPlaceholder("Nach Namen filtern...");
        txtFilter.setClearButtonVisible(true);
        txtFilter.setValueChangeMode(ValueChangeMode.EAGER);
        txtFilter.addValueChangeListener(e -> UpdateList());
        add(txtFilter, grid);
    }

    private void initializeGrid(){
        grid.setColumns("name", "surname");
        grid.getColumnByKey("name").setHeader("Vorname");
        grid.getColumnByKey("surname").setHeader("Nachname");
        grid.addItemClickListener(e -> presenter.ViewDossier(e.getItem()));
        add(grid);
    }

    public void UpdateList() {
        grid.setItems(presenter.GetDossiersByName(txtFilter.getValue()));
    }

}
