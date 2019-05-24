package model.objective;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Objective;
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
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Objective build(String content, Date dueDate, ObjectId creator, ObjectId patient){
        Objective obj = new Objective(dueDate,creator,patient,content,false);
        transaction.getObjectiveRepo().set(obj);
        transaction.commit();
        return obj;
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
