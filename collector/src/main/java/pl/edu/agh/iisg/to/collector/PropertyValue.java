package pl.edu.agh.iisg.to.collector;

public class PropertyValue {
    private PropertyType type;
    private Object value;

    PropertyValue(Object value) {
        this.value = value;
        this.type = PropertyType.createType(value);
    }

    String valueAsString() {
        return (String) value;
    }

    Integer valueAsInteger() {
        return (Integer) value;
    }

    boolean isString() {
        return PropertyType.STRING.equals(type);
    }

    boolean isInteger() {
        return PropertyType.INTEGER.equals(type);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }
}
