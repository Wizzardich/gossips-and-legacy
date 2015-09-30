package org.rug.masdesign;

import org.rug.masdesign.agents.Population;

public class Main {
	
    public static void main(String[] args) {
        Population population = new Population(100, 0.8, 0.05,30);
        population.nextGeneration();
    }
}
