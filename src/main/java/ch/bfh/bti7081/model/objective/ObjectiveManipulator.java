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

    public void delete(ObjectId entity) {
        Objective obj = transaction.getObjectiveRepo().get(entity);
        transaction.getObjectiveRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Objective> lst = entities.stream().map(x -> transaction.getObjectiveRepo().get(x)).collect(Collectors.toList());
        transaction.getObjectiveRepo().deleteMany(lst);
        transaction.commit();
    }
}
