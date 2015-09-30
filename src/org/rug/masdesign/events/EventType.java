package org.rug.masdesign.events;

/**
 * Created by wizzardich on 9/15/15.
 */
public enum EventType {
    /**
     * Means to convey the idea, that this event is a gossip event
     */
    Gossip,
    /**
     * Means to convey the idea, that this event is a grooming event
     */
    Grooming;

    public Event get() {
        switch(this) {
            case Gossip:
                return new GossipEvent();
            case Grooming:
                return new GroomingEvent();
        }
        return null;
    }
}
