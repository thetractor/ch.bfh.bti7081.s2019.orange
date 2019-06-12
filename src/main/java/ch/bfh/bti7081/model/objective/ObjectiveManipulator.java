package ch.bfh.bti7081.model.objective;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Objective;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for manipulating models
 * Methods for creating, updating & deleting belong in this class
 * Other classes can be as this example
 * @author gian.demarmels@students.bfh.ch
 */
public class ObjectiveManipulator{
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    /**
     * Creates a objective object, saves it to the DB and returns the object.
     * @param content String
     * @param dueDate Date
     * @param creator ObjectId
     * @param patient ObjectId
     * @param title String
     * @param weight double
     * @param progress double
     * @param parent ObjectId
     * @return Objective object
     */
    public Objective build(
            String content,
            Date dueDate,
            ObjectId creator,
            ObjectId patient,
            String title,
            double weight,
            double progress,
            ObjectId parent
    ){
        Objective obj = new Objective(dueDate,creator,patient,content, title, weight, progress, parent);
        transaction.getObjectiveRepo().set(obj);
        transaction.commit();
        return obj;
    }

    /**
     * Updates an already existing objective
     * @param entity ObjectId
     * @param content String
     * @param dueDate Date
     * @param title String
     * @param weight double
     * @param progress double
     * @param parent ObjectId
     */
    public void update(
            ObjectId entity,
            String content,
            Date dueDate,
            String title,
            double weight,
            double progress,
            ObjectId parent
    ){
        Objective obj = transaction.getObjectiveRepo().get(entity);
        obj.setTitle(title);
        obj.setContent(content);
        obj.setDueDate(dueDate);
        obj.setWeight(weight);
        obj.setProgress(progress);
        obj.setParent(parent);
        transaction.getObjectiveRepo().update(obj);
        transaction.commit();
    }

    /**
     * Deletes a objective by a given ID
     * @param entity ObjectId
     */
    public void delete(ObjectId entity) {
        Objective obj = transaction.getObjectiveRepo().get(entity);
        transaction.getObjectiveRepo().delete(obj);
        transaction.commit();
    }

    /**
     * Deletes multiple objectives
     * @param entities List of objective-IDs
     */
    public void deleteMany(List<ObjectId> entities) {
        List<Objective> lst = entities.stream().map(x -> transaction.getObjectiveRepo().get(x)).collect(Collectors.toList());
        transaction.getObjectiveRepo().deleteMany(lst);
        transaction.commit();
    }
}
