package com.util.datastore.model;

import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Data {

    private Map<String, java.lang.Object> value;

    private long expiryTimeStamp = 0L;

    public Data() {
        super();
    }

    public Data(java.lang.Object value, long expirySeconds) {

        this.value = ((JSONObject) value).toMap();
        if (expirySeconds > 0) {
            this.expiryTimeStamp = System.currentTimeMillis() + (expirySeconds * 1000);
        }
    }

    /**
     * This method is used to return the stored value in Json format.
     *
     * @return JSONObject
     */
    public Optional<JSONObject> getValue() {

        if (Optional.ofNullable(this.value).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new JSONObject(this.value));
    }

    /**
     * This method is used to check whether the given timeStamp is expired.
     *
     * @return boolean.
     */
    public boolean isExpired() {
        return this.expiryTimeStamp > 0L && this.expiryTimeStamp < System.currentTimeMillis();
    }

    public void setValue(Map<String, java.lang.Object> value) {
        this.value = value;
    }

    public long getExpiryTimeStamp() {
        return expiryTimeStamp;
    }

    public void setExpiryTimeStamp(long expiryTimeStamp) {
        this.expiryTimeStamp = expiryTimeStamp;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return expiryTimeStamp == data.expiryTimeStamp &&
                Objects.equals(value, data.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, expiryTimeStamp);
    }

    @Override
    public String toString() {
        return "Data{" +
                "value=" + value +
                ", expiryTimeStamp=" + expiryTimeStamp +
                '}';
    }
}
