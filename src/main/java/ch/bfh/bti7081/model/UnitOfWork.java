package ch.bfh.bti7081.model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ch.bfh.bti7081.model.entities.*;
import ch.bfh.bti7081.model.mongohelpers.MongoAttributes;
import ch.bfh.bti7081.model.mongohelpers.MongoCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of the <code>UnitOfWork</code> class.
 * For further information please refer to the authors.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 * @author gian.demarmelsz@students.bfh.ch
 */
public class UnitOfWork {

    /**
     * Generic method to initialize maps for caching
     *
     * @param <T>   Generic of type IEntity
     * @return      Map object
     */
    private static <T extends IEntity> Map<Operation, List<T>> initializeMap(){
        // To make sure every Map has an empty ArrayList in it (prevent null-pointer-exception)
        Map<Operation, List<T>> map = new HashMap<>();
        map.put(Operation.INSERT, new ArrayList<>());
        map.put(Operation.UPDATE, new ArrayList<>());
        map.put(Operation.DELETE, new ArrayList<>());
        return map;
    }

    /**
     *
     * generic methods for persisting specific collections
     *
     * @param collection    MongoCollection
     * @param cache         Cache for the given MongoCollection
     * @param <T>           Generic of the type IEntity
     */
    private static <T extends IEntity> void  commitEntity(MongoCollection<T> collection, Map<Operation, List<T>> cache){
        //insertmany doesnt accept empty list
        List<T> list = cache.get(Operation.INSERT);
        if(list.size() > 0) {
            collection.insertMany(list);
        }

        // TODO: Would be cleaner to use the updateOne function.
        cache.get(Operation.UPDATE)
                .forEach(x -> {
                    collection.deleteOne(eq(MongoAttributes.ID_ATTRIBUTE, x.getId()));
                    collection.insertOne(x);
                });

        cache.get(Operation.DELETE)
                .forEach(x -> collection.deleteOne(eq(MongoAttributes.ID_ATTRIBUTE, x.getId())));
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

    public UnitOfWork(MongoDatabase dbContext){
        // Prepare caches in order to prevent NullPointerExceptions!
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

    /**
     * Persist changes on caches
     */
    public void commit(){
        commitEntity(doctorCollection, doctorCache);
        commitEntity(dossierCollection, dossierCache);
        commitEntity(reportCollection, reportCache);
        commitEntity(patientCollection, patientCache);
        commitEntity(objectiveCollection, objectiveCache);
        commitEntity(messageCollection, messageCache);

        clearCaches();
    }

    /**
     * Initializes the caches with an empty map.
     * Has to be called at first in the constructor of <code>UnitOfWork</code>
     */
    private void initCaches(){
        doctorCache = initializeMap();
        dossierCache = initializeMap();
        reportCache = initializeMap();
        patientCache = initializeMap();
        objectiveCache = initializeMap();
        messageCache = initializeMap();
    }

    /**
     * Clears the ArrayList in all caches.
     * Must be called after every commit.
     */
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
}
