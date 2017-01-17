package pl.edu.agh.iisg.to.filter;

public interface PacketProcessingQueue {
    public void push(Packet packet);
    public Packet pop();
    public boolean isEmpty();
}
