package ch.bfh.bti7081.ui.widgets;
import ch.bfh.bti7081.ui.components.Divider;
import ch.bfh.bti7081.ui.components.FlexBoxLayout;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.util.UIUtils;
import ch.bfh.bti7081.ui.util.css.FlexDirection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.presenter.ObjectivePresenter;
import ch.bfh.bti7081.model.entities.Patient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Class for creating sub tasks to include in
 * side drawer of patient objective detail view.
 *
 * @author lars.peyer@students.bfh.ch
 */

public class SubtaskWidget extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private transient Objective objective;
    private transient List<Objective> children;
    private transient Patient patient;
    private transient ObjectivePresenter presenter = new ObjectivePresenter();


    public SubtaskWidget(Objective objective, Patient patient) {
        this.objective = objective;
        this.patient = patient;
        this.children = presenter.getObjectives(patient.getId(), objective.getId());
        addContent();
    }

    private void addContent() {
        Div content = new Div();
        content.setWidth("100%");

        for (Objective obj : children) {
            content.add(createSubtask(obj));
        }

        add(content);
    }

    private Component createSubtask(Objective obj) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        ListItem taskInfo = new ListItem(
            UIUtils.createTertiaryIcon(VaadinIcon.INFO_CIRCLE_O),
            dateFormat.format(obj.getDueDate()) + " / " + obj.getProgress() + "% / " + obj.getWeight(),
            "Due date / Progress / Weight"
        );
        ListItem task = new ListItem(obj.getTitle(), obj.getContent());
        Divider divider = new Divider("1px");

        FlexBoxLayout content = new FlexBoxLayout(task, taskInfo, divider);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }
}
