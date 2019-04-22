package uz.codic.ahmadtea.data.network.model;

public class ApiVisit {

    String id;
    String merchant_id;
    String comment_id;
    String notes;
    long time_start;
    long time_end;
    double latitude;
    double longitude;
    Integer filial_id;

    public ApiVisit(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTime_start() {
        return time_start;
    }

    public void setTime_start(long time_start) {
        this.time_start = time_start;
    }

    public long getTime_end() {
        return time_end;
    }

    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getFilial_id() {
        return filial_id;
    }

    public void setFilial_id(Integer filial_id) {
        this.filial_id = filial_id;
    }
}
