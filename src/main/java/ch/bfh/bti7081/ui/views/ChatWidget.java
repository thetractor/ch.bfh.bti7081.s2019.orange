package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.util.LumoStyles;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.bson.types.ObjectId;

public class Messages extends VerticalLayout {

    public Messages(){

        Div header = new Div(new Label("Messages"));
        Div divider = new Div(new Divider("1px"));
        divider.addClassName(LumoStyles.Padding.Responsive.Vertical.M);
        Div form = new Div(new Label("Form"));
        Div messages = new Div(header, divider, form);
        messages.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);

        add(messages);

    }



}
