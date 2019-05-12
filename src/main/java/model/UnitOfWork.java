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

import static com.mongodb.client.model.Filters.eq;

public class UnitOfWork {
    public UnitOfWork(MongoDatabase dbContext){
        initCaches();

        //init Collections
        doctorCollection = dbContext.getCollection(MongoCollections.Doctor, Doctor.class);
        patientCollection = dbContext.getCollection(MongoCollections.Patient, Patient.class);
        dossierCollection = dbContext.getCollection(MongoCollections.Dossier, Dossier.class);
        reportCollection = dbContext.getCollection(MongoCollections.Report, Report.class);
        objectiveCollection = dbContext.getCollection(MongoCollections.Objective, Objective.class);
        messageCollection = dbContext.getCollection(MongoCollections.Message, Message.class);

        //init repos
        doctorRepo  = new ModelRepository<>(doctorCollection,doctorCache);
        patientRepo = new ModelRepository<>(patientCollection, patientCache);
        dossierRepo = new ModelRepository<>(dossierCollection, dossierCache);
        reportRepo  = new ModelRepository<>(reportCollection, reportCache);
        objectiveRepo  = new ModelRepository<>(objectiveCollection, objectiveCache);
        messageRepo  = new ModelRepository<>(messageCollection, messageCache);
    }

    //Caches to track changes in memory until commit
    private Map<Operation, List<Doctor>> doctorCache;
    private Map<Operation, List<Patient>> patientCache;
    private Map<Operation, List<Dossier>> dossierCache;
    private Map<Operation, List<Report>> reportCache;
    private Map<Operation, List<Objective>> objectiveCache;
    private Map<Operation, List<Message>> messageCache;

    //Mongo Collections
    private MongoCollection<Doctor> doctorCollection;
    private MongoCollection<Patient> patientCollection;
    private MongoCollection<Dossier> dossierCollection;
    private MongoCollection<Report> reportCollection;
    private MongoCollection<Objective> objectiveCollection;
    private MongoCollection<Message> messageCollection;

    //private Repositories
    private ModelRepository<Doctor> doctorRepo;
    private ModelRepository<Patient> patientRepo;
    private ModelRepository<Dossier> dossierRepo;
    private ModelRepository<Report> reportRepo;
    private ModelRepository<Objective> objectiveRepo;
    private ModelRepository<Message> messageRepo;

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
    public ModelRepository<Objective> getObjectiveRepo() {
        return objectiveRepo;
    }
    public ModelRepository<Message> getMessageRepo() {
        return messageRepo;
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

    //persist changes on caches
    void commit(){
        commitEntity(doctorCollection, doctorCache);
        commitEntity(dossierCollection, dossierCache);
        commitEntity(reportCollection, reportCache);
        commitEntity(patientCollection, patientCache);
        commitEntity(objectiveCollection, objectiveCache);
        commitEntity(messageCollection, messageCache);

        initCaches();
    }

    //call to reinit and init the chaches
    private void initCaches(){
        doctorCache = initializeMap();
        dossierCache = initializeMap();
        reportCache = initializeMap();
        patientCache = initializeMap();
        objectiveCache = initializeMap();
        messageCache = initializeMap();
    }

    //generic methods for persisting specific collections
    private static <T extends IEntity> void  commitEntity(MongoCollection<T> collection, Map<Operation, List<T>> cache){
        //insertmany doesnt accept empty list
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
        UnitOfWork unitOfWork = new UnitOfWork(DbConnector.getDatabase());

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Albert", "Amman",new ArrayList<>() ));
        doctors.add(new Doctor("Peter", "Petersen", new ArrayList<>()));
        doctors.add(new Doctor("Hans", "Horst", new ArrayList<>()));

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
