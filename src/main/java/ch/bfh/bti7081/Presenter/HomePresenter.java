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
 * Implementation of the <code>HomePresenter</code> class.
 * For further information please refer to the authors.
 *
 * @author kevin.riesen@students.bfh.ch
 */
public class HomePresenter {

    private DoctorQuerier doctorQuerier;
    private PatientQuerier patientQuerier;
    private DossierQuerier dossierQuerier;
    private ReportQuerier reportQuerier;

    public HomePresenter(){
        doctorQuerier = new DoctorQuerier();
        patientQuerier = new PatientQuerier();
        dossierQuerier = new DossierQuerier();
        reportQuerier = new ReportQuerier();
    }

    /**
     * Get all doctors
     *
     * @return list of all doctors
     */
    public List<Doctor> getDoctors() {
        return doctorQuerier.getAll();
    }

    /**
     * gets a specific doctor
     * @param doctorId
     * @return Doctor
     */
    public Doctor getDoctor(ObjectId doctorId){
        return doctorQuerier.get(doctorId);
    }

    /**
     * gets the patient by the Id of one of it's reports
     * @param reportId
     * @return
     */
    public Patient getPatientByReport(ObjectId reportId){
        return patientQuerier.getByReport(reportId, dossierQuerier);
    }

    /**
     * get a specific Report
     * @param reportId
     * @return Report
     */
    public Report getReport(ObjectId reportId){
        return reportQuerier.get(reportId);
    }
}
