package model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

class DbConnector {
    private final static String server  = "localhost";
    private final static String databaseName  = "test";

    static MongoDatabase getDatabase(){
        // Configure the CodecRegistry to include a codecs to handle the translation to and from BSON (binary-JSON) for your POJOs (PlainOldJavaObjects).
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // Define a client for the mongoDB connection, using default address and port.
        MongoClient mongoClient = new MongoClient(server, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        // Use built in database 'test'
        return mongoClient.getDatabase(databaseName);
    }



}
