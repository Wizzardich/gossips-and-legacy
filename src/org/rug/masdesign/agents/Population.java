package org.rug.masdesign.agents;

import org.rug.masdesign.experiment.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Main simulation class
 */
public class Population {
    private int roundsPerGeneration = 30;
    private List<Agent> population;
    public static double SUCCESS_MODIFIER = 0.05;
    public static double FAIL_MODIFIER = 0.05;
    public static int SIZE_THRESHOLD = 50;
    public static int SIZE_MODIFIER = 10;
    private int size;

    public Population(int size, double startingGossipProbability, int roundsPerGeneration) {
        this.size = size;
        int tempSize = size;

        SIZE_MODIFIER = (int)Math.ceil(SIZE_THRESHOLD * 1.0 / size);

        if (size < SIZE_THRESHOLD) tempSize *= SIZE_MODIFIER;

        this.roundsPerGeneration = roundsPerGeneration;
        this.population = new ArrayList<>();
        for (int i = 0; i < tempSize; i++) {
            population.add(new Agent(startingGossipProbability));
        }
    }

    public void nextRound(){
        if (this.size >= SIZE_THRESHOLD) {
            Round r = new Round(population);
            r.execute();
        }
        else {
            for (int i = 0; i < SIZE_MODIFIER - 1; i++) {
                Round r = new Round(population.subList(i * size, (i + 1) * size));
                r.execute();
            }
        }
    }

    public void nextGeneration() {
        population.sort(Agent.comparator);
        List<Agent> nextGen = new ArrayList<>();
        for (int i = 0; i < (int)Math.ceil(population.size() * SUCCESS_MODIFIER); i++) {
            //Agent a = population.get(i).produceChild();
            //nextGen.add(a);
            //nextGen.add(new Agent(a.getGossipProbability()));
            nextGen.add(population.get(i).produceChild());
            nextGen.add(population.get(i).produceChild());
        }

        for (int i = (int)Math.ceil(population.size() * SUCCESS_MODIFIER);
             i < (int)(population.size() * (1 - FAIL_MODIFIER));
             i++) {
            nextGen.add(population.get(i).produceChild());
        }

        population = nextGen;
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

    public void execNGenerations(int numberOfGenerations){
    	
    	for (int i = 0; i < numberOfGenerations; i++) {
            this.execGeneration();
            this.nextGeneration();
        }
    }
}
