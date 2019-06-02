package ch.bfh.bti7081.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 *
 * Provides access to the database.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 * @author gian.demarmelsz@students.bfh.ch
 */
public class DbConnector {
    private final static String SERVER = "localhost";   // Running the db locally is just fine for now
                                                        // As well as using the default port (27017)
    private final static String DATABASE_NAME = "test"; // Use built-in database 'test'

    /**
     *
     * @return  MongoDatabase object
     */
    public static MongoDatabase getDatabase(){
        // Configure the CodecRegistry to include a codecs to handle the translation to and from BSON (binary-JSON)
        // for your POJOs (PlainOldJavaObjects).
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // Define a client for the mongoDB connection, using default address and port.
        MongoClient mongoClient = new MongoClient(SERVER, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        // Use built in database
        return mongoClient.getDatabase(DATABASE_NAME);
    }



}
