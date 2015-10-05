package org.rug.masdesign.experiment;

import org.rug.masdesign.agents.Agent;
import org.rug.masdesign.events.Event;
import org.rug.masdesign.events.EventType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Round {

    private List<Agent> agents;
    private ArrayList<Agent> initiatorPool;
    private static Random rand = new Random();
    private List<Event> events;

    public Round(List<Agent> people) {
        agents = people;
        initiatorPool = new ArrayList<>();
        initiatorPool.addAll(agents);
        events = new LinkedList<>();
    }

	public void execute(){
		//a number of agents initiates events
		//for every agent of the fraction, a random value v
		//if v > agent's gossip probability = agent will groom, else gossip
		//either a groom event or a gossip event is created
//        while (initiatorPool.size() >= 2) {
//            Agent initiator = initiatorPool.get(rand.nextInt(initiatorPool.size()));
//            initiatorPool.remove(initiator);
//
//            Event event = initiator.whatToDo();
//            event.build(initiator, initiatorPool, agents);
//            event.setRound(this);
//            event.execute();
//
//            events.add(event);
//        }
        List<Agent> groomers = new LinkedList<>();
        List<Agent> gossipers = new LinkedList<>();

        //divides agents into gossipers and groomers
        for (Agent agent: initiatorPool) {
            switch(agent.wantsToDo()) {
                case Grooming: groomers.add(agent);
                    break;
                case Gossip: gossipers.add(agent);
                    break;
            }
        }

        generateEvents(EventType.Grooming, groomers);
        generateEvents(EventType.Gossip, gossipers);

	}

    public List<Event> getEvents() {
        return events;
    }


    public void generateEvents(EventType et, List<Agent> pool) {
        while (pool.size() >= 2) {
            Agent initiator = pool.get(rand.nextInt(pool.size()));
            pool.remove(initiator);

            Event event = et.get();
            event.build(initiator, pool, agents);
            event.setRound(this);
            event.execute();

            events.add(event);
        }
    }
}
