package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Implementation of the Report entity
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Report implements IEntity {

    private ObjectId id;
    private String content;
    private ObjectId dossierId;

    public Report(String content, ObjectId dossierId) {
        this.content = content;
        this.dossierId = dossierId;
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Report(){

    }

    /**
     * Returns the entities Id
     * @return ObjectId
     */
    public ObjectId getId() {
        return id;
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
     * Returns the content of the report
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the report
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the id of the dossier the report is assigned to
     * @return ObjectId
     */
    public ObjectId getDossierId() {
        return dossierId;
    }

    /**
     * Sets the dossier the report is assigned to
     * @param dossierId ObjectID
     */
    public void setDossierId(ObjectId dossierId) {
        this.dossierId = dossierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(content, report.content) &&
                Objects.equals(dossierId, report.dossierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, dossierId);
    }

    @Override
    public String toString(){
        return String.format("Report(id=%s, dossierID=%s)", id, dossierId);
    }
}
