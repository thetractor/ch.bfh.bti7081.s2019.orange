package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.PatientPresenter;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawer;
import ch.bfh.bti7081.ui.components.detailsdrawer.DetailsDrawerHeader;
import ch.bfh.bti7081.ui.layout.size.Top;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.BorderRadius;
import ch.bfh.bti7081.ui.util.css.WhiteSpace;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import model.entities.Patient;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.time.LocalDate;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;


@Route(value = "patient-details", layout = MainLayout.class)
@PageTitle("Patient Details")
public class PatientDetail extends SplitViewFrame implements HasUrlParameter<String> {

  public int LENGTH_REPORT_LIST = 2;

  private DetailsDrawer detailsDrawer;
  private Patient patient;
  private ListItem patrientName;
  private ListItem patientDisorder;
  private ListItem patientMedication;
  private PatientPresenter patientPresenter = new PatientPresenter();


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
    setViewContent(createContent());
    patient = patientPresenter.getPatient(new ObjectId(patientIdString));
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


    patrientName = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), "Patient Name",
        "Patient");
    patrientName.getPrimary().addClassName(LumoStyles.Heading.H2);
    patrientName.setDividerVisible(true);
    patrientName.setReverse(true);

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

    FlexBoxLayout listItems = new FlexBoxLayout(patrientName, patientDisorder, patientMedication);
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
    Div items = new Div();
    items.addClassNames(BoxShadowBorders.BOTTOM,
        LumoStyles.Padding.Bottom.L);

    for (int i = 0; i < LENGTH_REPORT_LIST; i++) {
      Button details = UIUtils.createSmallButton("Details");
      // todo: Pass report object to showDetails function
      details.addClickListener(e -> showDetails());
      ListItem item = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EDIT),
          "Report Title",
          "Doctor name",
          details
         );

      // Dividers for all but the last item
      item.setDividerVisible(i < LENGTH_REPORT_LIST - 1);
      items.add(item);
    }

    return items;
  }

  private void showDetails() {  // todo: Expect report object
    detailsDrawer.setContent(createDetails()); // todo: pass report object
    detailsDrawer.show();
  }

  private Component createDetails() {  // todo: Expect report object
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

  private Component cerateMessages() {
    Div header = new Div(new Label("Messages"));
    Div divider = new Div(new Divider("1px"));
    divider.addClassName(LumoStyles.Padding.Responsive.Vertical.M);
    Div form = new Div(new Label("Form"));
    Div messages = new Div(header, divider, form);
    messages.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return messages;
  }

  private Component createGoals() {
    Div goals = new Div(new Label("Goals"));
    goals.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return goals;
  }

  private DetailsDrawer createDetailsDrawer() {
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
        detailsDrawer.setContent(createDetails());
      } else if (selectedTab.equals(messages)) {
        detailsDrawer.setContent(cerateMessages());
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
