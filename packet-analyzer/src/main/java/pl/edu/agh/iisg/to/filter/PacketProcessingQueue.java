package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.collector.Packet;

public interface PacketProcessingQueue {
    public void push(Packet packet);
    public Packet pop();
    public boolean isEmpty();
}
