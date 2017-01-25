package pl.edu.agh.iisg.to.collector;

/**
 * Created by Maria on 2017-01-15.
 */
public class Main {
    public static void main(String[] args) {
        EventPacketCapture cap = new EventPacketCapture();
        //cap.printDevicesList();
        //cap.setDevice(cap.getDevicesList().get(3));
        cap.start();
    }
}
