package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Entities.*;
import model.MongoHelpers.MongoAttributes;
import model.MongoHelpers.MongoCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class UnitOfWork {
    public UnitOfWork(){
        MongoDatabase dbContext = DbConnector.getDatabase();
        initCaches();

        //init Collections
        doctorCollection = dbContext.getCollection(MongoCollections.Doctor, Doctor.class);
        patientCollection = dbContext.getCollection(MongoCollections.Patient, Patient.class);
        dossierCollection = dbContext.getCollection(MongoCollections.Dossier, Dossier.class);
        reportCollection = dbContext.getCollection(MongoCollections.Report, Report.class);

        //init repos
        doctorRepo  = new ModelRepository<>(doctorCollection,doctorCache);
        patientRepo = new ModelRepository<>(patientCollection, patientCache);
        dossierRepo = new ModelRepository<>(dossierCollection, dossierCache);
        reportRepo  = new ModelRepository<>(reportCollection, reportCache);
    }

    //Caches to track changes in memory until commit
    private Map<Operation, List<Doctor>> doctorCache;
    private Map<Operation, List<Patient>> patientCache;
    private Map<Operation, List<Dossier>> dossierCache;
    private Map<Operation, List<Report>> reportCache;

    //Mongo Collections
    private MongoCollection<Doctor> doctorCollection;
    private MongoCollection<Patient> patientCollection;
    private MongoCollection<Dossier> dossierCollection;
    private MongoCollection<Report> reportCollection;

    //private Repositories
    private ModelRepository<Doctor> doctorRepo;
    private ModelRepository<Patient> patientRepo;
    private ModelRepository<Dossier> dossierRepo;
    private ModelRepository<Report> reportRepo;

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
        List<T> list = cache.get(Operation.INSERT);
        if(list.size() > 0) {
            collection.insertMany(list);
        }
        
        cache.get(Operation.UPDATE)
                .forEach(x -> collection.updateOne(eq(MongoAttributes.IdAttribute, x.getId()),eq(MongoAttributes.IdAttribute, x.getId())));

        cache.get(Operation.DELETE)
                .forEach(x -> collection.deleteOne(eq(MongoAttributes.IdAttribute, x.getId())));
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
        unitOfWork.getDoctorRepo().setAll(doctors);
        //unitOfWork.patientRepo.setAll(patients);

        unitOfWork.commit();
    }
}
