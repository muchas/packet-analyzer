package sample;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class PacketBuffer implements PacketProcessingQueue {

    private Queue<Packet> packetQueue;

    PacketBuffer() {
        this.packetQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void push(Packet packet) {
        packetQueue.add(packet);
    }

    @Override
    public Packet pop() {
        return packetQueue.remove();
    }

    @Override
    public boolean isEmpty() {
        return packetQueue.isEmpty();
    }
}
