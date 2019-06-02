package ch.bfh.bti7081.model.report;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query report related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class ReportQuerier {
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Report> getAll() {
        return transaction.getReportRepo().getAll();
    }

    public Report get(ObjectId id) {
        return transaction.getReportRepo().get(id);
    }
}
