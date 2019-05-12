package ch.bfh.bti7081.View;

import ch.bfh.bti7081.Model.DemoDossier;
import ch.bfh.bti7081.Presenter.DossiersPresenter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("dossiers")
public class DossiersView extends VerticalLayout {

    private DossiersPresenter presenter;

    private Grid<DemoDossier> grid = new Grid<>(DemoDossier.class);
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
        grid.setColumns("firstName", "lastName", "birthDate");
        grid.getColumnByKey("firstName").setHeader("Vorname");
        grid.getColumnByKey("lastName").setHeader("Nachname");
        grid.getColumnByKey("birthDate").setHeader("Geburtsdatum");
        grid.addItemClickListener(e -> viewDossier(e.getItem()));
        add(grid);
    }

    public void UpdateList() {
        grid.setItems(presenter.GetDossiersByName(txtFilter.getValue()));
    }

    public void viewDossier(DemoDossier dossier){
        Notification.show(dossier.toString() + " wurde ausgewählt.");
        //@ToDo: Implement
    }

}
