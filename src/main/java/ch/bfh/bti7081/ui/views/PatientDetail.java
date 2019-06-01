package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import ch.bfh.bti7081.Presenter.ReportPresenter;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawer;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawerHeader;
import ch.bfh.bti7081.ui.layout.size.Top;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.BorderRadius;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import ch.bfh.bti7081.ui.util.css.FlexWrap;
import com.vaadin.flow.server.VaadinSession;
import model.entities.Objective;
import model.entities.Patient;
import model.entities.Report;
import model.objective.ObjectiveQuerier;
import model.patient.PatientQuerier;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;


@Route(value = "patient-details", layout = MainLayout.class)
@PageTitle("Patient Details")
public class PatientDetail extends SplitViewFrame implements HasUrlParameter<String> {

  public int LENGTH_REPORT_LIST = 3;

  private DetailsDrawer detailsDrawer;
  private Patient patient;
  private ListItem patientName;
  private ListItem patientDisorder;
  private ListItem patientMedication;
  private PatientPresenter patientPresenter = new PatientPresenter();
  private ReportPresenter reportPresenter = new ReportPresenter();


  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    AppBar appBar = initAppBar();
    appBar.setTitle(patient.getName() + " " + patient.getSurname());
    UI.getCurrent().getPage().setTitle(patient.getName() + " " + patient.getSurname());

    setViewContent(createContent());
    setViewDetails(createDetailsDrawer());
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
            createLogoSection(),
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

  private FlexBoxLayout createLogoSection() {
    Image image = new Image();
    image.setSrc(IMG_PATH + "patient.png");
    image.setAlt("Patient image");
    image.setHeight("250px");
    image.setWidth("250px");
    image.addClassName(LumoStyles.Margin.Horizontal.L);
    UIUtils.setBorderRadius(BorderRadius._50, image);


    patientName = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), patient.getFullName(),
        "Patient");
    patientName.getPrimary().addClassName(LumoStyles.Heading.H2);
    patientName.setDividerVisible(true);
    patientName.setReverse(true);

    patientDisorder = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.SPARK_LINE), "Anxiety disorders",
        "Disorder");
    patientDisorder.getPrimary().addClassName(LumoStyles.Heading.H2);
    patientDisorder.setDividerVisible(true);
    patientDisorder.setReverse(true);

    patientMedication = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.PILLS), "Panic Disorder Booster 400mg",
        "Medication");
    patientMedication.getPrimary().addClassName(LumoStyles.Heading.H2);
    patientMedication.setReverse(true);

    FlexBoxLayout listItems = new FlexBoxLayout(patientName, patientDisorder, patientMedication);
    listItems.setFlexDirection(FlexDirection.COLUMN);

    FlexBoxLayout section = new FlexBoxLayout(image, listItems);
    section.addClassName(BoxShadowBorders.BOTTOM);
    section.setAlignItems(FlexComponent.Alignment.CENTER);
    section.setFlex("1", listItems);
    section.setFlexWrap(FlexWrap.WRAP);
    section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    section.setPadding(Bottom.L);
    return section;
  }

  private Component createSubHeader(String titleText) {
    Label title = UIUtils.createH3Label(titleText);

    FlexBoxLayout header = new FlexBoxLayout(title);
    header.setAlignItems(FlexComponent.Alignment.CENTER);
    header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
    return header;
  }

  private Component createRecentReportsList() {
    Div items = new Div();
    items.addClassNames(BoxShadowBorders.BOTTOM,
            LumoStyles.Padding.Bottom.L);

    List<Report> reports = reportPresenter.getReportsByPatentId(patient.getId(), LENGTH_REPORT_LIST);
    int counter = 0;
    for (Report report : reports) {
      counter++;
      Button details = UIUtils.createSmallButton("Details");
      // todo: Pass report object to showDetails function
      details.addClickListener(e -> showDetails());
      ListItem item = new ListItem(
              UIUtils.createTertiaryIcon(VaadinIcon.EDIT),
              "created by DOCTOR",
              report.getContent(),
              details
      );

      // Dividers for all but the last item
      item.setDividerVisible(counter != reports.size());
      items.add(item);
    }


    return items;
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
      Button details = UIUtils.createSmallButton("Show objective");
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

  private void showDetails() {  // todo: Expect report object
    detailsDrawer.setContent(createDetails()); // todo: pass report object
    detailsDrawer.show();
  }

  private void showObjectives(Objective objective) {
    detailsDrawer.setContent(createObjective(objective));
    detailsDrawer.show();
  }

  private Component createDetails() {  // todo: Expect report object
    Div details = new Div(new Label("Details"));
    details.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return details;
  }

  private Component cerateMessages() {
    Div header = new Div(new Label("Messages"));
    Div divider = new Div(new Divider("1px"));
    divider.addClassName(LumoStyles.Padding.Responsive.Vertical.M);
    Div form = new Div(new Label("Form"));
    Div messages = new Div(header, divider, form);
    messages.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return messages;
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
    cmbBox.setItems(new ObjectiveQuerier().getByPatient(patient.getId(), null));
    cmbBox.setItemLabelGenerator(Objective::getTitle);
    cmbBox.setWidth("100%");

    Button saveButton = new Button("Save");

    ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");
    saveButton.addClickListener(
            e -> patientPresenter.createOrUpdateObjectives(
                    objective == null ? null : objective.getId(), titleField.getValue(), contentField.getValue(),
                    dateField.getValue(), progressField.getValue(), weightField.getValue(), patient.getId(), cmbBox.getValue().getId(), doctorId
            )
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

  private DetailsDrawer createDetailsDrawer() {
    detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    // Header
    Tab details = new Tab("Details");
    Tab messages = new Tab("Messages");
    Tab objectives = new Tab("Objectives");

    Tabs tabs = new Tabs(details, messages, objectives);
    tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
    tabs.addSelectedChangeListener(e -> {
      Tab selectedTab = tabs.getSelectedTab();
      if (selectedTab.equals(details)) {
        detailsDrawer.setContent(createDetails());
      } else if (selectedTab.equals(messages)) {
        detailsDrawer.setContent(cerateMessages());
      } else if (selectedTab.equals(objectives)) {
        detailsDrawer.setContent(createObjective(null));
      }
    });

    DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Report Details", tabs);
    detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
    detailsDrawer.setHeader(detailsDrawerHeader);

    return detailsDrawer;
  }

}
