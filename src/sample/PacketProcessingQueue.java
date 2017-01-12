package sample;


public interface PacketProcessingQueue {
    public void push(Packet packet);
    public Packet pop();
    public boolean isEmpty();
}
