package model.report;

import model.entities.Report;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query report related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class ReportQuerier extends Querier<Report> {
    @Override
    public List<Report> getAll() {
        return transaction.getReportRepo().getAll();
    }

    @Override
    public Report get(ObjectId id) {
        return transaction.getReportRepo().get(id);
    }
}
