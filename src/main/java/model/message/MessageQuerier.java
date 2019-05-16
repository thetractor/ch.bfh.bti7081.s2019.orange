package model.message;

import model.entities.Message;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * API to Query dossier related stuff
 * @author gian.demarmels@students.bfh.ch
 */
public class MessageQuerier extends Querier<Message> {
    @Override
    public List<Message> getAll() {
        return transaction.getMessageRepo().getAll();
    }

    @Override
    public Message get(ObjectId id) {
        return transaction.getMessageRepo().get(id);
    }
}
