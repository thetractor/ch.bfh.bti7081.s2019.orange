package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.presenter.PatientPresenter;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawer;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawerHeader;
import ch.bfh.bti7081.ui.layout.size.Top;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.WhiteSpace;
import ch.bfh.bti7081.ui.widgets.ChatWidget;
import ch.bfh.bti7081.ui.widgets.PatientImageWidget;
import ch.bfh.bti7081.ui.widgets.ReportsWidget;
import ch.bfh.bti7081.ui.widgets.SubtaskWidget;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.components.navigation.bar.AppBar;
import ch.bfh.bti7081.ui.layout.size.Bottom;
import ch.bfh.bti7081.ui.layout.size.Horizontal;
import ch.bfh.bti7081.ui.layout.size.Vertical;
import ch.bfh.bti7081.ui.util.BoxShadowBorders;
import ch.bfh.bti7081.ui.util.css.FlexDirection;
import com.vaadin.flow.server.VaadinSession;
import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.entities.Report;
import ch.bfh.bti7081.model.objective.ObjectiveQuerier;
import org.bson.types.ObjectId;
import java.util.function.Function;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;


/**
 * Class still needs a lot of refactoring, therefore the class description will be added
 * after being in a more robust/ "more final" state.
 *
 * @author Team Orange
 */
@Route(value = "patient-details", layout = MainLayout.class)
@PageTitle("Patient Details")
public class PatientDetail extends SplitViewFrame implements HasUrlParameter<String> {

  private static final long serialVersionUID = 1L;
  private DetailsDrawer reportDrawer;
  private DetailsDrawer objectiveDrawer;
  private transient Patient patient;
  private transient PatientPresenter patientPresenter = new PatientPresenter();


  @Override
  protected void onAttach(AttachEvent attachEvent) {
    ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
    if (doctorId == null || !patientPresenter.getPatientsByDoctorId(doctorId).contains(patient)){
      UI.getCurrent().navigate(Home.class);
      return;
    }

    super.onAttach(attachEvent);
    AppBar appBar = initAppBar();
    appBar.setTitle(patient.getName() + " " + patient.getSurname());
    UI.getCurrent().getPage().setTitle(patient.getName() + " " + patient.getSurname());

    setViewContent(createContent());
  }

  @Override
  public void setParameter(BeforeEvent beforeEvent, String patientIdString) {
    patient = patientPresenter.getPatient(new ObjectId(patientIdString));
    setViewContent(createContent());
  }

  private AppBar initAppBar() {
    AppBar appBar = MainLayout.get().getAppBar();
    appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
    appBar.getContextIcon().addClickListener(
        e -> UI.getCurrent().navigate(Patients.class));
    return appBar;
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(
            new PatientImageWidget(patient),
            createSubHeader("Recent Reports"),
            createRecentReportsList(),
            createSubHeader("Objectives"),
            createObjectiveList()
    );
    content.setFlexDirection(FlexDirection.COLUMN);
    content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
    content.setMaxWidth("840px");
    return content;
  }

  private Component createSubHeader(String titleText) {
    Label title = UIUtils.createH3Label(titleText);

    FlexBoxLayout header = new FlexBoxLayout(title);
    header.setAlignItems(FlexComponent.Alignment.CENTER);
    header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
    return header;
  }

  private Component createRecentReportsList() {
    // Passes a reference to the showReportDetails function which shall be called after pressing of a reports
    // Detail-button. The showReportDetails function requires the id of the given report.
    Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction =  (report) -> {
      ComponentEventListener<ClickEvent<Button>> clickEvent = e -> showReportDetails(report);
      return clickEvent;
    };
    ReportsWidget reportsWidget = new ReportsWidget(patient, callBackFunction);
    return reportsWidget;
  }

  private Component createObjectiveList() {
    Div items = new Div();
    Div stats = new Div();

    items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

    List<Objective> objectives = patientPresenter.getObjectives(patient.getId(), null);
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Text intro = new Text("There are currently " + objectives.size() + " objectives for this patient.");

    stats.add(intro);
    stats.addClassName(LumoStyles.Margin.Left.L);
    items.add(stats);

    int counter = 0;
    for (Objective objective : objectives) {
      counter++;

      Button details = UIUtils.createSmallButton("Details", VaadinIcon.SEARCH);
      details.addClickListener(e -> showObjectives(objective));

      ListItem item = new ListItem(
              UIUtils.createTertiaryIcon(VaadinIcon.OPEN_BOOK),
              objective.getTitle() + " (due by: " + dateFormat.format(objective.getDueDate()) + ")",
              objective.getContent(),
              details

      );

      // Dividers for all but the last item
      item.setDividerVisible(counter != objectives.size());
      items.add(item);
    }
    Button addNewObjective = new Button("Add a new objective");
    addNewObjective.addClickListener(e -> showObjectives(null));

    addNewObjective.addClassName(LumoStyles.Margin.Left.L);
    items.add(addNewObjective);
    return items;
  }

