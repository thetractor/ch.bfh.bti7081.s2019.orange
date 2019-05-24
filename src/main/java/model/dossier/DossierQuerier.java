package model.dossier;

import model.DbConnector;
import model.UnitOfWork;
import model.entities.Dossier;
import model.entities.Report;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API to Query dossier related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class DossierQuerier{
    //ToDo dependency injection for UnitOfWork
    private UnitOfWork transaction = new UnitOfWork(DbConnector.getDatabase());

    public List<Dossier> getAll() {
        return transaction.getDossierRepo().getAll();
    }

    public Dossier get(ObjectId id) {
        return transaction.getDossierRepo().get(id);
    }

    public List<Report> getReports(ObjectId id){
        return transaction.getReportRepo()
                .getAll()
                .stream()
                .filter(x -> x.getDossierId().equals(id))
                .collect(Collectors.toList());
    }
}
