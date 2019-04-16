package uz.codic.ahmadtea.data.network.model.api_objects;

import java.util.List;

public class ApiObeject {

    Meta meta;
    List<Payload> payload;

    public ApiObeject() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Payload> getPayload() {
        return payload;
    }

    public void setPayload(List<Payload> payload) {
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
