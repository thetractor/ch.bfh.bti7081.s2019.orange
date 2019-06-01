package model.entities;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

/**
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Objective implements IEntity {

    private ObjectId id;
    private ObjectId patientId;
    private String title;
    private String content;
    private ObjectId parent;
    private double weight;
    private double progress;
    private Date dueDate;
    private ObjectId creatorId;

    public Objective(
            Date dueDate,
            ObjectId creatorId,
            ObjectId patientId,
            String content,
            String title,
            double weight,
            double progress,
            ObjectId parent
    ) {
        this.dueDate = dueDate;
        this.creatorId = creatorId;
        this.patientId = patientId;
        this.content = content;
        this.title = title;
        this.weight = weight;
        this.progress = progress;
        this.parent = parent;
    }

    public Objective(){

    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ObjectId getParent() {
        return parent;
    }

    public void setParent(ObjectId parent) {
        this.parent = parent;
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
        return Objects.equals(dueDate, objective.dueDate) &&
                creatorId.equals(objective.creatorId) &&
                content.equals(objective.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, creatorId, content);
    }
}
