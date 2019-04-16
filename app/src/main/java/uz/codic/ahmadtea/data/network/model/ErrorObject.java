package uz.codic.ahmadtea.data.network.model;

import java.util.HashMap;
import java.util.List;

public class ErrorObject {

    String id;
    Integer app_client_type;
    ErrorBody body;

    public ErrorObject() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getApp_client_type() {
        return app_client_type;
    }

    public void setApp_client_type(Integer app_client_type) {
        this.app_client_type = app_client_type;
    }

    public ErrorBody getBody() {
        return body;
    }

    public void setBody(ErrorBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ErrorObject{" +
                "id='" + id + '\'' +
                ", app_client_type=" + app_client_type +
                ", body=" + body +
                '}';
    }
}
