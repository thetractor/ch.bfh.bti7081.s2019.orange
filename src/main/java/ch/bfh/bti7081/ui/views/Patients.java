package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.util.UIUtils;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import model.entities.Patient;
import org.bson.types.ObjectId;

@Route(value = "patients", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Patients")
public class Patients extends ViewFrame implements RouterLayout {

    private PatientPresenter presenter;
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
        grid.setColumns("name", "surname");
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::viewDetails));
//        grid.setDataProvider(
//                DataProvider.ofCollection(DummyData.getBankAccounts()));
          grid.setSizeFull();
//
        grid.getColumnByKey("name").setFlexGrow(0).setFrozen(true)
            .setHeader("Vorname")
            .setHeader("ID").setSortable(true)
            .setWidth(UIUtils.COLUMN_WIDTH_XS)
            .setResizable(true);
        grid.addColumn(new ComponentRenderer<>(this::createPatientInfo))
                .setHeader("Bank Account").setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setResizable(true);
//        grid.addColumn(BankAccount::getOwner).setHeader("Owner")
//                .setWidth(UIUtils.COLUMN_WIDTH_XL).setResizable(true);
//        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
//                .setFlexGrow(0).setHeader("Availability ($)").setWidth("130px")
//                .setResizable(true).setTextAlign(ColumnTextAlign.END);
//        grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated,
//                DateTimeFormatter.ofPattern("MMM dd, YYYY")))
//                .setComparator(BankAccount::getUpdated).setFlexGrow(0)
//                .setHeader("Updated").setWidth(UIUtils.COLUMN_WIDTH_M)
//                .setResizable(true);

        return grid;
    }

    private Component createPatientInfo(Patient patient) {
        ListItem item = new ListItem(patient.getName(),
                patient.getSurname());
        item.setHorizontalPadding(false);
        return item;
    }

    @Override
    protected void onDetach(DetachEvent detachEvent){
    }
//
//    private Component createAvailability(BankAccount bankAccount) {
//        Double availability = bankAccount.getAvailability();
//        Label label = UIUtils.createAmountLabel(availability);
//
//        if (availability > 0) {
//            UIUtils.setTextColor(TextColor.SUCCESS, label);
//        } else {
//            UIUtils.setTextColor(TextColor.ERROR, label);
//        }
//
//        return label;
//    }
//
    private void viewDetails(Patient patient) {
        UI.getCurrent().navigate(PatientDetail.class, patient.getId().toString());
    }

    /**
     * Load the data for the patient overview
     */
    public void updateList() {
        ObjectId doctorId = (ObjectId) VaadinService.getCurrentRequest().getWrappedSession()
            .getAttribute("doctorId");
        // Manuall set a doctor
        //ObjectId doctorId = new ObjectId("5cde5d94247b1115c4306063");
        if (doctorId != null) {
            grid.setItems(presenter.getPatientsByDoctorId(doctorId));
        }
    }
}
