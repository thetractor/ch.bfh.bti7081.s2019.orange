package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

/**
 * Implementation of the Objective entity
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
        this.dueDate = new Date(dueDate.getTime());
        this.creatorId = creatorId;
        this.patientId = patientId;
        this.content = content;
        this.title = title;
        this.weight = weight;
        this.progress = progress;
        this.parent = parent;
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Objective(){

    }

    /**
     * Sets the entities ID
     * This method is needed in order to create an object out of the database, it has to be public to do that.
     *
     * TODO: Encapsulate this so the method is not publicly accessible
     *
     * @param id ObjectId
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Returns the entities Id
     * @return ObjectId
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Returns the due Date
     * @return Date
     */
    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    /**
     * sets due date
     * @param dueDate Date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = new Date(dueDate.getTime());
    }

    /**
     * gets creator id
     * @return ObjectId
     */
    public ObjectId getCreatorId() {
        return creatorId;
    }

    /**
     * sets creator id
     * @param creatorId ObjectId
     */
    public void setCreatorId(ObjectId creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * get objective content
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * sets the content
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * gets the objectives title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the objectives title
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns progress
     * @return double
     */
    public double getProgress() {
        return progress;
    }

    /**
     * sets the progress
     * @param progress double
     */
    public void setProgress(double progress) {
        this.progress = progress;
    }

    /**
     * gets the weight
     * @return double
     */
    public double getWeight() {
        return weight;
    }

    /**
     * seth the weight
     * @param weight double
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * returns the parent of the objective
     * @return ObjectId
     */
    public ObjectId getParent() {
        return parent;
    }

    /**
     * sets the parent of the objective
     * @param parent ObjectId
     */
    public void setParent(ObjectId parent) {
        this.parent = parent;
    }

    /**
     * Get the id of the patient the objective is assigned to
     * @return ObjectiveId
     */
    public ObjectId getPatientId() {
        return patientId;
    }

    /**
     * sets the patients id
     * @param patientId ObjectId
     */
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
