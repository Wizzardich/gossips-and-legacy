package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;
import org.rug.masdesign.events.EventType;
import org.rug.masdesign.events.GossipEvent;
import org.rug.masdesign.events.GroomingEvent;

import java.util.*;

/**
 * Basic unit of simulation
 */
public class Agent implements Comparable<Agent>{
    private double gossipProbability;
    private List<Event> memories;
    private static Random rand = new Random();
    public static double MAX_DEVIATION = 0.05;
    public static double MUTATION_CHANCE = 0.01;
    private double fit = 0.0;

    // These can be implemented dynamically only whe we need a fitness value.
    // Is that really optimal?

    private double groomingEvents;
    private double gossipEvents;


    public Agent(double gossip) {
        gossipProbability = gossip;
        groomingEvents = 0.0;
        gossipEvents = 0.0;

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
        return (5 * groomingEvents + 4 * gossipEvents) * memories.size() * memories.size();
    }

    public Agent produceChild() {
        double mutation = MAX_DEVIATION * rand.nextDouble();
        mutation *= rand.nextDouble() > 0.5 ? -1 : 1;
        double newProb = rand.nextDouble() < MUTATION_CHANCE
                ? gossipProbability + mutation
                : gossipProbability;

        newProb = Math.max(0, Math.min(1, newProb));

        return new Agent(newProb);
    }

    public Event whatToDo() {
        if (rand.nextDouble() < gossipProbability) return new GossipEvent();
        else return new GroomingEvent();
    }

    public EventType wantsToDo() {
        if (rand.nextDouble() < gossipProbability) return EventType.Gossip;
        else return EventType.Grooming;
    }

    public void increaseGroomingFitness() {
        groomingEvents += 1;
        fit = fitness();
    }

    public void increaseGossipFitness(int size) {
        gossipEvents += 1.0 / (size - 1);
        fit = fitness();
    }

    public void increaseFitness(EventType et, int size) {
        switch(et) {
            case Gossip:
                increaseGroomingFitness();
                break;
            case Grooming:
                increaseGossipFitness(size);
                break;
        }
    }

    public double getGossipProbability() {
        return gossipProbability;
    }


    @Override
    public int compareTo(Agent agent) {
        return (int)Math.signum(this.fitness() - agent.fitness());
    }

    private static class AgentComparator implements Comparator<Agent> {
        @Override
        public int compare(Agent agent, Agent t1) {
            return t1.compareTo(agent);
        }
    }

    public static final AgentComparator comparator = new AgentComparator();
}
