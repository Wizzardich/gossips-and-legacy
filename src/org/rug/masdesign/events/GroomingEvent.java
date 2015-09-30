package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.LinkedList;
import java.util.List;

/**
 * Grooming event representation
 */
public class GroomingEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.Grooming;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {
        // Increase Grooming Fitness
        for (Agent participant: participants) {
            participant.increaseGroomingFitness();
        }
        // Remember this event

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

        int numberOfParticipants = 1;

        for (int i = 0; i < numberOfParticipants; i++) {
            int index = rand.nextInt(socialPool.size());
            participants.add(socialPool.get(index));
            socialPool.remove(index);
        }

        observers = new LinkedList<>();
        int numberOfObservers = NUM_OBSERVERS;//rand.nextInt(NUM_OBSERVERS) + 1;

        for(int i = 0; i < numberOfObservers; i++) {
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
