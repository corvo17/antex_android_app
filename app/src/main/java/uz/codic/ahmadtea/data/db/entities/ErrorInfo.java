package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;
import java.util.UUID;

@Entity
public class ErrorInfo {

    @PrimaryKey(autoGenerate = true)
    Integer pid;

    String  id;
    String api_version;
    String os_version;
    String device_model;
    String permissions_granted;
    String permissions_denied;
    boolean active_internet_connection;
    Long timestamp;
    String error_type;
    String error_label;
    String error_message;
    String erro_log;
    boolean isSent;


    public ErrorInfo() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getPermissions_granted() {
        return permissions_granted;
    }

    public void setPermissions_granted(String permissions_granted) {
        this.permissions_granted = permissions_granted;
    }

    public String getPermissions_denied() {
        return permissions_denied;
    }

    public void setPermissions_denied(String permissions_denied) {
        this.permissions_denied = permissions_denied;
    }

    public boolean isActive_internet_connection() {
        return active_internet_connection;
    }

    public void setActive_internet_connection(boolean active_internet_connection) {
        this.active_internet_connection = active_internet_connection;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public String getError_label() {
        return error_label;
    }

    public void setError_label(String error_label) {
        this.error_label = error_label;
    }

    public String getErro_log() {
        return erro_log;
    }

    public void setErro_log(String erro_log) {
        this.erro_log = erro_log;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "pid=" + pid +
                ", id=" + id +
                ", api_version='" + api_version + '\'' +
                ", os_version='" + os_version + '\'' +
                ", device_model='" + device_model + '\'' +
                ", permissions_granted='" + permissions_granted + '\'' +
                ", permissions_denied='" + permissions_denied + '\'' +
                ", active_internet_connection=" + active_internet_connection +
                ", timestamp=" + timestamp +
                ", error_type='" + error_type + '\'' +
                ", error_label='" + error_label + '\'' +
                ", error_message='" + error_message + '\'' +
                ", erro_log='" + erro_log + '\'' +
                ", isSent=" + isSent +
                '}';
    }
}
