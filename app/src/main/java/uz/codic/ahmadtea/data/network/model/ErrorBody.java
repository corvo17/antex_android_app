package uz.codic.ahmadtea.data.network.model;

import java.util.HashMap;
import java.util.List;

public class ErrorBody {

    String error_id;
    String API_version;
    String OS_version;
    String device_model;
    List<Integer> permissions_granted;
    List<Integer> permissions_denied;
    boolean active_internet_connection;
    Long timestamp;
    String error_type;
    String error_label;
    List<HashMap<String , String >> params;
    String error_log;
    String error_message;
    List<HashMap<String , String >> before_error;
    List<HashMap<String , String >> during_error;

    public ErrorBody() {
    }

    public String getAPI_version() {
        return API_version;
    }

    public void setAPI_version(String API_version) {
        this.API_version = API_version;
    }

    public String getOS_version() {
        return OS_version;
    }

    public void setOS_version(String OS_version) {
        this.OS_version = OS_version;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public List<Integer> getPermissions_granted() {
        return permissions_granted;
    }

    public void setPermissions_granted(List<Integer> permissions_granted) {
        this.permissions_granted = permissions_granted;
    }

    public List<Integer> getPermissions_denied() {
        return permissions_denied;
    }

    public void setPermissions_denied(List<Integer> permissions_denied) {
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

    public List<HashMap<String, String>> getParams() {
        return params;
    }

    public void setParams(List<HashMap<String, String>> params) {
        this.params = params;
    }

    public String getError_log() {
        return error_log;
    }

    public void setError_log(String error_log) {
        this.error_log = error_log;
    }

    public List<HashMap<String, String>> getBefore_error() {
        return before_error;
    }

    public void setBefore_error(List<HashMap<String, String>> before_error) {
        this.before_error = before_error;
    }

    public List<HashMap<String, String>> getDuring_error() {
        return during_error;
    }

    public void setDuring_error(List<HashMap<String, String>> during_error) {
        this.during_error = during_error;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getError_id() {
        return error_id;
    }

    public void setError_id(String error_id) {
        this.error_id = error_id;
    }

    @Override
    public String toString() {
        return "ErrorBody{" +
                "error_id='" + error_id + '\'' +
                ", API_version='" + API_version + '\'' +
                ", OS_version='" + OS_version + '\'' +
                ", device_model='" + device_model + '\'' +
                ", permissions_granted=" + permissions_granted +
                ", permissions_denied=" + permissions_denied +
                ", active_internet_connection=" + active_internet_connection +
                ", timestamp=" + timestamp +
                ", error_type='" + error_type + '\'' +
                ", error_label='" + error_label + '\'' +
                ", params=" + params +
                ", error_log='" + error_log + '\'' +
                ", error_message='" + error_message + '\'' +
                ", before_error=" + before_error +
                ", during_error=" + during_error +
                '}';
    }
}
