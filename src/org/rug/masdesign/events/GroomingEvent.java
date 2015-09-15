package org.rug.masdesign.events;

import org.rug.masdesign.agents.Agent;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public class GroomingEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.Grooming;
    }

    @Override
    public void apply(List<Agent> participants, List<Agent> observers) {

    }
}
