package model.Message;

import model.Entities.Message;
import model.Querier;
import org.bson.types.ObjectId;

import java.util.List;

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
