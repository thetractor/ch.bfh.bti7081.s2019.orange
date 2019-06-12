package ch.bfh.bti7081.ui.widgets;

import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.layout.size.Bottom;
import ch.bfh.bti7081.ui.util.BoxShadowBorders;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.BorderRadius;
import ch.bfh.bti7081.ui.util.css.FlexDirection;
import ch.bfh.bti7081.ui.util.css.FlexWrap;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import static ch.bfh.bti7081.ui.util.UIUtils.IMG_PATH;

/**
 * Class for creating patient image and details for
 * patient detail view.
 *
 * @author lars.peyer@students.bfh.ch
 */

public class PatientImageWidget extends FlexBoxLayout {

  public PatientImageWidget(Patient patient) {
    addContent(patient);
  }

  private void addContent(Patient patient) {
    Image image = new Image();
    image.setSrc(IMG_PATH + "patient.png");
    image.setAlt("Patient image");
    image.setHeight("250px");
    image.setWidth("250px");
    image.addClassName(LumoStyles.Margin.Horizontal.L);
    UIUtils.setBorderRadius(BorderRadius._50, image);


    ListItem patientName = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), patient.getFullName(),
        "Patient");
    patientName.getPrimary().addClassName(LumoStyles.Heading.H2);
    patientName.setDividerVisible(true);
    patientName.setReverse(true);

    ListItem patientDisorder = new ListItem(
        UIUtils.createTertiaryIcon(VaadinIcon.SPARK_LINE), "Anxiety disorders",
        "Disorder");
    patientDisorder.getPrimary().addClassName(LumoStyles.Heading.H2);
    patientDisorder.setDividerVisible(true);
    patientDisorder.setReverse(true);

    ListItem patientMedication = new ListItem(
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

    add(section);
  }
}
