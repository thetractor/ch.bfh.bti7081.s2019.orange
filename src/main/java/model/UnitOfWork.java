package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Entities.Doctor;
import model.Entities.Dossier;
import model.Entities.Patient;
import model.Entities.Report;
import model.MongoHelpers.MongoAttributes;
import model.MongoHelpers.MongoCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class UnitOfWork {
    private MongoDatabase dbContext = DbConnector.getDatabase();

    public UnitOfWork(){
        initCaches();
    }

    //Caches to track changes in memory until commit
    private Map<Operation, List<Doctor>> doctorCache;
    private Map<Operation, List<Patient>> patientCache;
    private Map<Operation, List<Dossier>> dossierCache;
    private Map<Operation, List<Report>> reportCache;

    //Mongo Collections
    private MongoCollection<Doctor> doctorCollection = dbContext.getCollection(MongoCollections.Doctor, Doctor.class);
    private MongoCollection<Patient> patientCollection = dbContext.getCollection(MongoCollections.Patient, Patient.class);
    private MongoCollection<Dossier> dossierCollection = dbContext.getCollection(MongoCollections.Dossier, Dossier.class);
    private MongoCollection<Report> reportCollection = dbContext.getCollection(MongoCollections.Report, Report.class);

    //private Repositories
    private ModelRepository<Doctor> doctorRepo = new ModelRepository<>(doctorCollection,doctorCache);
    private ModelRepository<Patient> patientRepo = new ModelRepository<>(patientCollection, patientCache);
    private ModelRepository<Dossier> dossierRepo = new ModelRepository<>(dossierCollection, dossierCache);
    private ModelRepository<Report> reportRepo = new ModelRepository<>(reportCollection, reportCache);

    //getters for repositories
    public ModelRepository<Doctor> getDoctorRepo() {
        return doctorRepo;
    }

    public ModelRepository<Patient> getPatientRepo() {
        return patientRepo;
    }

    public ModelRepository<Dossier> getDossierRepo() {
        return dossierRepo;
    }

    public ModelRepository<Report> getReportRepo() {
        return reportRepo;
    }

    // generic method to initialize maps for caching
    private static <T extends IEntity> Map<Operation, List<T>> initializeMap(){
        // To make sure every Map has an empty ArrayList in it (prevent null-pointer-exception)
        Map<Operation, List<T>> map = new HashMap<>();
        map.put(Operation.INSERT, new ArrayList<>());
        map.put(Operation.UPDATE, new ArrayList<>());
        map.put(Operation.DELETE, new ArrayList<>());
        return map;
    }

    //persist changes on chaches
    public void commit(){
        commitEntity(doctorCollection, doctorCache);
        commitEntity(dossierCollection, dossierCache);
        commitEntity(reportCollection, reportCache);
        commitEntity(patientCollection, patientCache);
        initCaches();
    }

    //call to reinit and init the chaches
    private void initCaches(){
        doctorCache = initializeMap();
        dossierCache = initializeMap();
        reportCache = initializeMap();
        patientCache = initializeMap();
    }

    //generic methods for persisting specific collections
    private static <T extends IEntity> void  commitEntity(MongoCollection<T> collection, Map<Operation, List<T>> cache){
        if(cache.get(Operation.INSERT).size() > 0) {
            collection.insertMany(cache.get(Operation.INSERT));
        }

        for (T entity : cache.get(Operation.DELETE)){
            collection.deleteOne(eq(MongoAttributes.IdAttribute, entity.getId()));
        }

        for (T entity : cache.get(Operation.UPDATE)){
            collection.updateOne(eq(MongoAttributes.IdAttribute, entity.getId()),eq(MongoAttributes.IdAttribute, entity.getId()));
        }
    }

    public static void main(String[] args){
        UnitOfWork unitOfWork = new UnitOfWork();

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Albert", "Amman"));
        doctors.add(new Doctor("Peter", "Petersen"));
        doctors.add(new Doctor("Hans", "Horst"));

        /*
        List<Patient> patients = new ArrayList<Patient>();
        patients.add(new Patient("Robert", "Pfeiffer"));
        patients.add(new Patient("Stefan", "Precht"));
        */
        unitOfWork.doctorRepo.setAll(doctors);
        //unitOfWork.patientRepo.setAll(patients);

        unitOfWork.commit();
    }
}
