package ch.bfh.bti7081.model.entities;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

/**
 * Implementation of the Message entity
 * @author gian.demarmels@students.bf.ch
 * @author yannis.schmutz@students.bf.ch
 */
public class Message implements IEntity {

    private ObjectId id;
    private String content;
    private ObjectId fromDoctorId;
    private ObjectId reportId;
    private Date sentDate;

    public Message(String content, ObjectId fromDoctorId, ObjectId reportId, Date sentDate) {
        this.content = content;
        this.fromDoctorId = fromDoctorId;
        this.reportId = reportId;
        this.sentDate = new Date(sentDate.getTime());
    }

    /**
     * Empty constructor is needed in order to create an object out of the database
     */
    public Message(){

    }

    /**
     * Returns the ID of the report the message was written on
     * @return ObjectId
     */
    public ObjectId getReportId() {
        return reportId;
    }

    public void setReportId(ObjectId reportId) {
        this.reportId = reportId;
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
     * Gets the content of the message
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the doctorId of the author of the message
     * @return ObjectId
     */
    public ObjectId getFromDoctorId() {
        return fromDoctorId;
    }

    /**
     * Sets the author of the message
     * @param fromDoctorId ObjectId
     */
    public void setFromDoctorId(ObjectId fromDoctorId) {
        this.fromDoctorId = fromDoctorId;
    }

    /**
     * Get the date the message was sent
     * @return Date
     */
    public Date getSentDate(){
        return new Date(sentDate.getTime());
    }

    /**
     * Sets the date the message was sent
     * @param sentDate Date
     */
    public void setSentDate(Date sentDate){
        this.sentDate = new Date(sentDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(content, message.content) &&
                Objects.equals(fromDoctorId, message.fromDoctorId) &&
                Objects.equals(reportId, message.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, fromDoctorId, reportId);
    }

    @Override
    public String toString(){
        return String.format("Message('%s', doc=%s, report=%s", content, fromDoctorId, reportId);
    }
}
