package uz.codic.ahmadtea.data.network.model;

public class Merchant {

    String id;
    String label;
    String address;
    Double latitude;
    Double longitude;
    String inn;
    String phone;
    Long current_balance;
    Integer status_id;
    String contact_person;


    public Merchant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(Long current_balance) {
        this.current_balance = current_balance;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", inn='" + inn + '\'' +
                ", phone='" + phone + '\'' +
                ", current_balance=" + current_balance +
                ", status_id=" + status_id +
                ", contact_person='" + contact_person + '\'' +
                '}';
    }
}
