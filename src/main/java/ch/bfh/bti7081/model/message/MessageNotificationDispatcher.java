package ch.bfh.bti7081.model.message;

import com.vaadin.flow.shared.Registration;
import javafx.util.Pair;
import ch.bfh.bti7081.model.doctor.DoctorQuerier;
import ch.bfh.bti7081.model.dossier.DossierQuerier;
import ch.bfh.bti7081.model.entities.Message;
import ch.bfh.bti7081.model.patient.PatientQuerier;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.function.Consumer;

public class MessageNotificationDispatcher {

    static LinkedList<Pair<ObjectId, Consumer<Message>>> listeners = new LinkedList<>();

    /**
     * Registers a doctor and corresponding consumer as a listener for message notifications
     * @param listener
     * @param doctorId
     * @return Registration to unregister the listener
     */
    public static Registration register(Consumer<Message> listener, ObjectId doctorId) {
        Pair<ObjectId, Consumer<Message>> listenerPair = new Pair<>(doctorId, listener);
        listeners.add(listenerPair);

        return () -> {
            synchronized (MessageNotificationDispatcher.class) {
                listeners.remove(listenerPair);
            }
        };

    }

    /**
     * notifies the listeners about a new Message if the doctor has access to the patients dossier
     * @param message
     */
    public static void dispatch(Message message) {
        DoctorQuerier doctorQuerier = new DoctorQuerier();
        PatientQuerier patientQuerier = new PatientQuerier();
        DossierQuerier dossierQuerier = new DossierQuerier();

        for (Pair<ObjectId, Consumer<Message>> listenerPair : listeners) {

            if (doctorQuerier.getPatients(listenerPair.getKey())
                    .stream()
                    .map(x -> patientQuerier.getDossier(x.getId()))
                    .filter(x -> x != null)
                    .flatMap(x -> dossierQuerier.getReports(x.getId(), 10).stream())
                    .anyMatch(x -> x != null && x.getId().equals(message.getReportId()))){
                listenerPair.getValue().accept(message);
            }
        }
    }
}
