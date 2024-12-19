package resources;

import entities.Candidate;
import entities.PoliticalParty;
import entities.Vote;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repositories.BlankRepository;
import repositories.CandidateRepository;
import repositories.PoliticalPartyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("")
public class ElectionResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("candidates")
    public Response candidates() {
        CandidateRepository repository = new CandidateRepository();
        List<Candidate> list = repository.findAll();

        return Response.ok(list).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("candidates/{number}")
    public Response candidate(@PathParam("number") int number) {
        CandidateRepository repository = new CandidateRepository();
        Candidate candidate = repository.findOne(number);

        if (candidate == null) {
            return Response.noContent().build();
        }

        return Response.ok(candidate).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("political-parties")
    public Response politicalParties() {
        PoliticalPartyRepository repository = new PoliticalPartyRepository();
        List<PoliticalParty> list = repository.findAll();

        return Response.ok(list).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("political-parties/{number}")
    public Response policitalParty(@PathParam("number") int number) {
        PoliticalPartyRepository repository = new PoliticalPartyRepository();
        PoliticalParty politicalParty = repository.findOne(number);

        if (politicalParty == null) {
            return Response.noContent().build();
        }

        return Response.ok(politicalParty).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("vote")
    public Response vote(Vote vote) {
        if (vote.isBlank()) {
            BlankRepository repository = new BlankRepository();
            repository.increment();
            return Response.ok().build();
        }

        try {
            CandidateRepository repository = new CandidateRepository();
            repository.registerVote(vote.getCandidate());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("results")
    @Produces(MediaType.APPLICATION_JSON)
    public Response results() {
        Map<String, Object> response = new HashMap<>();
        CandidateRepository cRepository = new CandidateRepository();
        PoliticalPartyRepository pRepository = new PoliticalPartyRepository();
        BlankRepository bRepository = new BlankRepository();

        response.put("candidates", cRepository.findAll());
        response.put("politicalParties", pRepository.findAll());
        response.put("blank", bRepository.getVotes());

        return Response.ok(response).build();
    }
}
