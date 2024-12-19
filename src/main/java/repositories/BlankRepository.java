package repositories;

import database.Database;
import entities.Blank;

public class BlankRepository {
    public void increment() {
        Database.getInstance().setBlankVote();
    }

    public Blank getVotes() {
        return Database.getInstance().getBlank();
    }
}
