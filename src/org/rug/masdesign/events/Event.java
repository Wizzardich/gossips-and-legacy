package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public abstract class Event {
	public int id;
    protected List<Agent> participants;
    protected List<Agent> observers;
    protected boolean legacy = false;
    protected Round round;

    public Event(){
    	//TODO: assign ID
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
}
