package model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.mongohelpers.MongoAttributes;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Generic Repository with general functionality for all repos
 * @author gian.demarmels@students.bfh.ch
 * @author yannis.schmutz@students.bfh.ch
 */
public class ModelRepository <T>{

    //mongo collection holds reference to the collection in the database
    private MongoCollection<T> collection;

    //in memory cache to track changes
    private Map<Operation, List<T>> operationCache;

    public ModelRepository(MongoCollection<T> collection, Map<Operation, List<T>> operationCache){
        this.collection = collection;
        this.operationCache = operationCache;
    }

    /**
     * returns the whole collection of type T as list
     * @return List of type T
     */
    public List<T> getAll(){
        final FindIterable<T> models = collection.find();
        List<T> modelList = new ArrayList<>();
        for(T t : models){
            modelList.add(t);
        }
        return modelList;
    }

    /**
     * Returns a specific entity
     * @param  id from object
     * @return specific Object
     */
    public T get(ObjectId id){
        return collection.find(eq(MongoAttributes.ID_ATTRIBUTE, id)).first();
    }

    /**
     * deletes a specific entity
     * @param object of T
     */
    public void delete(T object){
        addToCache(object, Operation.DELETE);
    }

    /**
     * updates a specific entity
     * @param object of T
     */
    public void update(T object){
        addToCache(object, Operation.UPDATE);
    }

    /**
     * update many entities
     * @param objects of T
     */
    public void updateMany(List<T> objects){
        addManyToCache(objects, Operation.UPDATE);
    }

    /**
     * set many entites
     * @param object of T
     */
    public void set(T object){
        addToCache(object, Operation.INSERT);
    }

    /**
     * set many entites
     * @param modelList of T
     */
    public void setAll(List<T> modelList){
        addManyToCache(modelList, Operation.INSERT);
    }

    // generalized method to add something to the cache for some operation
    private void addToCache(T object, Operation op){
        List<T> list = operationCache.get(op);
        list.add(object);
        operationCache.replace(op, list);
    }

    // generalized method to add a list of something to the cache for some operation
    private void addManyToCache(List<T> objects, Operation op){
        List<T> list = operationCache.get(op);
        list.addAll(objects);
        operationCache.replace(op, list);
    }
}
