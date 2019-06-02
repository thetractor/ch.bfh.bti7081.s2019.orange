package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.doctor.DoctorQuerier;
import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.model.entities.Patient;
import ch.bfh.bti7081.model.objective.ObjectiveManipulator;
import ch.bfh.bti7081.model.patient.PatientQuerier;
import ch.bfh.bti7081.model.objective.ObjectiveQuerier;
import org.bson.types.ObjectId;

import java.time.LocalDate;
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
    public List<Objective> getObjectives(ObjectId id, ObjectId parent) {
        return objectiveQuerier.getByPatient(id);
    }

    /**
     * Get all objectives of a doctor
     *
     * @param id ObjectId of the doctor
     */
    public void createOrUpdateObjectives(
            ObjectId id, String title, String content, LocalDate dueDate,
            double progress, double weight, ObjectId patientId, ObjectId parentId,ObjectId doctorId
    ) {
        ObjectiveManipulator manipulator = new ObjectiveManipulator();
        if (id == null) {
            manipulator.build(
                    content, java.sql.Date.valueOf(dueDate), doctorId, patientId, title, weight, progress, parentId
            );
        } else {
            manipulator.update(
                    id, content, java.sql.Date.valueOf(dueDate), title, weight, progress, parentId
            );
        }
    }
}
