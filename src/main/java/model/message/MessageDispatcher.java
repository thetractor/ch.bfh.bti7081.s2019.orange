package model.message;

import com.vaadin.flow.shared.Registration;
import javafx.util.Pair;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class MessageDispatcher {
    static Executor executor = Executors.newSingleThreadExecutor();

    //static LinkedList<Consumer<String>> listeners = new LinkedList<>();
    static LinkedList<Pair<ObjectId, Consumer<String>>> listeners = new LinkedList<>();

    /**
     *
     *
     * @param listener
     * @return
     */
    public static synchronized Registration register(Consumer<String> listener, ObjectId doctorId) {

        System.out.println(String.format("Register: %s", listener.toString()));

        Pair<ObjectId, Consumer<String>> listenerPair = new Pair<>(doctorId, listener);
        listeners.add(listenerPair);
        //listeners.add(listener);

        return () -> {
            synchronized (MessageDispatcher.class) {
                listeners.remove(listenerPair);
                System.out.println("********* ---------------- **************");
                //listeners.remove(listener);
            }
        };
    }

    /**
     *
     * @param message
     */
    public static synchronized void dispatch(String message, ObjectId doctorId) {
        System.out.println(String.format("Dispatching message: %s", message));


        for (Pair<ObjectId, Consumer<String>> listenerPair : listeners) {
        //for (Consumer<String> listener : listeners) {
            System.out.println(String.format("Execute message [%s] on listener [%s]", message, listenerPair.toString()));

            // TODO: change logic of course
            if (doctorId != listenerPair.getKey()){
                // Accept
                executor.execute(() -> listenerPair.getValue().accept(message));
            }
        }
    }
}
