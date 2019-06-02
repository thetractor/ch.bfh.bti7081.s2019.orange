package ch.bfh.bti7081.ui.views;

import ch.bfh.bti7081.presenter.ReportPresenter;
import ch.bfh.bti7081.ui.components.ListItem;
import ch.bfh.bti7081.ui.util.BoxShadowBorders;
import ch.bfh.bti7081.ui.util.LumoStyles;
import ch.bfh.bti7081.ui.util.UIUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import ch.bfh.bti7081.model.doctor.DoctorQuerier;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.entities.Report;
import java.util.List;
import java.util.function.Function;


/**
 * Implementation of the report view
 *
 * @author lars.peyer@students.bfh.ch
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
public class ReportsWidget extends Div {

    private ReportPresenter reportPresenter = new ReportPresenter();
    private Patient patient;
    private List<Report> patientReports;
    private Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction;
    private DoctorQuerier doctorQuerier;

    public ReportsWidget(Patient patient, Function<Report, ComponentEventListener<ClickEvent<Button>>> callBackFunction) {
        this.patient = patient;
        this.callBackFunction = callBackFunction;
        patientReports = reportPresenter.getReportsByPatentId(patient.getId(), 10);
        doctorQuerier = new DoctorQuerier();

        addClassNames(BoxShadowBorders.BOTTOM,
                LumoStyles.Padding.Bottom.L);

        addReportData();
    }


    private void addReportData(){

        for (Integer i = 0; i < patientReports.size(); i++) {
            Report report = patientReports.get(i);

            Button details = UIUtils.createSmallButton("Details");
            details.addClickListener(callBackFunction.apply(report));
            ListItem item = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EDIT),
                    report.getContent(),
                    "Doc",  // TODO: find out doctor from report
                    details
            );

            // Dividers for all but the last item
            boolean setVisible = i < patientReports.size() - 1;
            item.setDividerVisible(setVisible);
            add(item);
        }
    }


}
