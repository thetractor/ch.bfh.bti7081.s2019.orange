package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.Presenter.ReportPresenter;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.util.BoxShadowBorders;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import model.entities.Patient;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.function.Function;

public class ReportsWidget extends Div {

    private ReportPresenter reportPresenter = new ReportPresenter();
    private Patient patient;
    List<Report> patientReports;
    ComponentEventListener<ClickEvent<Button>> clickEvent;
    Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction;

    public ReportsWidget(Patient patient, Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction) {
        this.patient = patient;
        patientReports = reportPresenter.getReportsByPatentId(patient.getId());
        this.callBackFunction = callBackFunction;

        addClassNames(BoxShadowBorders.BOTTOM,
                LumoStyles.Padding.Bottom.L);

        addReportData();
    }


    private void addReportData(){

        for (Integer i = 0; i < patientReports.size(); i++) {
            Report report = patientReports.get(i);

            Button details = UIUtils.createSmallButton("Details");
            // todo: Pass report object to showDetails function
            details.addClickListener(callBackFunction.apply(report));
            ListItem item = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EDIT),
                    report.getContent(),
                    "Doctor name",
                    details
            );

            // Dividers for all but the last item
            boolean setVisible = i < patientReports.size() - 1;
            item.setDividerVisible(setVisible);
            add(item);
        }
    }


}
