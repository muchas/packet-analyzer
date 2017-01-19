package pl.edu.agh.iisg.to.collector;

public enum PropertyType {
    STRING, INTEGER, UNKNOWN;

    public static PropertyType createType(Object object) {
        if (object != null) {
            if (String.class.equals(object.getClass())) {
                return STRING;
            }
            if (Integer.class.equals(object.getClass())) {
                return INTEGER;
            }
        }
        return UNKNOWN;
    }


}
