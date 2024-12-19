package entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Blank {
    private int votes;

    public Blank() {
        votes = 0;
    }

    public int getVotes() {
        return votes;
    }

    public void increment() {
        votes += 1;
    }
}
