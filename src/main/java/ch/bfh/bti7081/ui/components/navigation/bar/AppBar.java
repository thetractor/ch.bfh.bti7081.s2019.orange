package ch.bfh.bti7081.ui.components.navigation.bar;

import ch.bfh.bti7081.presenter.HomePresenter;
import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.components.navigation.tab.NaviTab;
import ch.bfh.bti7081.ui.components.navigation.tab.NaviTabs;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.views.Home;
import ch.bfh.bti7081.ui.views.PatientDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import java.util.List;

import ch.bfh.bti7081.model.entities.Doctor;
import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.entities.Report;
import ch.bfh.bti7081.model.message.MessageNotificationDispatcher;
import org.bson.types.ObjectId;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;

public class AppBar extends Composite<FlexLayout> {

    private HomePresenter homePresenter;

    private String CLASS_NAME = "app-bar";

    private FlexBoxLayout container;

    private Button menuIcon;
    private Button contextIcon;

    private H4 title;
    private FlexBoxLayout actionItems;
    private Image messages;
    private Image avatar;
    private Label doctorName;

    private FlexBoxLayout tabContainer;
    private NaviTabs tabs;
    private Button addTab;

    private TextField search;
    private Registration searchRegistration;
    private Registration messageNotifyRegistration;

    public enum NaviMode {
        MENU, CONTEXTUAL
    }

    public AppBar(String title, NaviTab... tabs) {
        getContent().setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);
        homePresenter = new HomePresenter();

