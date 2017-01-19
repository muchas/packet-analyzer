package pl.edu.agh.iisg.to.collector.src;

import org.jnetpcap.JCaptureHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.protocol.tcpip.*;


public class PacketHandler implements PcapPacketHandler {
    // network protocols
    Ethernet eth;
    Arp arp = new Arp();
    Icmp icmp = new Icmp();
    Ip4 ip4 = new Ip4();
    Ip6 ip6 = new Ip6();
    Tcp tcp = new Tcp();
    Udp udp = new Udp();
    Http http = new Http();
    int i = 1;

    @Override
    public void nextPacket(PcapPacket pcapPacket, Object o) {
        Packet p = new Packet();
        Ethernet eth = new Ethernet();
        Arp arp = new Arp();
        Ip4 ip4 = new Ip4();
        Ip6 ip6 = new Ip6();

        //PropertyValue pv = new PropertyValue(pcapPacket.getFrameNumber());
        //p.set("number", pv);

        final JCaptureHeader header = pcapPacket.getCaptureHeader();
        System.out.println(pcapPacket.getFrameNumber());
        System.out.println(pcapPacket.getCaptureHeader().caplen());

        if (pcapPacket.hasHeader(eth)) {
            System.out.println("Ethernet");
            System.out.println(FormatUtils.asString(pcapPacket.getHeader(eth).destination()));
            System.out.println(FormatUtils.asString(pcapPacket.getHeader(eth).source()));
        }

        if (pcapPacket.hasHeader(arp)) {
            System.out.println("Arp");
            System.out.println(pcapPacket.getHeader(arp).operationEnum().toString());
            System.out.println(pcapPacket.getHeader(arp).getHeaderLength());
            System.out.println(FormatUtils.asString(pcapPacket.getHeader(arp).tha()));
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(arp).tpa()));
            System.out.println(FormatUtils.asString(pcapPacket.getHeader(arp).sha()));
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(arp).spa()));
        }

        if (pcapPacket.hasHeader(ip4)) {
            System.out.println("ip4");
            System.out.println(pcapPacket.getHeader(ip4).version());
            System.out.println(pcapPacket.getHeader(ip4).getHeaderLength());
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip4).source()));
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip4).destination()));
        }


        if (pcapPacket.hasHeader(ip6)) {
            System.out.println("ip6");
            System.out.println(pcapPacket.getHeader(ip6).version());
            System.out.println(pcapPacket.getHeader(ip6).getHeaderLength());
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip6).source()));
            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip6).destination()));
        }

        if (pcapPacket.hasHeader(tcp)) {
            System.out.println("tcp");
            System.out.println(pcapPacket.getHeader(tcp).getHeaderLength());
            System.out.println(pcapPacket.getHeader(tcp).destination());
            System.out.println(pcapPacket.getHeader(tcp).source());
            System.out.println(FormatUtils.hexdump(pcapPacket.getHeader(tcp).getPayload()));
        }

        if (pcapPacket.hasHeader(udp)) {
            System.out.println("udp");
            System.out.println(pcapPacket.getHeader(udp).getHeaderLength());
            System.out.println(pcapPacket.getHeader(udp).destination());
            System.out.println(pcapPacket.getHeader(udp).source());
            System.out.println(FormatUtils.hexdump(pcapPacket.getHeader(udp).getPayload()));
        }

        if (pcapPacket.hasHeader(http)) {
            System.out.println("http");
            System.out.println(pcapPacket.getHeader(http).getHeaderLength());
            System.out.println(pcapPacket.getHeader(http).getMessageType().toString());
        }

        System.out.println("*******************************************************");

    }

}