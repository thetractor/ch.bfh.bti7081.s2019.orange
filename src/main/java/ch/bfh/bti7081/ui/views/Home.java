package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.ui.MainLayout;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.layout.size.Horizontal;
import ch.bfh.bti7081.ui.layout.size.Vertical;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.FlexDirection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("PMS-Orange :: Home")
public class Home extends ViewFrame {

    public Home() {
        setId("home");
        setViewContent(createContent());
    }

    private Component createContent() {
        Html intro = new Html("<div><h1>Welcome to PMS Orange</h1><h3>The ultimate patients management tool developed by team orange. This is project task in Software Engineering & Design at the BFH Berne</h3></div>");

        Html readme = new Html("<p>To get started with using the PMS make sure you login as one of the doctors. To do so click on the doctor icon in the top right corner and select the desired doctor." +
            "This will log you in as the chosen docten and you will have access to its patients through the side navigation on the left side.</p>");

        Anchor source = new Anchor("https://github.com/thetractor/ch.bfh.bti7081.s2019.orange", UIUtils.createButton("Get the source code from Github", VaadinIcon.CODE));

        FlexBoxLayout content = new FlexBoxLayout(intro, readme, source);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setMaxWidth("840px");
        content.setPadding(Horizontal.RESPONSIVE_L, Vertical.L);
        return content;
    }

}
