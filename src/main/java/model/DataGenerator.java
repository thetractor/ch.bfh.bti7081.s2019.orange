package model;

import model.dossier.DossierQuerier;
import model.entities.*;
import model.patient.PatientQuerier;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Used to generate dummy data.
 * This class is only used to provide a greenfield for all developer, so they all can work with the same data.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
class DataGenerator {

    private static UnitOfWork unitOfWork = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Purges all content of the used database to provide a greenfield.
     */
    private static void purgeAll(){
        // Delete all doctors in DB
        System.out.println("[*] Going to delete all doctors in db...");
        DataGenerator.unitOfWork.getDoctorRepo().getAll().forEach(x -> unitOfWork.getDoctorRepo().delete(x));
        System.out.println("[+] Doctors have been deleted");

        System.out.println("[*] Going to delete all patients in db...");
        DataGenerator.unitOfWork.getPatientRepo().getAll().forEach(x -> unitOfWork.getPatientRepo().delete(x));
        System.out.println("[+] Patients have been deleted");

        System.out.println("[*] Going to delete all dossiers in db...");
        DataGenerator.unitOfWork.getDossierRepo().getAll().forEach(x -> unitOfWork.getDossierRepo().delete(x));
        System.out.println("[+] Dossiers have been deleted");

        System.out.println("[*] Going to delete all reports in db...");
        DataGenerator.unitOfWork.getReportRepo().getAll().forEach(x -> unitOfWork.getReportRepo().delete(x));
        System.out.println("[+] Reports have been deleted");

        System.out.println("[*] Going to delete all messages in db...");
        DataGenerator.unitOfWork.getMessageRepo().getAll().forEach(x -> unitOfWork.getMessageRepo().delete(x));
        System.out.println("[+] Messages have been deleted");

        // Make changes valid
        unitOfWork.commit();
    }


    /**
     * Generates doctor dummy data
     */
    private static void generateDoctors(){
        System.out.println("[*] Going to create all doctors...");
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Albert", "Amman",new ArrayList<>() ));
        doctors.add(new Doctor("Peter", "Petersen", new ArrayList<>()));
        doctors.add(new Doctor("Hans", "Horst", new ArrayList<>()));

        unitOfWork.getDoctorRepo().setAll(doctors);

        unitOfWork.commit();
        System.out.println("[+] Doctors have been committed");
    }

    /**
     * Generates patient dummy data as well as the corresponding dossiers
     */
    private static void generatePatients(){

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Robert", "Pfeiffer"));
        patients.add(new Patient("Stefan", "Precht"));
        patients.add(new Patient("Alfred", "Ploch"));
        patients.add(new Patient("Kevin", "Riesen"));
        patients.add(new Patient("Gian", "Demarmels"));
        patients.add(new Patient("Yannis", "Schmutz"));
        patients.add(new Patient("Lars", "Peyer"));
        patients.add(new Patient("Adrian", "Berger"));
        patients.add(new Patient("Matthias", "Ossola"));

        unitOfWork.getPatientRepo().setAll(patients);
        unitOfWork.commit();

        List<Dossier> dossiers = new ArrayList<>();
        unitOfWork.getPatientRepo().getAll().forEach(x -> {
          Dossier dossier = new Dossier();
          dossier.setId(ObjectId.get());
          dossier.setPatientId(x.getId());
          dossiers.add(dossier);
          x.setDossierId(dossier.getId());
          unitOfWork.getPatientRepo().update(x);
        });
        unitOfWork.getDossierRepo().setAll(dossiers);
        unitOfWork.commit();
    }

    /**
     * Assigns the already existing patients to doctors
     */
    private static void assignPatientsToDoctors(){
        List<Patient> patients = unitOfWork.getPatientRepo().getAll();
        List<Doctor> doctors = unitOfWork.getDoctorRepo().getAll();

        ListIterator<Patient> patientListIterator = patients.listIterator();
        Integer doctorListSize = doctors.size();
        while (patientListIterator.hasNext()){
            Patient patient = patientListIterator.next();
            doctors.get(patientListIterator.nextIndex() % doctorListSize).addPatient(patient);
            // Assign the patient to two doctors if more than one doctor is defined
            // This assignment will be relevant in order to notify at least one doctor for a given message
            if(doctorListSize > 1){
                doctors.get((patientListIterator.nextIndex() + 1) % doctorListSize).addPatient(patient);
            }
        }
        unitOfWork.getDoctorRepo().updateMany(doctors);
        unitOfWork.commit();
    }

    /**
     * Generates report dummy data
     */
    private static void generateReports(){
        List<Report> reports = new ArrayList<>();
        unitOfWork.getDossierRepo().getAll().forEach(x ->{
            reports.add(new Report("Some content", x.getId()));
            reports.add(new Report("Some further content", x.getId()));
        });
        unitOfWork.getReportRepo().setAll(reports);
        unitOfWork.commit();
    }

    /**
     * Generates message dummy data
     *
     * for every doctor:
     *     for every one of doctor's patients:
     *         get patient's dossier
     *         for every report in dossier
     *             add one message for this report, written by a specific docter
     */
    private static void generateMessages(){
        List<Message> messages = new ArrayList<>();
        PatientQuerier patientQuerier = new PatientQuerier();
        DossierQuerier dossierQuerier = new DossierQuerier();

        unitOfWork.getDoctorRepo().getAll().forEach(doctor -> {
            doctor.getPatients().forEach(patientID -> {
                dossierQuerier.getReports(patientQuerier.getDossier(patientID).getId()).forEach(report -> {
                    messages.add(new Message("Dummy message", doctor.getId(), report.getId()));
                });
            });
        });

        unitOfWork.getMessageRepo().setAll(messages);
        unitOfWork.commit();
    }

    /**
     *
     * @param args  System arguments, what else?
     */
    public static void main(String[] args){
        /*
        Mongo Daemon has to be started using the "mongod" command in a terminal!
         */

        // Todo: Implement a logger...
        System.out.println("[*] Starting DataGenerator");

        DataGenerator.purgeAll();
        DataGenerator.generateDoctors();
        DataGenerator.generatePatients();
        DataGenerator.assignPatientsToDoctors();
        DataGenerator.generateReports();
        DataGenerator.generateMessages();

        System.out.println("[+] DataGenerator completed");
    }
}
