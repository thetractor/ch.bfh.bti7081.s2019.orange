package ch.bfh.bti7081;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {
    private CustomerService service = CustomerService.getInstance();

    private TextField filterText = new TextField();
    private Button addCustomer = new Button("Add Customer");
    private CustomerForm customerForm = new CustomerForm(this);
    private Grid<Customer> grid = new Grid<>(Customer.class);

    public MainView() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        addCustomer.addClickListener(e -> {
            customerForm.setVisible(true);
            customerForm.setCustomer(new Customer());
        });

        customerForm.setCustomer(null);

        grid.setColumns("firstName", "lastName", "status");
        grid.asSingleSelect().addValueChangeListener(event ->
            customerForm.setCustomer(grid.asSingleSelect().getValue()));
        grid.setSizeFull();


        HorizontalLayout toolBar = new HorizontalLayout(filterText, addCustomer);
        HorizontalLayout mainContent = new HorizontalLayout(grid, customerForm);

        mainContent.setSizeFull();

        add(toolBar, mainContent);
        setSizeFull();
        updateList();
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
}
