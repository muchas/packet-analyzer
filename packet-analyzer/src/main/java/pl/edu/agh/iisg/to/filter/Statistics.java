package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.collector.Packet;

import java.util.HashMap;
import java.util.Map;

public class Statistics {

    private HashMap<String, Integer> values;

    private static Statistics instance = null;

    public static Statistics getInstance() {
        if(instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    private Statistics() {
        values = new HashMap<>();

        values.put("averageLength", 0);
        values.put("maxLength", 0);
        values.put("minLength", 200000000);
        values.put("packetCount", 0);
        values.put("packetSizeSum", 0);
    }

    public void update(Packet packet) {

        int length = (int) packet.getProperty("length").getValue();

        values.put("packetCount", values.get("packetCount") + 1);
        values.put("packetSizeSum", values.get("packetSizeSum") + length);
        values.put("averageLength", values.get("packetSizeSum") / values.get("packetCount"));
        values.put("maxLength", Math.max(values.get("maxLength"), length));
        values.put("minLength", Math.min(values.get("minLength"), length));
    }

    public Map<String, Integer> get() {
        return values;
    }
}
