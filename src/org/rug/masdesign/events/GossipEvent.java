package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.LinkedList;
import java.util.List;

/**
 * Gossip event representation
 */
public class GossipEvent extends Event {
    
    @Override
    public EventType getEventType() {
        return EventType.Gossip;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {

        // Increase Gossip Fitness
        for (Agent participant: participants) {
            participant.increaseGossipFitness(participants.size());
        }

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

        participants.add(initiator);
        for (Agent participant: participants) {
            participant.addMemory(this);
        }
        for (Agent observer: observers) {
            observer.addMemory(this);
        }
    }

    @Override
    public void build(Agent initiator, List<Agent> socialPool, List<Agent> allAgents) {
        participants = new LinkedList<>();
        participants.add(initiator);

        int numberOfParticipants = rand.nextInt(MAX_GOSSIPERS - 1) + 1;
        numberOfParticipants = numberOfParticipants > socialPool.size()
                ? socialPool.size()
                : numberOfParticipants;

        for (int i = 0; i < numberOfParticipants; i++) {
            int index = rand.nextInt(socialPool.size());
            participants.add(socialPool.get(index));
            socialPool.remove(index);
        }

        if (participants.size() == 1) {
            System.out.println("here");
        }

        observers = new LinkedList<>();
        int numberOfObservers = NUM_OBSERVERS;//rand.nextInt(NUM_OBSERVERS) + 1;

        for (int i = 0; i < numberOfObservers; i++) {
            boolean added = false;
            while (!added) {
                int index = rand.nextInt(allAgents.size());
                Agent candidate = allAgents.get(index);
                if (!participants.contains(candidate)) {
                    added = true;
                    observers.add(candidate);
                }
            }
        }
    }
}
