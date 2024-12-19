package entities;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PoliticalParty {
    private String name;
    private String abbreviation;
    private int number;

    private int votes;

    private int vacancies;

    public PoliticalParty() {}

    public PoliticalParty(String name, String abbreviation, int number) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
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

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }
}
