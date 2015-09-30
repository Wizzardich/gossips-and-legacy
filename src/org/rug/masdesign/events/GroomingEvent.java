package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

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
}
