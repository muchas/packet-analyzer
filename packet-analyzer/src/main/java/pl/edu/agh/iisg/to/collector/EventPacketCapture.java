package pl.edu.agh.iisg.to.collector;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.jnetpcap.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;

public class EventPacketCapture {

    private PcapIf device;
    private Pcap pcap;
    private List<PcapIf> alldevs;
    private StringBuilder errbuf;

    public void start() {
        for(PcapIf device: alldevs) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.printf(device.getName());
                    JBuffer buf = new JBuffer(JMemory.POINTER);
                    int snaplen = 64 * 1024;           // Capture all packets, no trucation
                    int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
                    int timeout = 10 * 1000;           // 10 seconds in millis
                    pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

                    if (pcap == null) {
                        System.err.printf("Error while opening device for capture: "
                                + errbuf.toString());
                        return;
                    }

                    JBufferHandler<String> handler = new JBufferHandler<String>() {
                        public void nextPacket(PcapHeader header, JBuffer buffer, String user) {
                            System.out.println("size of packet is=" + buffer.size());
                        }
                    };

                    pcap.loop(Pcap.LOOP_INFINITE, new PacketHandler(), "jnetPcap");
                    pcap.close();
                }
            }).start();
        }
    }

    public void stop() {
        pcap.breakloop();
        pcap.close();
    }

    public List<PcapIf> getDevicesList() {
        return alldevs;
    }

    public void printDevicesList() {
        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : this.alldevs) {
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), device
                    .getDescription());
        }
    }

    public void setDevice(PcapIf device) {
        this.device = device;
    }

    public EventPacketCapture() {
        this.alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        this.errbuf = new StringBuilder();

        int r = Pcap.findAllDevs(this.alldevs, this.errbuf);
        if (r == Pcap.NOT_OK || this.alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
            return;
        }
    }
}