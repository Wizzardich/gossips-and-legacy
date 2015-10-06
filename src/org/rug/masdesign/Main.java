package org.rug.masdesign;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.agents.Population;
import org.rug.masdesign.events.Event;

public class Main {

    public static void main(String[] args) {
        int n = 210;
        double startingGossipProbability = 0.5;
        int roundsPerGeneration = 30;

        Event.GOSSIP_INFORMATION_SHARING = 10;
        Event.MAX_GOSSIPERS = 4;
        Event.NUM_OBSERVERS = 4;

        Population.SUCCESS_MODIFIER = 0.05;
        Population.FAIL_MODIFIER = 0.05;

        Agent.MAX_DEVIATION = 0.05;
        Agent.MUTATION_CHANCE = 0.01;

        for (int populationSize = 10; populationSize < 220; populationSize +=20) {
            double result = 0.0;
            for(int j = 0; j < 5; j++) {
                Population population = new Population(populationSize, startingGossipProbability, roundsPerGeneration);

                population.execNGenerations(n);

                System.out.println("Average Gossip probability for " + populationSize + " people = " + population.getAverage());
                result += population.getAverage();
            }
            result /= 5;
            System.out.println("Average Gossip overall probability for " + populationSize + " people = " + result);
        }

//        Population population = new Population(10, startingGossipProbability, roundsPerGeneration);
//        for (int i = 0; i < n; i++) {
//            population.execGeneration();
//            population.nextGeneration();
//            System.out.println(population.getAverage());
//        }
    }

}