  private void showReportDetails(Report report) {
    setViewDetails(createReportDrawer(report));
    reportDrawer.setContent(createReportDetails(report));
    reportDrawer.show();
  }

  private void showObjectives(Objective objective) {
    setViewDetails(createObjectiveDrawer(objective));
    objectiveDrawer.setContent(createObjective(objective));
    objectiveDrawer.show();
  }

  private Component createReportDetails(Report report) {
    ListItem content = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.FILE_TEXT_O),
        report.getContent(), "Content");

    ListItem docter = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.DOCTOR),
        "Peter Petersen", "Docter");

    ListItem date = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR_O),
        "21.05.2019", "Date");


    for (ListItem item : new ListItem[] {content, docter, date}) {
      item.setReverse(true);
      item.setWhiteSpace(WhiteSpace.PRE_LINE);
    }

    Div details = new Div(content, docter, date);
    details.addClassName(LumoStyles.Padding.Vertical.S);
    return details;
  }

  private Component createMessageView(Report report) {
    return new ChatWidget(report);
  }

  private Component createObjective(Objective objective) {
    Div objectives = new Div();

    objectives.addClassNames(
            LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L
    );

    TextField titleField = new TextField("Title");
    titleField.setWidth("100%");

    TextArea contentField = new TextArea("Content");
    contentField.setWidth("100%");

    DatePicker dateField = new DatePicker("Due date");
    dateField.setWidth("100%");


    NumberField progressField = new NumberField("Progress in % (0-100)");
    progressField.setWidth("40%");
    progressField.setMax(100);
    progressField.setMin(0);

    NumberField weightField = new NumberField("Weight");
    weightField.setWidth("40%");
    weightField.addClassName(LumoStyles.Margin.Horizontal.L);

    ComboBox<Objective> cmbBox = new ComboBox<>("Parent");
    cmbBox.setItems(new ObjectiveQuerier().getByPatient(patient.getId()));
    cmbBox.setItemLabelGenerator(Objective::getTitle);
    cmbBox.setWidth("100%");

    Button saveButton = new Button("Save");

    ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
    saveButton.addClickListener(
            e -> {
              patientPresenter.createOrUpdateObjectives(
                    objective == null ? null : objective.getId(), titleField.getValue(), contentField.getValue(),
                    dateField.getValue(), progressField.getValue(), weightField.getValue(), patient.getId(), cmbBox.getValue() == null ? null : cmbBox.getValue().getId(), doctorId
              );
              Notification.show("Objective has been saved");
              UI.getCurrent().getPage().reload();
            }
    );

    if (objective != null) {
      titleField.setValue(objective.getTitle());
      contentField.setValue(objective.getContent());
      dateField.setValue(objective.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      progressField.setValue(objective.getProgress());
      weightField.setValue(objective.getWeight());
    }

    // add fields to view
    objectives.add(titleField);
    objectives.add(contentField);
    objectives.add(dateField);
    objectives.add(progressField);
    objectives.add(weightField);
    objectives.add(cmbBox);
    objectives.add(saveButton);

    return objectives;
  }

  private Component createSubtaskView(Objective objective) {
    return new SubtaskWidget(objective, patient);
  }

  private DetailsDrawer createReportDrawer(Report report) {
    reportDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    // Header
    Tab reportDetailTab = new Tab("Details");
    Tab messageTab = new Tab("Messages");

    Tabs tabs = new Tabs(reportDetailTab, messageTab);
    tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

    tabs.addSelectedChangeListener(e -> {
      Tab selectedTab = tabs.getSelectedTab();
      if (selectedTab.equals(reportDetailTab)) {
        reportDrawer.setContent(createReportDetails(report));
      } else if (selectedTab.equals(messageTab)) {
        reportDrawer.setContent(createMessageView(report));
      }
    });

    DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Report", tabs);
    detailsDrawerHeader.addCloseListener(buttonClickEvent -> reportDrawer.hide());
    reportDrawer.setHeader(detailsDrawerHeader);

    return reportDrawer;
  }

  private DetailsDrawer createObjectiveDrawer(Objective objective){
    objectiveDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    // header
    Tab objectiveDetailTab = new Tab("Details");
    Tab objectiveSubtaskTab = new Tab("Subtask");

    Tabs tabs = new Tabs(objectiveDetailTab, objectiveSubtaskTab);
    tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

    tabs.addSelectedChangeListener(
        e -> {
          Tab selectedTab = tabs.getSelectedTab();
          if (selectedTab.equals(objectiveDetailTab)) {
            objectiveDrawer.setContent(createObjective(objective));
          } else if (selectedTab.equals(objectiveSubtaskTab)) {
            objectiveDrawer.setContent(createSubtaskView(objective));
          }
        });

    DetailsDrawerHeader objectiveDrawerHeader = new DetailsDrawerHeader("Objective", tabs);
    objectiveDrawerHeader.addCloseListener(buttonClickEvent -> objectiveDrawer.hide());
    objectiveDrawer.setHeader(objectiveDrawerHeader);

    return objectiveDrawer;

  }
}
