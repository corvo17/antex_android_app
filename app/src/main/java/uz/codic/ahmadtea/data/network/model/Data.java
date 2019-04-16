package uz.codic.ahmadtea.data.network.model;

import java.util.List;

public class Data {
    int size;
    List<String> merchants_to_visit;

    public Data(){

    }

    public void setMerchants_to_visit(List<String> merchants_to_visit) {
        this.merchants_to_visit = merchants_to_visit;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public List<String> getMerchants_to_visit() {
        return merchants_to_visit;
    }
}
