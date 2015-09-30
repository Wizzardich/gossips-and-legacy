package org.rug.masdesign.experiment;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.events.Event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Round {

    private List<Agent> agents;
    private ArrayList<Agent> initiatorPool;
    private int agentNumber;
    private static Random rand = new Random();
    private List<Event> events;

    public Round(List<Agent> people) {
        agents = people;
        initiatorPool = new ArrayList<>();
        initiatorPool.addAll(agents);
        agentNumber = agents.size();
        events = new LinkedList<>();
    }

	public void execute(){
		//a number of agents initiates events
		//for every agent of the fraction, a random value v
		//if v > agent's gossip probability = agent will groom, else gossip
		//either a groom event or a gossip event is created
        while (initiatorPool.size() >= 2) {
            Agent initiator = initiatorPool.get(rand.nextInt(initiatorPool.size()));
            initiatorPool.remove(initiator);

            Event event = initiator.whatToDo();
            event.build(initiator, initiatorPool, agents);
            event.setRound(this);
            event.execute();

            events.add(event);
        }

	}

    public List<Event> getEvents() {
        return events;
    }

}
