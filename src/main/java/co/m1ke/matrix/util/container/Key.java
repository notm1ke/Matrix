package co.m1ke.matrix.util.container;

import org.json.JSONObject;

public class Key {

    public static final Key TRUE = new Key(true);
    public static final Key FALSE = new Key(false);

    private String value;

    public Key(String value) {
        this.value = value;
    }

    public Key(int value) {
        this.value = value + "";
    }

    public Key(long value) {
        this.value = value + "";
    }

    public Key(boolean value) {
        this.value = value + "";
    }

    public Key(byte value) {
        this.value = value + "";
    }

    public Key(JSONObject value) {
        this.value = value.toString();
    }

    public static Key of(String s) {
        return new Key(s);
    }

    public int asInt() {
        return Integer.parseInt(value);
    }

    public String asString() {
        return value;
    }

    public double asDouble() {
        return Double.parseDouble(value);
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(value);
    }

    public float asFloat() {
        return Float.parseFloat(value);
    }

    public long asLong() {
        return Long.parseLong(value);
    }

    public byte asByte() {
        return Byte.parseByte(value);
    }

    public JSONObject asJson() {
        return new JSONObject(value);
    }

    @Override
    public String toString() {
        return this.asString();
    }

}
