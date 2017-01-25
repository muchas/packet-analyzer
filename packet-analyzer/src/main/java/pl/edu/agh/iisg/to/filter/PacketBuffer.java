package pl.edu.agh.iisg.to.filter;
import pl.edu.agh.iisg.to.collector.Packet;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class PacketBuffer implements PacketProcessingQueue {

    private Queue<Packet> packetQueue;
    private static PacketBuffer instance = null;

    private PacketBuffer() {
        this.packetQueue = new ConcurrentLinkedQueue<>();
    }

    public static PacketBuffer getInstance() {
        if(instance == null) {
            instance = new PacketBuffer();
        }
        return instance;
    }

    @Override
    public void push(Packet packet) {
        packetQueue.add(packet);
        System.out.println("Adding packet to PacketBuffer queue " + packet.getProperty("number"));
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
