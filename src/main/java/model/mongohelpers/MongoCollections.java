package model.mongohelpers;

/**
 * MongoDb Collections are always given as strings eg. for the eq filter
 * To reduce typos and bugs this class represents the Mongo Collections
 * @author gian.demarmels@students.bfh.ch
 */
public class MongoCollections {
    public final static String DOCTOR = "doctor";
    public final static String PATIENT = "patient";
    public final static String DOSSIER = "dossier";
    public final static String REPORT = "report";
    public final static String OBJECTIVE = "objective";
    public final static String MESSAGE = "message";

}
