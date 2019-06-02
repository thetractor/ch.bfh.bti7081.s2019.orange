package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.doctor.DoctorQuerier;
import ch.bfh.bti7081.model.dossier.DossierQuerier;
import ch.bfh.bti7081.model.entities.Doctor;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.entities.Report;
import ch.bfh.bti7081.model.patient.PatientQuerier;
import ch.bfh.bti7081.model.report.ReportQuerier;
import org.bson.types.ObjectId;
import java.util.List;

/**
 * Implementation of the <code>ReportPresenter</code> class.
 * For further information please refer to the authors.
 *
 * @author adrian.berger@students.bfh.ch
 * @author lars.peyer@students.bfh.ch
 */
public class ReportPresenter {

    private ReportQuerier reportQuerier;
    private PatientQuerier patientQuerier;
    private DossierQuerier dossierQuerier;
    private DoctorQuerier doctorQuerier;

    public ReportPresenter(){
        reportQuerier = new ReportQuerier();
        patientQuerier = new PatientQuerier();
        dossierQuerier = new DossierQuerier();
        doctorQuerier = new DoctorQuerier();
    }

    /**
     * Get all reports of an patient
     *
     * @param patientId ObjectId of the patient
     * @return          list of all reports from the given patient
     */
    public List<Report> getReportsByPatentId(ObjectId patientId, Integer limit) {
        return dossierQuerier.getReports(
                getPatientById(patientId).getDossierId(),
                limit
        );
    }

    /**
     * Get patient by id
     *
     * @param id ObjectId of the patient
     * @return   the patient
     */
    public Patient getPatientById(ObjectId id) {
        return patientQuerier.get(id);
    }

    /**
     * Get report by id
     *
     * @param id ObjectId of the report
     * @return   the report
     */
    public Report getReportById(ObjectId id) {
        return reportQuerier.get(id);
    }

    public String getDoctorName(ObjectId id){
        return doctorQuerier.get(id).getName();
    }

    public String getDoctorSurName(ObjectId id){
        return doctorQuerier.get(id).getSurname();
    }

    public String getDoctorFullName(ObjectId id){
        Doctor doctor = doctorQuerier.get(id);
        return String.format("%s %s", doctor.getName(), doctor.getSurname());
    }
}
