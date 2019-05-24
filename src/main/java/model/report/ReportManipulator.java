package model.report;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Patient;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class for manipulating models
 * Methods for creating, updating & deleting belong in this class
 * Other classes can be as this example
 * @author gian.demarmels@students.bfh.ch
 */
public class ReportManipulator {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public Report build(String content, ObjectId dossier){
        Report obj = new Report(content, dossier);
        transaction.getReportRepo().set(obj);
        transaction.commit();
        return obj;
    }

    public void delete(ObjectId entity) {
        Report obj = transaction.getReportRepo().get(entity);
        transaction.getReportRepo().delete(obj);
        transaction.commit();
    }

    public void deleteMany(List<ObjectId> entities) {
        List<Report> lst = entities.stream().map(x -> transaction.getReportRepo().get(x)).collect(Collectors.toList());
        transaction.getReportRepo().deleteMany(lst);
        transaction.commit();
    }
}
