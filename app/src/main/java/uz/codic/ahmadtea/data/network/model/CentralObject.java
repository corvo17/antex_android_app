package uz.codic.ahmadtea.data.network.model;

import java.util.HashMap;

public class CentralObject {

    HashMap<String, String > meta;
    HashMap<String, String > payload;

    public CentralObject() {
    }

    public HashMap<String, String> getMeta() {
        return meta;
    }

    public void setMeta(HashMap<String, String> meta) {
        this.meta = meta;
    }

    public HashMap<String, String> getPayload() {
        return payload;
    }

    public void setPayload(HashMap<String, String> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "CentralObject{" +
                "meta=" + meta +
                ", payload=" + payload +
                '}';
    }
}
