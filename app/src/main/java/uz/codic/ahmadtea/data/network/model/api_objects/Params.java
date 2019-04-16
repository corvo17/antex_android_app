package uz.codic.ahmadtea.data.network.model.api_objects;

import java.util.HashMap;
import java.util.List;

public class Params {

    List<HashMap<String, Integer>> collection_item_count;

    public Params() {
    }

    public List<HashMap<String, Integer>> getCollection_item_count() {
        return collection_item_count;
    }

    public void setCollection_item_count(List<HashMap<String, Integer>> collection_item_count) {
        this.collection_item_count = collection_item_count;
    }

    @Override
    public String toString() {
        return "Params{" +
                "collection_item_count=" + collection_item_count +
                '}';
    }
}
