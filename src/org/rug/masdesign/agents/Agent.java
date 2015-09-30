package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;
import org.rug.masdesign.events.GossipEvent;
import org.rug.masdesign.events.GroomingEvent;

import java.util.*;

/**
 * Basic unit of simulation
 */
public class Agent implements Comparable<Agent>{
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
        if (!memories.contains(e)) {
            memories.add(e);
        }
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
        if (this.memories.size() <= number) return this.memories;

        // otherwise we iterate number of times and get a random memory each time
        List<Event> memories = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            Event newMem;

            do {
                newMem = this.memories.get(rand.nextInt(this.memories.size()));
            } while (memories.contains(newMem));

            memories.add(newMem);
        }

        return memories;
    }

    public double fitness() {
        return (5 * groomingEvents + 4 * gossipEvents) * Math.pow(memories.size(), 2);
    }

    public Agent produceChild() {
        double mutation = mutationChance * rand.nextDouble();
        mutation *= rand.nextDouble() > 0.5 ? -1 : 1;
        double newProb = rand.nextDouble() < mutationChance
                ? gossipProbability + mutation
                : gossipProbability;

        newProb = Math.max(0, Math.min(1, newProb));

        return new Agent(mutationChance, newProb);
    }

    public Event whatToDo() {
        if (rand.nextDouble() < gossipProbability) return new GossipEvent();
        else return new GroomingEvent();
    }

    public void increaseGroomingFitness() {
        groomingEvents += 1;
    }

    public void increaseGossipFitness(int size) {
        gossipEvents += 1.0 / (size - 1);
    }

    public double getGossipProbability() {
        return gossipProbability;
    }


    @Override
    public int compareTo(Agent agent) {
        return (int)(this.fitness() - agent.fitness() / Math.abs(this.fitness() - agent.fitness()));
    }

    private static class AgentComparator implements Comparator<Agent> {
        @Override
        public int compare(Agent agent, Agent t1) {
            return agent.compareTo(t1);
        }
    }

    public static final AgentComparator comparator = new AgentComparator();
}