        initMenuIcon();
        initContextIcon();
        initTitle(title);
        initSearch();
        initNotifications();
        initAvatar();
        initDoctorName();
        initActionItems();
        initContainer();
        initTabs(tabs);
    }

    public void setNaviMode(NaviMode mode) {
        if (mode.equals(NaviMode.MENU)) {
            menuIcon.setVisible(true);
            contextIcon.setVisible(false);
        } else {
            menuIcon.setVisible(false);
            contextIcon.setVisible(true);
        }
    }

    private void initMenuIcon() {
        menuIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
        menuIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        menuIcon.addClassName(CLASS_NAME + "__navi-icon");
        menuIcon.addClickListener(e -> MainLayout.get().getNaviDrawer().toggle());
        UIUtils.setAriaLabel("Menu", menuIcon);
    }

    private void initContextIcon() {
        contextIcon = UIUtils
                .createTertiaryInlineButton(VaadinIcon.ARROW_LEFT);
        contextIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        contextIcon.addClassNames(CLASS_NAME + "__context-icon");
        contextIcon.setVisible(false);
        UIUtils.setAriaLabel("Back", contextIcon);
    }

    private void initTitle(String title) {
        this.title = new H4(title);
        this.title.setClassName(CLASS_NAME + "__title");
    }

    private void initSearch() {
        search = new TextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setVisible(false);
    }

    private void initAvatar() {
        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc(IMG_PATH + "avatar.png");
        avatar.setAlt("User menu");

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        List<Doctor> doctorList = homePresenter.getDoctors();
        for (Doctor doctor:doctorList) {
            String doctorName = doctor.getName() + " " + doctor.getSurname();
            contextMenu.addItem(doctorName,
                e -> clickDoctorEvent(doctor));
        }
    }

    private void clickDoctorEvent(Doctor doctor){
        VaadinSession.getCurrent().setAttribute("doctorId", doctor.getId());
        VaadinSession.getCurrent().setAttribute("doctorName", doctor.getName() + " " + doctor.getSurname());
        if(messageNotifyRegistration != null){
            messageNotifyRegistration.remove();
            messageNotifyRegistration = null;
        }
        UI.getCurrent().getPage().reload();
    }

    /**
     * initializes the notification icon and registers a listener to the MessageNotificationDispatcher
     */
    private void initNotifications() {
        messages = new Image();
        messages.setClassName(CLASS_NAME + "__notification");
        messages.setSrc(IMG_PATH + "logo-17.png");
        messages.setWidth("20px");
        messages.setHeight("20px");
        messages.setAlt("Notifications");
        messages.getStyle().set("display", "none");

        ObjectId doctorId = (ObjectId) VaadinSession.getCurrent().getAttribute("doctorId");

        if (doctorId == null){
            messages.setVisible(false);
            return;
        }

        ContextMenu contextMenu = new ContextMenu(messages);
        contextMenu.setOpenOnClick(true);

        UI ui = UI.getCurrent();

        // Register a lambda function to the Broadcaster, which defines what to do in dispatch()
        // Register returns a lambda, used to remove the registration
        messageNotifyRegistration = MessageNotificationDispatcher.register(newMessage -> {
            addMessageNotification(newMessage, contextMenu, doctorId, ui);
        }, doctorId);
    }

    /**
     * Sets the notification icon to visible and adds the message details to the notification list
     * @param message Message the notification is about
     * @param messageContextMenu Contextmenu which shows the notification
     * @param doctorId doctorId of the Doc who receives the notification on the UI
     * @param ui Ui that has to be updated
     */
    private void addMessageNotification(Message message, ContextMenu messageContextMenu, ObjectId doctorId, UI ui){
        if (doctorId.equals(message.getFromDoctorId())) {
            return;
        }
        ui.access(() -> messages.getStyle().set("display", "block"));

        Doctor doctor = homePresenter.getDoctor(message.getFromDoctorId());
        Patient patient = homePresenter.getPatientByReport(message.getReportId());
        String text = doctor.getFullName() + " wrote regarding patient " + patient.getFullName() + ":\n" + message.getContent();
        messageContextMenu.addItem(text,
                e -> clickMessageEvent(message));
    }

    /**
     * Handles the click on a message notification and redirects to the corresponding patient
     * @param message
     */
    private void clickMessageEvent(Message message){
        Report report = homePresenter.getReport(message.getReportId());
        Patient patient = homePresenter.getPatientByReport(message.getReportId());
        UI.getCurrent().navigate(PatientDetail.class, patient.getId().toString());
        //ToDo: Open Report directly
    }

    private void initActionItems() {
        actionItems = new FlexBoxLayout();
        actionItems.addClassName(CLASS_NAME + "__action-items");
        actionItems.setVisible(false);
    }

    private void initDoctorName() {
      String name = (String) VaadinSession.getCurrent().getAttribute("doctorName");
      doctorName = new Label(name);
    }

    private void initContainer() {
        container = new FlexBoxLayout(menuIcon, contextIcon, this.title, search,
                actionItems, messages, doctorName, avatar);
        container.addClassName(CLASS_NAME + "__container");
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.setFlexGrow(1, search);
        getContent().add(container);
    }

    private void initTabs(NaviTab... tabs) {
        addTab = UIUtils.createSmallButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> this.tabs
                .setSelectedTab(addClosableNaviTab("New Tab", Home.class)));
        addTab.setVisible(false);

        this.tabs = tabs.length > 0 ? new NaviTabs(tabs) : new NaviTabs();
        this.tabs.setClassName(CLASS_NAME + "__tabs");
        this.tabs.setVisible(false);
        for (NaviTab tab : tabs) {
            configureTab(tab);
        }

        tabContainer = new FlexBoxLayout(this.tabs, addTab);
        tabContainer.addClassName(CLASS_NAME + "__tab-container");
        tabContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        getContent().add(tabContainer);
    }

    /* === MENU ICON === */

    public Button getMenuIcon() {
        return menuIcon;
    }

    /* === CONTEXT ICON === */

    public Button getContextIcon() {
        return contextIcon;
    }

    public void setContextIcon(Icon icon) {
        contextIcon.setIcon(icon);
        contextIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
    }

    /* === TITLE === */

    public String getTitle() {
        return this.title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    /* === ACTION ITEMS === */

    public Component addActionItem(Component component) {
        actionItems.add(component);
        updateActionItemsVisibility();
        return component;
    }

    public Button addActionItem(VaadinIcon icon) {
        Button button = UIUtils.createButton(icon, ButtonVariant.LUMO_SMALL,
                ButtonVariant.LUMO_TERTIARY);
        addActionItem(button);
        return button;
    }

    public void removeAllActionItems() {
        actionItems.removeAll();
        updateActionItemsVisibility();
    }

    /* === TABS === */

    public void centerTabs() {
        tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
    }

    private void configureTab(Tab tab) {
        tab.addClassName(CLASS_NAME + "__tab");
        updateTabsVisibility();
    }

    public Tab addTab(String text) {
        Tab tab = tabs.addTab(text);
        configureTab(tab);
        return tab;
    }

    public Tab addTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab addClosableNaviTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addClosableTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text,
            Class<? extends Component> navigationTarget) {
        tabs.updateSelectedTab(text, navigationTarget);
    }

    public void navigateToSelectedTab() {
        tabs.navigateToSelectedTab();
    }

    public void addTabSelectionListener(
            ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabs.addSelectedChangeListener(listener);
    }

    public int getTabCount() {
        return tabs.getTabCount();
    }

    public void removeAllTabs() {
        tabs.removeAll();
        updateTabsVisibility();
    }

    /* === ADD TAB BUTTON === */

    public void setAddTabVisible(boolean visible) {
        addTab.setVisible(visible);
    }

    /* === SEARCH === */

    public void searchModeOn() {
        menuIcon.setVisible(false);
        title.setVisible(false);
        actionItems.setVisible(false);
        tabContainer.setVisible(false);

        contextIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        contextIcon.setVisible(true);
        searchRegistration = contextIcon
                .addClickListener(e -> searchModeOff());

        search.setVisible(true);
        search.focus();
    }

    public void addSearchListener(HasValue.ValueChangeListener listener) {
        search.addValueChangeListener(listener);
    }

    public void setSearchPlaceholder(String placeholder) {
        search.setPlaceholder(placeholder);
    }

    private void searchModeOff() {
        menuIcon.setVisible(true);
        title.setVisible(true);
        tabContainer.setVisible(true);

        updateActionItemsVisibility();
        updateTabsVisibility();

        contextIcon.setVisible(false);
        searchRegistration.remove();

        search.clear();
        search.setVisible(false);
    }

    /* === RESET === */

    public void reset() {
        title.setText("");
        setNaviMode(AppBar.NaviMode.MENU);
        removeAllActionItems();
        removeAllTabs();
    }

    /* === UPDATE VISIBILITY === */

    private void updateActionItemsVisibility() {
        actionItems.setVisible(actionItems.getComponentCount() > 0);
    }

    private void updateTabsVisibility() {
        tabs.setVisible(tabs.getComponentCount() > 0);
    }

    public Image getAvatar() {
        return avatar;
    }
}
