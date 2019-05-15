package model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.mongohelperss.MongoAttributes;
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

    public void delete(T object){
        addToCache(object, Operation.DELETE);
    }

    public void update(T object){
        addToCache(object, Operation.UPDATE);
    }

    public void updateMany(List<T> objects){
        addManyToCache(objects, Operation.UPDATE);
    }

    public T get(ObjectId id){
         return collection.find(eq(MongoAttributes.IdAttribute, id)).first();
    }

    public void setAll(List<T> modelList){
        addManyToCache(modelList, Operation.INSERT);
    }

    private void addToCache(T object, Operation op){
        List<T> list = operationCache.get(op);
        list.add(object);
        operationCache.replace(op, list);
    }

    private void addManyToCache(List<T> objects, Operation op){
        List<T> list = operationCache.get(op);
        list.addAll(objects);
        operationCache.replace(op, list);
    }
}
