package uz.codic.ahmadtea.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Message {

    private String routingKey;

    Map<Object, Object> headers;

    Map<Object, Object> params;

    Object payload;

    public Map<Object, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<Object, Object> headers) {
        this.headers = headers;
    }

    public Map<Object, Object> getParams() {
        return params;
    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String toString() {
        return "Message{" +
                "routingKey='" + routingKey + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                ", payload='" + payload + '\'' +
                '}';
    }
//    @SerializedName("params")
//    RequestWithWorkspaceId params;
//
//    public Message(){
//
//    }
//
//    public RequestWithWorkspaceId getParams() {
//        return params;
//    }
//
//    public void setParams(RequestWithWorkspaceId params) {
//        this.params = params;
//    }
}
