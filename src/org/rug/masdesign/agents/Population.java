package org.rug.masdesign.agents;

import org.rug.masdesign.events.Event;
import org.rug.masdesign.experiment.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Main simulation class
 */
public class Population {
    private int roundsPerGeneration = 30;
    private List<Agent> population;
    public static double SUCCESS_MODIFIER = 0.05;
    public static double FAIL_MODIFIER = 0.05;

    public Population(int size, double startingGossipProbability, double mutationProbability, int roundsPerGeneration) {
        this.roundsPerGeneration = roundsPerGeneration;
        this.population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            population.add(new Agent(mutationProbability, startingGossipProbability));
        }
    }

    public void nextRound(){
        Round r = new Round(population);

//        System.out.printf("Round starts now\n");

        r.execute();

        //TODO get this inside round

        int gossiping = 0;
        int grooming = 0;

        for (Event e: r.getEvents()) {
            switch (e.getEventType()) {
                case Gossip: gossiping++;
                    break;
                case Grooming: grooming++;
                    break;
            }
        }

//        System.out.println("Gossiping: " + gossiping + "\nGrooming: " + grooming);
//        System.out.printf("Round successful\n");
    }

    public void nextGeneration() {
        population.sort(Agent.comparator);
        List<Agent> nextgen = new ArrayList<>();
        for (int i = 0; i < (int)(population.size() * SUCCESS_MODIFIER); i++) {
            nextgen.add(population.get(i).produceChild());
            nextgen.add(population.get(i).produceChild());
        }

        for (int i = (int)(population.size() * SUCCESS_MODIFIER);
             i <= (int)(population.size() * (1 - FAIL_MODIFIER));
             i++) {
            nextgen.add(population.get(i).produceChild());
        }

        population = nextgen;
        System.out.println(nextgen.size());
    }

    public void execGeneration() {
        for (int i = 0; i < roundsPerGeneration; i++) {
            nextRound();
        }
    }

    public double getAverage() {
        double sum = 0.0;
        for(Agent a : population) {
            sum += a.getGossipProbability();
        }
        return sum / population.size();
    }
}
