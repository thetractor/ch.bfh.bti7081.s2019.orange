package model.Dossier;

import model.Entities.Doctor;
import model.Entities.Dossier;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

public class DossierQuerier extends Querier<Dossier> {
    @Override
    public List<Dossier> getAll() {
        return transaction.getDossierRepo().getAll();
    }

    @Override
    public Dossier get(ObjectId id) {
        return transaction.getDossierRepo().get(id);
    }
}
