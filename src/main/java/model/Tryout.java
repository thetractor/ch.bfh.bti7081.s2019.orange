package model;

import model.doctor.DoctorQuerier;
import model.dossier.DossierQuerier;
import model.entities.Doctor;
import model.entities.Dossier;
import model.entities.Patient;
import model.entities.Report;
import model.patient.PatientQuerier;

import java.util.List;

public class Tryout {

    public static void main(String[] args){
        DoctorQuerier doctorQuerier = new DoctorQuerier();
        PatientQuerier patientQuerier = new PatientQuerier();
        DossierQuerier dossierQuerier = new DossierQuerier();


        List<Doctor> doctors = doctorQuerier.getAll();
        System.out.println(doctors);

        Doctor albert = doctors.get(0);

        List<Patient> patients = doctorQuerier.getPatients(albert.getId());
        System.out.println(patients);

        Patient stefan = patients.get(0);

        Dossier stefansDossier = patientQuerier.getDossier(stefan.getId());
        System.out.println(stefansDossier);

        List<Report> reports = dossierQuerier.getReports(stefansDossier.getId());
        System.out.println(reports);


    }
}
