package ch.bfh.bti7081.Presenter;

import model.doctor.DoctorQuerier;
import model.entities.Objective;
import model.entities.Patient;
import model.patient.PatientQuerier;
import model.objective.ObjectiveQuerier;
import org.bson.types.ObjectId;
import java.util.List;

/**
 * Implementation of the <code>PatientPresenter</code> class.
 * For further information please refer to the authors.
 *
 * @author matthias.ossola@students.bfh.ch
 */
public class PatientPresenter {

    private DoctorQuerier doctorQuerier;
    private PatientQuerier patientQuerier;
    private ObjectiveQuerier objectiveQuerier;

    public PatientPresenter(){
        doctorQuerier = new DoctorQuerier();
        patientQuerier = new PatientQuerier();
        objectiveQuerier = new ObjectiveQuerier();
    }

    /**
     * Get all patients of a doctor
     *
     * @param id ObjectId of the doctor
     * @return   list of all patients from the given doctor
     */
    public List<Patient> getPatientsByDoctorId(ObjectId id) {
        return doctorQuerier.getPatients(id);
    }

    public Patient getPatient(ObjectId patientId){
        return patientQuerier.get(patientId);
    }


    /**
     * Get all objectives of a doctor
     *
     * @param id ObjectId of the doctor
     * @return   list of all objectives from the given doctor
     */
    public List<Objective> getObjectives(ObjectId id) {
        return objectiveQuerier.getByPatient(id);
    }
}
