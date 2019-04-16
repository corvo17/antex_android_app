package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Merchant implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int pid;

    String id;
    String label;
    String inn;
    String phone;
    String address;
    Long current_balance;
    Double latitude;
    Double longitude;
    Integer status_id;
    String contact_person;

    public Merchant() {

    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
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

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "pid=" + pid +
                ", id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", inn='" + inn + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", current_balance=" + current_balance +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status_id=" + status_id +
                ", contact_person='" + contact_person + '\'' +
                '}';
    }
}
