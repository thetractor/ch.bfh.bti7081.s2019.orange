package model.report;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query report related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class ReportQuerier {
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Report> getAll() {
        return transaction.getReportRepo().getAll();
    }

    public Report get(ObjectId id) {
        return transaction.getReportRepo().get(id);
    }
}
