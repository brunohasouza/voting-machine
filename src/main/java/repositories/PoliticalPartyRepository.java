package repositories;

import database.Database;
import entities.Candidate;
import entities.PoliticalParty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PoliticalPartyRepository {
    public List<PoliticalParty> findAll() {
        return Database.getInstance().getPoliticalParties();
    }

    public PoliticalParty findOne(int number) {
        for (PoliticalParty p: Database.getInstance().getPoliticalParties()) {
            if (p.getNumber() == number) {
                return p;
            }
        }

        return null;
    }
}
