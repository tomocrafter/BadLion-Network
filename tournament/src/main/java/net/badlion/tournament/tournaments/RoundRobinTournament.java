package net.badlion.tournament.tournaments;

import net.badlion.tournament.bracket.RoundRobinBracket;
import net.badlion.tournament.bracket.tree.bracket.Bracket;
import net.badlion.tournament.teams.Team;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RoundRobinTournament implements Tournament {

    private UUID id;
    private String name;
    private List<Team> teams;
    private boolean active;
    private Bracket bracket;

    public RoundRobinTournament(UUID id, String name, List<Team> teams, boolean active, int teamsPerMatch, Map<Integer, Integer> roundsToWin, int defaultRoundsToWin, int groupSize, boolean generateBracket) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.active = active;
        this.bracket = new RoundRobinBracket(UUID.randomUUID(), this, teamsPerMatch, defaultRoundsToWin, roundsToWin, groupSize);
        if (generateBracket) {
            this.getBracket().generate(teams, true);
        }
    }

    public RoundRobinTournament(UUID id, String name, List<Team> teams, boolean active, Bracket bracket) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.active = active;
        this.bracket = bracket;
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "roundrobin";
    }

    @Override
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public Team getTeam(UUID teamID) {
        for (Team team : this.getTeams()) {
            if (team.getID().equals(teamID)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Bracket getBracket() {
        return bracket;
    }

    @Override
    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    @Override
    public int getPlayersPerTeam() {
        return 0;
    }

    @Override
    public String getLinkToTournament() {
        return null;
    }

    @Override
    public int getRemainingMatches() {
        return this.getBracket().getActiveNodes().size();
    }
}
