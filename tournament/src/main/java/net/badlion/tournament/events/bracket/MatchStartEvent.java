package net.badlion.tournament.events.bracket;

import net.badlion.tournament.bracket.tree.bracket.SeriesNode;
import net.badlion.tournament.events.TournamentEvent;
import net.badlion.tournament.matches.Round;
import net.badlion.tournament.tournaments.Tournament;
import org.bukkit.event.HandlerList;

public class MatchStartEvent extends TournamentEvent {

    private static final HandlerList handlers = new HandlerList();

    private SeriesNode node;
    private Round round;

    public MatchStartEvent(Tournament tournament, SeriesNode node, Round round) {
        super(tournament);
        this.node = node;
        this.round = round;
    }

    public SeriesNode getSeriesNode() {
        return node;
    }

    public Round getRound() {
        return round;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
