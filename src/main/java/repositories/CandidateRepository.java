package repositories;

import database.Database;
import entities.Candidate;
import java.util.List;

public class CandidateRepository {
    public Candidate findOne(int number) {
        for (Candidate candidate: Database.getInstance().getCandidates()) {
            if (candidate.getNumber() == number) {
                return candidate;
            }
        }

        return null;
    }

    public List<Candidate> findAll() {
        return Database.getInstance().getCandidates();
    }

    public void registerVote(int number) throws Exception {
        Candidate candidate = findOne(number);

        if (candidate == null) {
            throw new Exception("Candidate not found");
        }

        Database.getInstance().setVote(candidate);
    }
}
