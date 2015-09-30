package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;
import org.rug.masdesign.events.GossipEvent;
import org.rug.masdesign.events.GroomingEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Basic unit of simulation
 */
public class Agent {
    private double gossipProbability;
    private List<Event> memories;
    private double mutationChance;
    private static Random rand = new Random();

    // These can be implemented dynamically only whe we need a fitness value.
    // Is that really optimal?

    private double groomingEvents = 0.0;
    private double gossipEvents = 0.0;

    public Agent(double mutation, double gossip) {
        mutationChance = mutation;
        gossipProbability = gossip;
        memories = new ArrayList<>();
    }

    public void addMemory(Event e) {
        memories.add(e);
    }

    public void addMemories(Iterable<Event> e) {
        for (Event event : e) {
            if (!memories.contains(event)) {
                memories.add(event);
            }
        }
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

    public Event whatToDo() {
        if (rand.nextDouble() > gossipProbability) return new GossipEvent();
        else return new GroomingEvent();
    }

    public void increaseGroomingFitness() {
        groomingEvents += 1;
    }

    public void increaseGossipFitness(int size) {
        gossipEvents += 1.0 / (size - 1);
    }
}
