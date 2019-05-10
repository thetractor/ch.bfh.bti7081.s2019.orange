package model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.MongoHelpers.MongoAttributes;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class ModelRepository <T>{

    private MongoCollection<T> collection;
    private Map<Operation, List<T>> operationCache;

    public ModelRepository(MongoCollection<T> collection, Map<Operation, List<T>> operationCache){
        this.collection = collection;
        this.operationCache = operationCache;
    }

    public List<T> getAll(){
        final FindIterable<T> models = collection.find();
        List<T> modelList = new ArrayList<>();
        for(T t : models){
            modelList.add(t);
        }
        return modelList;
    }

    public T get(ObjectId id){
         return collection.find(eq(MongoAttributes.IdAttribute, id)).first();
    }

    public void setAll(List<T> modelList){
        // Just add the model list in-memory, insert after the "commit"
        List<T> list = operationCache.get(Operation.INSERT);
        list.addAll(modelList);
        operationCache.replace(Operation.INSERT, list);
    }

}
