package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.collector.Packet;
import pl.edu.agh.iisg.to.visualizer.App;

public class PacketConsumer implements Consumer {

    private PacketProcessingQueue buffer;
    private FilterApplier filterApplier;
    private Statistics statistics;

    public PacketConsumer(FilterApplier applier, Statistics statistics) {
        this.buffer = PacketBuffer.getInstance();
        this.filterApplier = applier;
        this.statistics = statistics;
    }

    @Override
    public void execute() {
        Packet packet;
        App visualizer = App.getInstance();

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
