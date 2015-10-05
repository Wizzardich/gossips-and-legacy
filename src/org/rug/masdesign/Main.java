package org.rug.masdesign;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.agents.Population;
import org.rug.masdesign.events.Event;

public class Main {
	
	public static void main(String[] args) {
		new Gui();
	}
	
    public static void mainOld(String[] args) {
        int generations = 210;
        double startingGossipProbability = 0.5;
        int roundsPerGeneration = 30;

        Event.GOSSIP_INFORMATION_SHARING = 10;
        Event.MAX_GOSSIPERS = 4;
        Event.NUM_OBSERVERS = 4;

        Population.SUCCESS_MODIFIER = 0.05;
        Population.FAIL_MODIFIER = 0.05;

        Agent.MAX_DEVIATION = 0.05;
        Agent.MUTATION_CHANCE = 0.01;

        for (int populationSize = 10; populationSize < 250; populationSize +=20) {
            double result = 0.0;
            for(int j = 0; j < 5; j++) {
                Population population = new Population(populationSize, startingGossipProbability, roundsPerGeneration);

                population.execNGenerations(generations);

                System.out.println("Average Gossip probability for " + populationSize + " people = " + population.getAverage());
                result += population.getAverage();
            }
            result /= 5;
            System.out.println("Average Gossip overall probability for " + populationSize + " people = " + result);
        }
    }
    public static double getDefaultFinalAverage(int populationSize){
    	
    	Population population = new Population(populationSize, 0.5, 30);
        population.execNGenerations(210);
    	return population.getAverage();
    }
}
