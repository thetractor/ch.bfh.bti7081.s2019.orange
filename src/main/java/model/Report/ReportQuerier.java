package model.Report;

import model.Entities.Report;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

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
