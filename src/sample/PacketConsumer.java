package sample;


public class PacketConsumer implements Consumer {

    private PacketProcessingQueue buffer;
    private FilterApplier filterApplier;
    private Statistics statistics;

    PacketConsumer(PacketProcessingQueue buffer, FilterApplier applier, Statistics statistics) {
        this.buffer = buffer;
        this.filterApplier = applier;
        this.statistics = statistics;
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
        }
    }
}
