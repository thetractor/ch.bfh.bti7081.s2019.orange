package ch.bfh.bti7081.Presenter;

import com.vaadin.flow.component.notification.Notification;
import model.Dossier.DossierQuerier;
import model.Entities.Dossier;
import model.Entities.Patient;
import model.Patient.PatientQuerier;

import java.util.List;
import java.util.stream.Collectors;

public class DossiersPresenter {

    private PatientQuerier patientQuerier;

    public DossiersPresenter(){
        patientQuerier = new PatientQuerier();
    }

    public List<Patient> GetDossiersByName(String name) {

        List<Patient> list = patientQuerier.getAll();
        if (name.isEmpty()) { return list; }
        return list
                .stream()
                .filter(x -> x.getName().startsWith(name) ||x.getSurname().startsWith(name))
                .collect(Collectors.toList());
    }

    public void ViewDossier(Patient patient){
        Notification.show(patient.toString() + " wurde ausgewählt.");
        //@ToDo: Implement
    }
}
