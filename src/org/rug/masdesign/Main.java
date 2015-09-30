package org.rug.masdesign;

import org.rug.masdesign.agents.Population;

public class Main {
	
    public static void main(String[] args) {
        int generations = 200;

        Population population = new Population(20, 0.5, 0.05, 30);

        for (int i = 0; i < generations; i++) {
            population.execGeneration();
            System.out.println("Average Gossip probability = " + population.getAverage());
            population.nextGeneration();
        }
    }
}
