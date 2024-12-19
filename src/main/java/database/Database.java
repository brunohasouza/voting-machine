package database;

import entities.Blank;
import entities.Candidate;
import entities.PoliticalParty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Database {
    private static Database instance;
    private static Blank blank = new Blank();
    private static List<Candidate> candidates = new ArrayList<>();
    private static List<PoliticalParty> politicalParties = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    static {
        PoliticalParty politicalParty = new PoliticalParty("PARTIDO DEMOCRÁTICO TRABALHISTA", "PDT", 12);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("JOSÉ LUIS SCHAFER", politicalParty, 1212));
        candidates.add(new Candidate("VANDA CHERFEN DE SOUZA", politicalParty, 1201));
        candidates.add(new Candidate("SEBASTIÃO SOUZA QUEIROZ", politicalParty, 1207));
        candidates.add(new Candidate("EDUARDO BRASIL DANTAS", politicalParty, 1222));

        politicalParty = new PoliticalParty("PARTIDO DOS TRABALHADORES", "PT", 13);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("AURINETE DE OLIVEIRA SILVA", politicalParty, 1365));
        candidates.add(new Candidate("MARCELO FEITOSA DE ARAÚJO", politicalParty, 1310));
        candidates.add(new Candidate("FRANCISCA CILENE RODRIGUES LOPES", politicalParty, 1377));
        candidates.add(new Candidate("LENILDA DE SOUSA QUEIROZ", politicalParty, 1311));

        politicalParty = new PoliticalParty("PARTIDO SOCIALISMO E LIBERDADE", "PSOL", 50);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("EMERSON DA SILVA SANTOS", politicalParty, 5050));
        candidates.add(new Candidate("ANA PAULA MORAIS DE HOLANDA", politicalParty, 5012));
        candidates.add(new Candidate("GERALDO SILVA DOS SANTOS", politicalParty, 5051));
        candidates.add(new Candidate("PAULO ROBERTO CHAGAS SANTOS", politicalParty, 5000));

        politicalParty = new PoliticalParty("PARTIDO DO MOVIMENTO DEMOCRÁTICO BRASILEIRO", "PMDB", 15);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("FLAVIANO FLÁVIO BAPTISTA DE MELO", politicalParty, 1555));
        candidates.add(new Candidate("JÉSSICA ROJAS SALES", politicalParty, 1515));
        candidates.add(new Candidate("VANIA LUCIA SOUZA DA SILVA", politicalParty, 1516));
        candidates.add(new Candidate("YONARA TENÓRIO TOLEDO", politicalParty, 1560));

        politicalParty = new PoliticalParty("PARTIDO SOCIALISTA BRASILEIRO", "PSB", 40);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("CARLOS CESAR CORREIA DE MESSIAS", politicalParty, 4040));
        candidates.add(new Candidate("IRIS DOS SANTOS MELO", politicalParty, 4044));
        candidates.add(new Candidate("ALVARO FERREIRA GUIMARÃES FILHO", politicalParty, 4000));
        candidates.add(new Candidate("MARIA LIGIA MININ DE LINS", politicalParty, 4099));

        politicalParty = new PoliticalParty("PARTIDO DA SOCIAL DEMOCRACIA BRASILEIRA", "PSDB", 45);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("LUIZETE SILVA E SILVA", politicalParty, 4554));
        candidates.add(new Candidate("FRANCISCO NAZARENO DA SILVA", politicalParty, 4510));
        candidates.add(new Candidate("EDSON BRAGA RODRIGUES", politicalParty, 4567));
        candidates.add(new Candidate("JOSÉ VIEIRA DE FARIAS", politicalParty, 4500));

        politicalParty = new PoliticalParty("PARTIDO SOCIAL LIBERAL", "PSL", 17);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("MARILEIDE SERAFIM DE ANDRADE", politicalParty, 1733));
        candidates.add(new Candidate("GILMAR EDUARDO COSTA DO COUTO", politicalParty, 1717));
        candidates.add(new Candidate("ADILSON VICENTE DA SILVA", politicalParty, 1777));
        candidates.add(new Candidate("FLORISVALDO BISPO DE JESUS", politicalParty, 1713));

        politicalParty = new PoliticalParty("PARTIDO COMUNISTA BRASILEIRO", "PCB", 21);
        politicalParties.add(politicalParty);
        candidates.add(new Candidate("MARCELO FURTADO MACHADO LEITÃO", politicalParty, 2111));
        candidates.add(new Candidate("VALDIRENE PICANÇO DE CASTRO", politicalParty, 2100));
        candidates.add(new Candidate("FRANCISCO ROBERTO DOS SANTOS", politicalParty, 2123));
        candidates.add(new Candidate("IGOR SANTA CRUZ LOPES", politicalParty, 2121));

        for (Candidate candidate: candidates) {
            Random r = new Random();
            candidate.setVotes(r.nextInt(21));
        }

        refreshDb();
    }

    public Blank getBlank() {
        return blank;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<PoliticalParty> getPoliticalParties() {
        return politicalParties;
    }

    public void setBlankVote() {
        blank.increment();
    }

    public void setVote(Candidate c) {
        c.setVotes(c.getVotes() + 1);
        refreshDb();
    }

    private static void calculatePartyVotes() {
        for (PoliticalParty party : politicalParties) {
            int votes = candidates.stream()
                    .filter(c -> c.getPoliticalParty().getNumber() == party.getNumber())
                    .reduce(0, (total, c) -> total + c.getVotes(), Integer::sum);

            party.setVotes(votes);
        }
    }

    private static void distributeVacancies() {
        int TOTAL_VACANCIES = 7;
        int totalVotes = politicalParties.stream().reduce(0, (total, c) -> total + c.getVotes(), Integer::sum);
        int quotientTotal = (int) Math.ceil(totalVotes / TOTAL_VACANCIES);

        for (PoliticalParty politicalParty : politicalParties) {
            politicalParty.setVacancies((int) politicalParty.getVotes() / quotientTotal);
        }

        int sumVacancies = politicalParties.stream().reduce(0, (total, pp) -> total + pp.getVacancies(), Integer::sum);
        int diff = TOTAL_VACANCIES - sumVacancies;

        while (diff > 0) {
            PoliticalParty politicalParty = getNextPoliticalParty();
            politicalParty.setVacancies(politicalParty.getVacancies() + 1);

            diff--;
        }
    }

    private static PoliticalParty getNextPoliticalParty() {
        PoliticalParty bigger = null;

        for (PoliticalParty politicalParty : politicalParties) {
            if (bigger == null) {
                bigger = politicalParty;
                continue;
            }

            int biggerDiv = bigger.getVotes() / (bigger.getVacancies() + 1);
            int currentDiv = politicalParty.getVotes() / (politicalParty.getVacancies() + 1);

            if (currentDiv > biggerDiv) {
                bigger = politicalParty;
            }
        }

        return bigger;
    }

    private static void setElectedCandidates() {
        List<Integer> electedAuxList = new ArrayList<>();

        for (Candidate c : candidates) {
            int partyVacancies = c.getPoliticalParty().getVacancies();
            int electedCount = (int) electedAuxList.stream().filter(i -> i == c.getPoliticalParty().getNumber()).count();

            if (electedCount < partyVacancies) {
                c.setElected(true);
                electedAuxList.add(c.getPoliticalParty().getNumber());
            } else {
                c.setElected(false);
            }
        }
    }

    private static void sortDb() {
        candidates.sort(Comparator.comparing(Candidate::getVotes).reversed());
        politicalParties.sort(Comparator.comparing(PoliticalParty::getVotes).reversed());
    }

    private static void refreshDb() {
        calculatePartyVotes();
        distributeVacancies();
        sortDb();
        setElectedCandidates();
    }
}
