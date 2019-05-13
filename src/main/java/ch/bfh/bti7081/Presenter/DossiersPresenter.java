package ch.bfh.bti7081.Presenter;

import com.vaadin.flow.component.notification.Notification;
import model.Dossier.DossierQuerier;
import model.Entities.Dossier;
import model.Entities.Patient;
import model.Patient.PatientQuerier;

import java.util.List;

public class DossiersPresenter {

    private PatientQuerier patientQuerier;

    public DossiersPresenter(){
        patientQuerier = new PatientQuerier();
    }

    public List<Patient> GetDossiersByName(String name) {
        return patientQuerier.getAll();
        //ToDo: implement filtering
    }

    public void ViewDossier(Patient patient){
        Notification.show(patient.toString() + " wurde ausgewählt.");
        //@ToDo: Implement
    }
}
