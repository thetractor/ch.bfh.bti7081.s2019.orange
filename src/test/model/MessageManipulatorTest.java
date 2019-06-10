package model;

import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.message.MessageManipulator;
import ch.bfh.bti7081.model.message.MessageQuerier;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageManipulatorTest {

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
    void build() {
        Message message = manipulator.build(content, doc, report, new Date());
        if (!(message.getContent() == content &&
            message.getFromDoctorId().equals(doc) &&
            message.getReportId().equals(report)
        )){
            Assert.fail();
        }
    }

    @Test
    void delete() {
        Message message = manipulator.build(content, doc, report, new Date());
        manipulator.delete(message.getId());
        if(!querier.getByReportId(report).isEmpty()){
            Assert.fail();
        }
    }

    @Test
    void deleteMany() {
        Message message = manipulator.build(content, doc, report, new Date());
        List<ObjectId> entities = new ArrayList<>();
        entities.add(message.getId());
        manipulator.deleteMany(entities);
        if(!querier.getByReportId(report).isEmpty()){
            Assert.fail();
        }
    }
}