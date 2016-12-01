import java.io.IOException;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.*;
import jpcap.PacketReceiver;


public class PacketCapture {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        //Obtain the list of network interfaces
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        //for each network interface
        for (int i = 0; i < devices.length; i++) {
            //print out its name and description
            System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");

            //print out its datalink name and description
            System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

            //print out its MAC address
            System.out.print(" MAC address:");
            for (byte b : devices[i].mac_address)
                System.out.print(Integer.toHexString(b&0xff) + ":");
            System.out.println();

            //print out its IP address, subnet mask and broadcast address
            for (NetworkInterfaceAddress a : devices[i].addresses)
                System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
        }

//        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        int index= 1;  // set index of the interface that you want to open.

        //Open an interface with openDevice(NetworkInterface intrface, int snaplen, boolean promics, int to_ms)
        try {
            JpcapCaptor captor=JpcapCaptor.openDevice(devices[index], 65535, false, 20);

            for(int i=0;i<10;i++){
                //capture a single packet and print it out
                System.out.println(captor.getPacket());
            }

            captor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PacketPrinter implements PacketReceiver {
    //this method is called every time Jpcap captures a packet
    public void receivePacket(Packet packet) {
        //just print out a captured packet
        System.out.println(packet);
    }
}