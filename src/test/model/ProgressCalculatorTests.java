package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.bfh.bti7081.model.entities.Objective;
import ch.bfh.bti7081.model.objective.ProgressCalculator;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressCalculatorTests {



    @Test
    void calculateProgressTest(){
        double result = 75;
        List<Objective> objectives = new ArrayList<>();
        objectives.add(new Objective (new Date(), ObjectId.get(), ObjectId.get(), "", "", 2, 50, ObjectId.get()));
        objectives.add(new Objective (new Date(), ObjectId.get(), ObjectId.get(), "", "", 3, 100, ObjectId.get()));
        objectives.add(new Objective (new Date(), ObjectId.get(), ObjectId.get(), "", "", 1 , 50, ObjectId.get()));
        Assert.assertEquals(result, ProgressCalculator.calculateProgress(objectives), 0.5);

    }
}
