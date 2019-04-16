package uz.codic.ahmadtea.data.network.model;

public class Send {
    Payload payload;

    public Send(){

    }

    public Send(Payload payload) {
        this.payload = payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return payload;
    }
}
