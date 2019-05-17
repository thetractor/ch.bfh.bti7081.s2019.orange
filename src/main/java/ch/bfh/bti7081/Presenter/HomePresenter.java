package ch.bfh.bti7081.Presenter;

import model.doctor.DoctorQuerier;
import model.entities.Doctor;
import java.util.List;

/**
 * Implementation of the <code>HomePresenter</code> class.
 * For further information please refer to the authors.
 *
 * @author kevin.riesen@students.bfh.ch
 */
public class HomePresenter {

    private DoctorQuerier doctorQuerier;

    public HomePresenter(){
        doctorQuerier = new DoctorQuerier();
    }

    /**
     * Get all doctors
     *
     * @return list of all doctors
     */
    public List<Doctor> getDoctors() {
        return doctorQuerier.getAll();
    }
}
