package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;

import java.util.List;

/**
 * Created by wizzardich on 9/15/15.
 */
public class Agent {
    private double gossipProbability;
    private List<Event> memories;

    public void addMemory(Event e) {
        memories.add(e);
    }

    public void groom(Agent other) {

    }

    public void gossip(List<Agent> others) {

    }

    public double fitness(){
        return 0.0;
    }
}
