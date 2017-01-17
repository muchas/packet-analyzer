package pl.edu.agh.iisg.to.filter;

public class Packet {



    private String protocol;


    public Packet() {
        this.protocol = "TCP";
    }

    public Packet(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
