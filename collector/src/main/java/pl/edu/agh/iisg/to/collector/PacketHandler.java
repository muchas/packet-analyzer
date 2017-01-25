package pl.edu.agh.iisg.to.collector;

import org.jnetpcap.JCaptureHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;


public class PacketHandler implements PcapPacketHandler {
    // network protocols
    Ethernet eth = new Ethernet();
    Arp arp = new Arp();
    Ip4 ip4 = new Ip4();
    Ip6 ip6 = new Ip6();
    Tcp tcp = new Tcp();
    Udp udp = new Udp();
    Http http = new Http();
    int i = 1;

    @Override
    public void nextPacket(PcapPacket pcapPacket, Object o) {
        Packet p = new Packet();

        PropertyValue pv = new PropertyValue(pcapPacket.getFrameNumber());
        p.set("number", pv);

        final JCaptureHeader header = pcapPacket.getCaptureHeader();
//        System.out.println(pcapPacket.getFrameNumber());
//        System.out.println(pcapPacket.getCaptureHeader().caplen());
        p.setProperty("length", pcapPacket.getCaptureHeader().caplen());

        if (pcapPacket.hasHeader(eth)) {
//            System.out.println("Ethernet");
//            System.out.println(FormatUtils.asString(pcapPacket.getHeader(eth).destination()));
//            System.out.println(FormatUtils.asString(pcapPacket.getHeader(eth).source()));
            p.setProperty("ethernet", pcapPacket.getHeader(eth).getHeaderLength());
            p.setProperty("dMAC", FormatUtils.asString(pcapPacket.getHeader(eth).destination()));
            p.setProperty("sMAC", FormatUtils.asString(pcapPacket.getHeader(eth).source()));
        }

        if (pcapPacket.hasHeader(arp)) {
//            System.out.println("Arp");
//            System.out.println(pcapPacket.getHeader(arp).operationEnum().toString());
//            System.out.println(pcapPacket.getHeader(arp).getHeaderLength());
//            System.out.println(FormatUtils.asString(pcapPacket.getHeader(arp).tha()));
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(arp).tpa()));
//            System.out.println(FormatUtils.asString(pcapPacket.getHeader(arp).sha()));
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(arp).spa()));

            p.setProperty("arpcode", pcapPacket.getHeader(arp).operationEnum().toString());
            p.setProperty("arp", pcapPacket.getHeader(arp).getHeaderLength());
            p.setProperty("arp_dMAC", FormatUtils.asString(pcapPacket.getHeader(arp).tha()));
            p.setProperty("dIP", FormatUtils.ip(pcapPacket.getHeader(arp).tpa()));
            p.setProperty("arp_sMAC", FormatUtils.asString(pcapPacket.getHeader(arp).sha()));
            p.setProperty("sIP", FormatUtils.ip(pcapPacket.getHeader(arp).spa()));
        }

        if (pcapPacket.hasHeader(ip4)) {
//            System.out.println("ip4");
//            System.out.println(pcapPacket.getHeader(ip4).version());
//            System.out.println(pcapPacket.getHeader(ip4).getHeaderLength());
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip4).source()));
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip4).destination()));

            p.setProperty("ipversion", pcapPacket.getHeader(ip4).version());
            p.setProperty("ip", pcapPacket.getHeader(ip4).getHeaderLength());
            p.setProperty("sIP", FormatUtils.ip(pcapPacket.getHeader(ip4).source()));
            p.setProperty("dIP", FormatUtils.ip(pcapPacket.getHeader(ip4).destination()));
        }


        if (pcapPacket.hasHeader(ip6)) {
//            System.out.println("ip6");
//            System.out.println(pcapPacket.getHeader(ip6).version());
//            System.out.println(pcapPacket.getHeader(ip6).getHeaderLength());
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip6).source()));
//            System.out.println(FormatUtils.ip(pcapPacket.getHeader(ip6).destination()));

            p.setProperty("ipversion", pcapPacket.getHeader(ip6).version());
            p.setProperty("ip", pcapPacket.getHeader(ip6).getHeaderLength());
            p.setProperty("sIP", FormatUtils.ip(pcapPacket.getHeader(ip6).source()));
            p.setProperty("dIP", FormatUtils.ip(pcapPacket.getHeader(ip6).destination()));
        }

        if (pcapPacket.hasHeader(tcp)) {
//            System.out.println("tcp");
//            System.out.println(pcapPacket.getHeader(tcp).getHeaderLength());
//            System.out.println(pcapPacket.getHeader(tcp).destination());
//            System.out.println(pcapPacket.getHeader(tcp).source());
//            System.out.println(FormatUtils.hexdump(pcapPacket.getHeader(tcp).getPayload()));

            p.setProperty("tcp", pcapPacket.getHeader(tcp).getHeaderLength());
            p.setProperty("dPort", pcapPacket.getHeader(tcp).destination());
            p.setProperty("sPort", pcapPacket.getHeader(tcp).source());
            p.setProperty("payload", FormatUtils.hexdump(pcapPacket.getHeader(tcp).getPayload()));
        }

        if (pcapPacket.hasHeader(udp)) {
//            System.out.println("udp");
//            System.out.println(pcapPacket.getHeader(udp).getHeaderLength());
//            System.out.println(pcapPacket.getHeader(udp).destination());
//            System.out.println(pcapPacket.getHeader(udp).source());
//            System.out.println(FormatUtils.hexdump(pcapPacket.getHeader(udp).getPayload()));

            p.setProperty("udp", pcapPacket.getHeader(udp).getHeaderLength());
            p.setProperty("dPort", pcapPacket.getHeader(udp).destination());
            p.setProperty("sPort", pcapPacket.getHeader(udp).source());
            p.setProperty("payload", FormatUtils.hexdump(pcapPacket.getHeader(udp).getPayload()));
        }

        if (pcapPacket.hasHeader(http)) {
//            System.out.println("http");
//            System.out.println(pcapPacket.getHeader(http).getHeaderLength());
//            System.out.println(pcapPacket.getHeader(http).getMessageType().toString());

            p.setProperty("http", pcapPacket.getHeader(http).getHeaderLength());
            p.setProperty("httpType", pcapPacket.getHeader(http).getMessageType().toString());
        }

//        System.out.println("*******************************************************");

//        PacketBuffer.getInstance().push(p);
    }

}