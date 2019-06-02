package ch.bfh.bti7081.model.objective;

import ch.bfh.bti7081.model.entities.Objective;

import java.util.List;

public class ProgressCalculator {

    public static double calculateProgress(List<Objective> objectives){
        double totalWeight = objectives.stream().mapToDouble(Objective::getWeight).sum();
        double currentWeightDone = objectives.parallelStream().mapToDouble(x -> (x.getProgress()/100) * x.getWeight()).sum();
        return (100/totalWeight) * currentWeightDone;
    }
}
