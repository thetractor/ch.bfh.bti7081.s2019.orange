package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import ch.bfh.bti7081.Presenter.ReportPresenter;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawer;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawerHeader;
import ch.bfh.bti7081.ui.layout.size.Top;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.BorderRadius;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
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
import model.entities.Patient;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.function.Function;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;


@Route(value = "patient-details", layout = MainLayout.class)
@PageTitle("Patient Details")
public class PatientDetail extends SplitViewFrame implements HasUrlParameter<String> {

  public int LENGTH_REPORT_LIST = 2;

  private DetailsDrawer detailsDrawer;
  private Patient patient;
  private ListItem patientName;
  private ListItem patientDisorder;
  private ListItem patientMedication;
  private PatientPresenter patientPresenter = new PatientPresenter();
  private ReportPresenter reportPresenter = new ReportPresenter();
  List<Report> patientReports;


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
    //setViewDetails(createDetailsDrawer());
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
    FlexBoxLayout content = new FlexBoxLayout(createLogoSection(),
        createRecentReportsHeader(),
        createRecentReportsList());
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
        UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), "Patient Name",
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

  private Component createRecentReportsHeader() {
    Label title = UIUtils.createH3Label("Recent Reports");

    FlexBoxLayout header = new FlexBoxLayout(title);
    header.setAlignItems(FlexComponent.Alignment.CENTER);
    header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
    return header;
  }

  private Component createRecentReportsList() {
    // Passes a reference to the showDetails function which shall be called after pressing of a reports
    // Detail-button. The showDetails function requires the id of the given report.
    Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction =  (report) -> {
      ComponentEventListener<ClickEvent<Button>> clickEvent = e -> showDetails(report);
      return clickEvent;
    };
    ReportsWidget reportsWidget = new ReportsWidget(patient, callBackFunction);
    return reportsWidget;
  }

  private void showDetails(Report report) {
    setViewDetails(createDetailsDrawer(report));
    //detailsDrawer.setContent(createDetails(report)); // todo: pass report object
    detailsDrawer.show();
  }

  private Component createDetails(Report report) {  // todo: Expect report object
//    ListItem status = new ListItem(payment.getStatus().getIcon(),
//        payment.getStatus().getName(), "Status");
//
//    status.getContent().setAlignItems(FlexComponent.Alignment.BASELINE);
//    status.getContent().setSpacing(Bottom.XS);
//    UIUtils.setTheme(payment.getStatus().getTheme().getThemeName(),
//        status.getPrimary());
//    UIUtils.setTooltip(payment.getStatus().getDesc(), status);
//
//    ListItem from = new ListItem(
//        UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT),
//        payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
//    ListItem to = new ListItem(
//        UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT),
//        payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
//    ListItem amount = new ListItem(
//        UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR),
//        UIUtils.formatAmount(payment.getAmount()), "Amount");
//    ListItem date = new ListItem(
//        UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR),
//        UIUtils.formatDate(payment.getDate()), "Date");
//
//    for (ListItem item : new ListItem[] { status, from, to, amount,
//        date }) {
//      item.setReverse(true);
//      item.setWhiteSpace(WhiteSpace.PRE_LINE);
//    }
//
//    Div details = new Div(status, from, to, amount, date);
//    details.addClassName(LumoStyles.Padding.Vertical.S);
//    return details;


    Div details = new Div(new Label("Details"));
    details.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return details;
  }

  private Component createMessageView(Report report) {
    ChatWidget messageView = new ChatWidget(report);
    return messageView;
  }

  private Component createGoals() {
    Div goals = new Div(new Label("Goals"));
    goals.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return goals;
  }

  private DetailsDrawer createDetailsDrawer(Report report) {
    detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    // Header
    Tab details = new Tab("Details");
    Tab messages = new Tab("Messages");
    Tab goals = new Tab("Goals");

    Tabs tabs = new Tabs(details, messages, goals);
    tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
    tabs.addSelectedChangeListener(e -> {
      Tab selectedTab = tabs.getSelectedTab();
      if (selectedTab.equals(details)) {
        detailsDrawer.setContent(createDetails(report));
      } else if (selectedTab.equals(messages)) {
        detailsDrawer.setContent(createMessageView(report));
      } else if (selectedTab.equals(goals)) {
        detailsDrawer.setContent(createGoals());
      }
    });

    DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Report Details", tabs);
    detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
    detailsDrawer.setHeader(detailsDrawerHeader);

    return detailsDrawer;
  }

}
