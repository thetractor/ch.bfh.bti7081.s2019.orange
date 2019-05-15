package model.dossiers;

import model.entitiess.Dossier;
import model.entitiess.Report;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class DossierQuerier extends Querier<Dossier> {
    @Override
    public List<Dossier> getAll() {
        return transaction.getDossierRepo().getAll();
    }

    @Override
    public Dossier get(ObjectId id) {
        return transaction.getDossierRepo().get(id);
    }

    public List<Report> getReports(ObjectId id){
        return transaction.getReportRepo()
                .getAll()
                .stream()
                .filter(x -> x.getDossierId() == id)
                .collect(Collectors.toList());
    }
}
