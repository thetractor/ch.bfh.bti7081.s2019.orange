package model;

import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.message.MessageManipulator;
import ch.bfh.bti7081.model.message.MessageQuerier;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MessageQuerierTest {

    private MessageManipulator manipulator;
    private MessageQuerier querier;
    private String content = "UnitTestContent";
    private ObjectId doc = ObjectId.get();
    private ObjectId report = ObjectId.get();


    @BeforeEach
    /**
     * Cerate necessary Manipulator and Querier
     */
    void setUp() {
        manipulator = new MessageManipulator();
        querier = new MessageQuerier();
    }

    @AfterEach
    /**
     * Delete Messages created during testing.
     */
    void tearDown() {
        for (Message m : querier.getByReportId(report)) {
            manipulator.delete(m.getId());
        }
    }

    @Test
    void getAll() {
        manipulator.build(content, doc, report, new Date());
        if(!querier.getAll().
                stream().
                anyMatch(x -> x.getContent().equals(content) &&
                        x.getReportId().equals(report) &&
                        x.getFromDoctorId().equals(doc)
                )){
            Assert.fail();
        }
    }

    @Test
    void get() {
        Message message = manipulator.build(content, doc, report, new Date());
        if(querier.get(message.getId()).equals(null)){
            Assert.fail();
        }
    }

    @Test
    void getByReportId() {
        manipulator.build(content, doc, report, new Date());
        if(querier.getByReportId(report).equals(null)){
            Assert.fail();
        }
    }
}