package ch.bfh.bti7081.Presenter;

import ch.bfh.bti7081.Model.DemoDossier;
import ch.bfh.bti7081.Model.DemoDossierService;

import java.util.List;

public class DossiersPresenter {

    private DemoDossierService dossierService;

    public DossiersPresenter(){
        dossierService = DemoDossierService.getInstance();
    }

    public List<DemoDossier> GetDossiersByName(String name) {
        return dossierService.findAll(name);
    }
}
