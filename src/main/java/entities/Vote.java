package entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Vote {
    private int candidate;
    private boolean isBlank;

    public Vote() {}

    public int getCandidate() {
        return candidate;
    }

    public boolean isBlank() {
        return isBlank;
    }
}
