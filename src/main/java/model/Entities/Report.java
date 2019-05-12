package model.Entities;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Report implements IEntity {
    private ObjectId id;
    public ObjectId getId() {
        return id;
    }
    private String content;
    private ObjectId dossierId;

    public Report(String content, ObjectId dossierId) {
        this.content = content;
        this.dossierId = dossierId;
    }

    public Report(){

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
}
