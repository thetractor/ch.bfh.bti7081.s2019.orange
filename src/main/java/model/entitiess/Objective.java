package model.entitiess;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class Objective implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }

    public Objective(Date dueDate, ObjectId creatorId, ObjectId patientId, String content, boolean finished) {
        this.dueDate = dueDate;
        this.creatorId = creatorId;
        this.patientId = patientId;
        this.content = content;
        this.finished = finished;
    }

    public Objective(){

    }

    private ObjectId patientId;
    private String content;
    private boolean finished;
    private Date dueDate;
    private ObjectId creatorId;

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public ObjectId getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(ObjectId creatorId) {
        this.creatorId = creatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ObjectId getPatientId() {
        return patientId;
    }

    public void setPatientId(ObjectId patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objective objective = (Objective) o;
        return finished == objective.finished &&
                Objects.equals(dueDate, objective.dueDate) &&
                creatorId.equals(objective.creatorId) &&
                content.equals(objective.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, creatorId, content, finished);
    }
}
