package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.experiment.*;

import java.util.List;
import java.util.UUID;

public abstract class Event {
    protected UUID id;
    protected List<Agent> participants;
    protected List<Agent> observers;
    protected boolean legacy = false;
    protected Round round;

    public Event() {
        id = UUID.randomUUID();
    }
    public List<Agent> getParticipants() {
        return participants;
    }

    public List<Agent> getObservers() {
        return observers;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public void setLegacy() {
        legacy = true;
    }
    public Round getRound(){
    	return round;
    }
    public void setRound(Round r){
    	round = r;
    }

    public abstract EventType getEventType();
    public abstract void apply(List<Agent> participants, List<Agent> observers);

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Event)) return false;
        Event otherEvent = (Event) other;
        return otherEvent.id.equals(this.id);
    }
}
