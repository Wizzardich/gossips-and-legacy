package org.rug.masdesign;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.agents.Population;
import org.rug.masdesign.events.Event;

public class Main {
	
    public static void main(String[] args) {
        int generations = 210;

        Event.GOSSIP_INFORMATION_SHARING = 10;
        Event.MAX_GOSSIPERS = 4;
        Event.NUM_OBSERVERS = 4;

        Population.SUCCESS_MODIFIER = 0.05;
        Population.FAIL_MODIFIER = 0.05;

        Agent.MAX_DEVIATION = 0.05;

        for (int pop = 10; pop < 250 ;pop +=40) {
            double result = 0.0;
            for(int j = 0; j < 5; j++) {
                Population population = new Population(pop, 0.1, 0.01, 60);

                for (int i = 0; i < generations; i++) {
                    population.execGeneration();
                    population.nextGeneration();
                }

                System.out.println("Average Gossip probability for " + pop + " people = " + population.getAverage());
                result += population.getAverage();
            }
            result /= 5;
            System.out.println("Average Gossip overall probability for " + pop + " people = " + result);
        }
    }
}
