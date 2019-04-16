package uz.codic.ahmadtea.data.network.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class EmployeeLocation {

    private double lat;
    private double lng;
    private long received_time;

    public EmployeeLocation(){

    }

    public EmployeeLocation(double lat, double lng, long received_time) {
        this.lat = lat;
        this.lng = lng;
        this.received_time = received_time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getReceived_time() {
        return received_time;
    }

    public void setReceived_time(long received_time) {
        this.received_time = received_time;
    }
}
