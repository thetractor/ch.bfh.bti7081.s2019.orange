package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.entities.*;
import model.mongohelpers.MongoAttributes;
import model.mongohelpers.MongoCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class UnitOfWork {
    /**
     *
     * @param dbContext
     */
    public UnitOfWork(MongoDatabase dbContext){
        //
        initCaches();

        //init Collections
        doctorCollection = dbContext.getCollection(MongoCollections.DOCTOR, Doctor.class);
        patientCollection = dbContext.getCollection(MongoCollections.PATIENT, Patient.class);
        dossierCollection = dbContext.getCollection(MongoCollections.DOSSIER, Dossier.class);
        reportCollection = dbContext.getCollection(MongoCollections.REPORT, Report.class);
        objectiveCollection = dbContext.getCollection(MongoCollections.OBJECTIVE, Objective.class);
        messageCollection = dbContext.getCollection(MongoCollections.MESSAGE, Message.class);

        //init repos
        doctorRepo  = new ModelRepository<>(doctorCollection, doctorCache);
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

        clearCaches();
    }

    /**
     * Initializes the caches with an empty map
     */
    private void initCaches(){
        doctorCache = initializeMap();
        dossierCache = initializeMap();
        reportCache = initializeMap();
        patientCache = initializeMap();
        objectiveCache = initializeMap();
        messageCache = initializeMap();
    }

    private void clearCaches(){
        for(Operation operation : Operation.values()){
            doctorCache.get(operation).clear();
            dossierCache.get(operation).clear();
            reportCache.get(operation).clear();
            patientCache.get(operation).clear();
            objectiveCache.get(operation).clear();
            messageCache.get(operation).clear();
        }
    }

    //generic methods for persisting specific collections
    private static <T extends IEntity> void  commitEntity(MongoCollection<T> collection, Map<Operation, List<T>> cache){
        //insertmany doesnt accept empty list
        List<T> list = cache.get(Operation.INSERT);
        if(list.size() > 0) {
            collection.insertMany(list);
        }

        // TODO: Would be cleaner to use the updateOne function.
        cache.get(Operation.UPDATE)
                //.forEach(x -> collection.updateOne(eq(MongoAttributes.ID_ATTRIBUTE, x.getId()),eq(MongoAttributes.ID_ATTRIBUTE, x.getId())));
                .forEach(x -> {
                    collection.deleteOne(eq(MongoAttributes.ID_ATTRIBUTE, x.getId()));
                    collection.insertOne(x);
                });

        cache.get(Operation.DELETE)
                .forEach(x -> collection.deleteOne(eq(MongoAttributes.ID_ATTRIBUTE, x.getId())));
    }
}
