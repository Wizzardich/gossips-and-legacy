package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by wizzardich on 9/15/15.
 */
public class Agent {
    private double gossipProbability;
    private List<Event> memories;
    private double mutationChance;
    private Random rand;

    public Agent(double mutation, double gossip) {
        mutationChance = mutation;
        gossipProbability = gossip;
        memories = new ArrayList<>();
        rand = new Random();
    }

    public void addMemory(Event e) {
        memories.add(e);
    }

    public List<Event> getRandomMemories(int number) {
        // in case we have less then number of memories
        if (this.memories.size() < number) return this.memories;

        // otherwise we iterate number of times and get a random memory each time
        List<Event> memories = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            Event newMem;

            do {
                newMem = this.memories.get(rand.nextInt());
            } while (memories.contains(newMem));

            memories.add(newMem);
        }

        return memories;
    }

    public double fitness(){
        return 0.0;
    }

    public Agent produceChild() {
        return new Agent(mutationChance, gossipProbability);
    }
}
