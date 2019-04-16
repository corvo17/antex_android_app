package uz.codic.ahmadtea.data.network.model.api_objects;

import java.util.List;

public class ApiObeject<T> {

    Meta meta;
    List<T> payload;

    public ApiObeject() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<T> getPayload() {
        return payload;
    }

    public void setPayload(List<T> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApiObeject{" +
                "meta=" + meta +
                ", payload=" + payload +
                '}';
    }
}
