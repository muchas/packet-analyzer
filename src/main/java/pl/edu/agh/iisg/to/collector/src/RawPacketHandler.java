/**
 * Created by Maria on 2017-01-04.
 */
package src.main.java.pl.edu.agh.iisg.to.collector.src;

import net.sourceforge.jpcap.capture.RawPacketListener;
import net.sourceforge.jpcap.net.*;

public class RawPacketHandler implements RawPacketListener
{
    public void rawPacketArrived(RawPacket packet) {
        int i = 0;
        try {
            //Context context = new Context(new ConvertARP());
            System.out.println("arrived " + i + "packet");
            i++;

//            ICMPPacket icmpPacket = (ICMPPacket) packet;
//
//            String srcHost = icmpPacket.getSourceAddress();
//            String dstHost = icmpPacket.getDestinationAddress();
//
//            System.out.println("From <"+srcHost+"> to <"+dstHost+">");
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
}