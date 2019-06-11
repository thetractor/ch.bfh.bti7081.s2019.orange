package ch.bfh.bti7081.ui.components.navigation.bar;

import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.components.navigation.tab.NaviTabs;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.views.Home;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;

public class TabBar extends Composite<FlexLayout> {

    private static final String CLASS_NAME = "tab-bar";

    private Button menuIcon;

    private Image avatar;

    private Button addTab;
    private NaviTabs tabs;

    public TabBar() {
        getContent().setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        menuIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
        menuIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        menuIcon.addClassName(CLASS_NAME + "__navi-icon");
        menuIcon.addClickListener(e -> MainLayout.get().getNaviDrawer().toggle());

        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc(IMG_PATH + "avatar.png");

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("john.smith@gmail.com",
                e -> System.out.println("Testing..."));
        contextMenu.addItem("john.smith@yahoo.com",
                e -> System.out.println("Testing..."));
        contextMenu.addItem("Settings", e -> System.out.println("Testing..."));
        contextMenu.addItem("Logout", e -> System.out.println("Testing..."));

        addTab = UIUtils.createSmallButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> tabs
                .setSelectedTab(addClosableTab("New Tab", Home.class)));
        addTab.setClassName(CLASS_NAME + "__add-tab");

        tabs = new NaviTabs();
        tabs.setClassName(CLASS_NAME + "__tabs");

        getContent().add(menuIcon, tabs, addTab, avatar);
    }

    /* === TABS === */

    private void configureTab(Tab tab) {
        tab.addClassName(CLASS_NAME + "__tab");
    }


    public Tab addClosableTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addClosableTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }


    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text,
            Class<? extends Component> navigationTarget) {
        tabs.updateSelectedTab(text, navigationTarget);
    }


    public int getTabCount() {
        return tabs.getTabCount();
    }
}
