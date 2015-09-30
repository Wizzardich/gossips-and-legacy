package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public class GroomingEvent extends Event {
	public GroomingEvent(){
		
	}
	//roundParticipants: all participats of the round
	public GroomingEvent(Agent initiator, List<Agent> roundParticipants){
		//TODO: randomly select 1 participants that have not participated
		//in an event this round
		
		//TODO:add initiator and selected participant to the participants of the
		//event
		
		//TODO:add this event to the memory of both parcipants
		
		//TODO: randomly select 4 participants from roundParticipants and add this
		//event to their memories
	}
    @Override
    public EventType getEventType() {
        return EventType.Grooming;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {

    }
}
