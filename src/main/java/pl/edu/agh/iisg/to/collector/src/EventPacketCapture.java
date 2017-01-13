package src.main.java.pl.edu.agh.iisg.to.collector.src;
import net.sourceforge.jpcap.capture.*;
import java.lang.*;

public class EventPacketCapture {
    private static final int INFINITE = -1;
    private static final int PACKET_COUNT = INFINITE;

    // BPF filter for capturing any packet
    private static final String FILTER = "";

    private PacketCapture m_pcap;
    private String m_device;

    public EventPacketCapture() throws Exception {
        // Step 1:  Instantiate Capturing Engine
        m_pcap = new PacketCapture();

/*
        System.out.print(m_pcap.lookupDevices());
        Step 2:  Check for devices
        */

        m_device = m_pcap.findDevice();

        // Step 3:  Open Device for Capturing (requires root)
        m_pcap.open(m_device, true);

        // Step 4:  Add a BPF Filter (see tcpdump documentation)
        m_pcap.setFilter(FILTER, true);

        // Step 5:  Register a Listener for Raw Packets
        m_pcap.addRawPacketListener(new RawPacketHandler());

        // Step 6:  Capture Data (max. PACKET_COUNT packets)
        m_pcap.capture(PACKET_COUNT);
    }

    public static void main(String[] args) {
        try {
            EventPacketCapture eventPacketCapture= new EventPacketCapture();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
