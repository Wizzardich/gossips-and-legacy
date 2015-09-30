package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public class GossipEvent extends Event {
	
	//constructor of the method
	public GossipEvent (){
	}
	
	//roundParticipants: all participats of the round
	public GossipEvent(Agent initiator, List<Agent> roundParticipants){
		super();
		//TODO: randomly select 1-3 participants that have not participated
		//in an event this round
		
		//TODO:add the participants and the initiator to the participants of the
		//event
		
		//TODO:select of the participants of the event, and add ten of its memories
		//to the memories of the participants of the event
		
		//TODO: randomly select 4 participants from roundParticipants and add this
		//event to their memories
	}
    
    @Override
    public EventType getEventType() {
        return EventType.Gossip;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {

    }
}
