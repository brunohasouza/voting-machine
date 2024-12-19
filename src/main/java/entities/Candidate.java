package entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Candidate {
    private String name;
    private PoliticalParty politicalParty;
    private int number;

    private int votes;

    private boolean elected;

    public Candidate() {}

    public Candidate(String name, PoliticalParty politicalParty, int number) {
        this.name = name;
        this.politicalParty = politicalParty;
        this.number = number;
        this.elected = false;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public PoliticalParty getPoliticalParty() {
        return politicalParty;
    }

    public int getNumber() {
        return number;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isElected() {
        return elected;
    }

    public void setElected(boolean elected) {
        this.elected = elected;
    }
}
