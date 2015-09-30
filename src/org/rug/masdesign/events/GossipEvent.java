package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public class GossipEvent extends Event {
    
    @Override
    public EventType getEventType() {
        return EventType.Gossip;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {
        // Get event initiator

        int initiatorIndex = rand.nextInt(participants.size());
        Agent initiator = participants.get(initiatorIndex);
        participants.remove(initiatorIndex);

        // Share information from initiator memory

        List<Event> info = initiator.getRandomMemories(GOSSIP_INFORMATION_SHARING);
        for (Agent participant: participants) {
            participant.addMemories(info);
        }

        // Remember this event

        initiator.addMemory(this);
        for (Agent participant: participants) {
            participant.addMemory(this);
        }
        for (Agent observer: observers) {
            observer.addMemory(this);
        }
    }
}
