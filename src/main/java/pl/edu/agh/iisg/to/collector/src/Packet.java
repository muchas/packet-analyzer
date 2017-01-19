package pl.edu.agh.iisg.to.collector.src;

import java.util.HashMap;

public class Packet {
    private HashMap<String, PropertyValue> packetProperties;

    public void Packet() {
        this.packetProperties = new HashMap<String, PropertyValue>();
    }

    public void setPacketProperties(HashMap<String, PropertyValue> packetProperties) {
        this.packetProperties = packetProperties;
    }

    public HashMap<String, PropertyValue> getPacketProperties() {
        return packetProperties;
    }

    public PropertyValue getProperty(String key) {
        return packetProperties.get(key);
    }

    public void setProperty(String key, Object o) {
        packetProperties.put(key, new PropertyValue(o));
    }

    public void set(String key, PropertyValue value) {
        packetProperties.put(key, value);
    }

}