package uz.codic.ahmadtea.data.network.model;

public class LocationResponse {
    long timestamp;
    String message;

    public LocationResponse(){

    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
