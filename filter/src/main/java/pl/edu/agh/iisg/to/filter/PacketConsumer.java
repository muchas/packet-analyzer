package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.collector.Packet;
import pl.edu.agh.iisg.to.visualizer.App;

public class PacketConsumer implements Consumer {

    private PacketProcessingQueue buffer;
    private FilterApplier filterApplier;
    private Statistics statistics;
    private App visualizer;

    public PacketConsumer(FilterApplier applier, Statistics statistics, App visualizer) {
        this.buffer = PacketBuffer.getInstance();
        this.filterApplier = applier;
        this.statistics = statistics;
        this.visualizer = visualizer;
    }

    @Override
    public void execute() {
        Packet packet;

        while(!this.buffer.isEmpty()) {

            packet = this.buffer.pop();

            if(!filterApplier.apply(packet)) {
                continue;
            }

            statistics.update(packet);


            // push to visualizer's queue
            visualizer.addPacketToQueue(packet);
        }
    }
}
