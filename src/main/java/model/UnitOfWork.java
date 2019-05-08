package model;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.util.Pair;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UnitOfWork {

    // TODO: Make DB-Creator class

    // Configure the CodecRegistry to include a codecs to handle the translation to and from BSON (binary-JSON) for your POJOs (PlainOldJavaObjects).
    private CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    // Define a client for the mongoDB connection, using default address and port.
    private MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

    // Use built in database 'test'
    private MongoDatabase testDatabase = mongoClient.getDatabase("test");

    private Map<Operation, List<Doctor>> doctorCache = initializeMap();
    private Map<Operation, List<Patient>> patientCache = initializeMap();
    private Map<Operation, List<Dossier>> dossierCache = initializeMap();
    private Map<Operation, List<Report>> reportCache = initializeMap();

    private MongoCollection<Doctor> doctorCollection = testDatabase.getCollection("doctor", Doctor.class);
    private MongoCollection<Patient> patientCollection = testDatabase.getCollection("patient", Patient.class);
    private MongoCollection<Dossier> dossierCollection = testDatabase.getCollection("dossier", Dossier.class);
    private MongoCollection<Report> reportCollection = testDatabase.getCollection("report", Report.class);

    private ModelRepository<Doctor> doctorRepo = new ModelRepository<Doctor>(doctorCollection,doctorCache);
    private ModelRepository<Patient> patientRepo = new ModelRepository<Patient>(patientCollection, patientCache);
    private ModelRepository<Dossier> dossierRepo = new ModelRepository<Dossier>(dossierCollection, dossierCache);
    private ModelRepository<Report> reportRepo = new ModelRepository<Report>(reportCollection, reportCache);



    public UnitOfWork(){

    }

    public static <T> Map<Operation, List<T>> initializeMap(){
        // To make sure every Map has an empty ArrayList in it (prevent null-pointer-exception)
        Map<Operation, List<T>> map = new HashMap<>();
        map.put(Operation.INSERT, new ArrayList<>());
        map.put(Operation.UPDATE, new ArrayList<>());
        map.put(Operation.DELETE, new ArrayList<>());
        return map;
    }

    public void commit(){
        commitDoctors();
    }

    private void commitDoctors(){
        doctorCollection.insertMany(doctorCache.get(Operation.INSERT));

        for (Doctor doctor : doctorCache.get(Operation.DELETE)){
            doctorCollection.deleteOne(eq("_id", doctor.getId()));
        }

        for (Doctor doctor : doctorCache.get(Operation.UPDATE)){
            doctorCollection.updateOne(eq("_id", doctor.getId()),eq("_id", doctor.getId()));
        }

    }

    public static void main(String[] args){

        UnitOfWork unitOfWork = new UnitOfWork();

        List<Doctor> doctors = new ArrayList<Doctor>();
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
