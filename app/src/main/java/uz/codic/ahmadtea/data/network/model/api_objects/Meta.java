package uz.codic.ahmadtea.data.network.model.api_objects;

public class Meta {

    Integer status;
    String message;
    Node node;
    Params params;
    Integer payload_type_id;
    String payload_type_label;
    Integer payload_count;
    String error;

    public Meta() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Integer getPayload_type_id() {
        return payload_type_id;
    }

    public void setPayload_type_id(Integer payload_type_id) {
        this.payload_type_id = payload_type_id;
    }

    public String getPayload_type_label() {
        return payload_type_label;
    }

    public void setPayload_type_label(String payload_type_label) {
        this.payload_type_label = payload_type_label;
    }

    public Integer getPayload_count() {
        return payload_count;
    }

    public void setPayload_count(Integer payload_count) {
        this.payload_count = payload_count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", node=" + node +
                ", params=" + params +
                ", payload_type_id=" + payload_type_id +
                ", payload_type_label='" + payload_type_label + '\'' +
                ", payload_count=" + payload_count +
                ", error='" + error + '\'' +
                '}';
    }
}
