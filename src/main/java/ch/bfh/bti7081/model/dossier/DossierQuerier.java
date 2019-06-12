package ch.bfh.bti7081.model.dossier;

import ch.bfh.bti7081.model.DbConnector;
import ch.bfh.bti7081.model.UnitOfWork;
import ch.bfh.bti7081.model.entities.Dossier;
import ch.bfh.bti7081.model.entities.Report;
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

    /**
     * Returns all dossiers
     *
     * TODO: Getting all data in memory might be dangerous!
     *
     * @return List of Dossiers
     */
    public List<Dossier> getAll() {
        return transaction.getDossierRepo().getAll();
    }

    /**
     * Get a specific dossier by its ID
     * @param id ObjectId
     * @return Dossier
     */
    public Dossier get(ObjectId id) {
        return transaction.getDossierRepo().get(id);
    }

    /**
     * Returns a maximum of reports of a given dossier, where the maximum is determined by the limit value.
     * @param dossierId ObjectiveId
     * @param limit Integer
     * @return List of reports
     */
    public List<Report> getReports(ObjectId dossierId, Integer limit){
        return transaction.getReportRepo()
                .getAll()
                .stream()
                .filter(x -> x.getDossierId().equals(dossierId))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
