package uz.codic.ahmadtea.data.network.model.api_objects;

import java.util.HashMap;
import java.util.UUID;

public class Meta {

    Integer status;
    String message;
    Node node;
    Params params;
    Integer payload_type_id;
    String payload_type_label;
    Integer payload_count;
    Error error;

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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
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

    public class Error{

        UUID error_id;
        String error_label;
        Integer error_type_id;
        String error_type_label;
        HashMap<String , String > params;

        public Error() {
        }

        public UUID getError_id() {
            return error_id;
        }

        public void setError_id(UUID error_id) {
            this.error_id = error_id;
        }

        public String getError_label() {
            return error_label;
        }

        public void setError_label(String error_label) {
            this.error_label = error_label;
        }

        public Integer getError_type_id() {
            return error_type_id;
        }

        public void setError_type_id(Integer error_type_id) {
            this.error_type_id = error_type_id;
        }

        public String getError_type_label() {
            return error_type_label;
        }

        public void setError_type_label(String error_type_label) {
            this.error_type_label = error_type_label;
        }

        public HashMap<String, String> getParams() {
            return params;
        }

        public void setParams(HashMap<String, String> params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "Error{" +
                    "error_id=" + error_id +
                    ", error_label='" + error_label + '\'' +
                    ", error_type_id=" + error_type_id +
                    ", error_type_label='" + error_type_label + '\'' +
                    ", params=" + params +
                    '}';
        }
    }
}
