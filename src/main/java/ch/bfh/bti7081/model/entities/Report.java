package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
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

    public Report(){

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getDossierId() {
        return dossierId;
    }

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
