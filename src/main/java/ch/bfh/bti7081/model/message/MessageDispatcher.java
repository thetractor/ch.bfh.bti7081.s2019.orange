package ch.bfh.bti7081.model.message;

import com.vaadin.flow.shared.Registration;
import ch.bfh.bti7081.model.util.Pair;
import ch.bfh.bti7081.model.entities.Message;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Dispatches message to all matching, registered views.
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
public class MessageDispatcher {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Pair<ObjectId, Consumer<Message>>> listeners = new LinkedList<>();

    /**
     * Registers a report and corresponding consumer as a listener for message dispatching
     * @param listener
     * @param reportId
     * @return Registration to unregister the listener
     */
    public static synchronized Registration register(Consumer<Message> listener, ObjectId reportId) {
        Pair<ObjectId, Consumer<Message>> listenerPair = new Pair<>(reportId, listener);
        listeners.add(listenerPair);

        // Used to remove the listener when leaving the
        return () -> {
            synchronized (MessageDispatcher.class) {
                listeners.remove(listenerPair);
            }
        };
    }

    /**
     * Sends message to all registered listener which share the same report.
     *
     * @param message
     */
    public static synchronized void dispatch(Message message) {

        for (Pair<ObjectId, Consumer<Message>> listenerPair : listeners) {
            // Dispatch message to all registered listeners on the same report as the given message
            if (message.getReportId().equals(listenerPair.getFirst())){
                executor.execute(() -> listenerPair.getSecond().accept(message));
            }
        }
    }
}
