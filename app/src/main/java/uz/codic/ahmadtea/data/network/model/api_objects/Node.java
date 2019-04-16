package uz.codic.ahmadtea.data.network.model.api_objects;

public class Node {

    Integer node_id;
    String node_label;
    Integer refernces_count;
    Integer anchors_count;
    String references;
    String anchors;

    public Node() {

    }

    public Integer getNode_id() {
        return node_id;
    }

    public void setNode_id(Integer node_id) {
        this.node_id = node_id;
    }

    public String getNode_label() {
        return node_label;
    }

    public void setNode_label(String node_label) {
        this.node_label = node_label;
    }

    public Integer getRefernces_count() {
        return refernces_count;
    }

    public void setRefernces_count(Integer refernces_count) {
        this.refernces_count = refernces_count;
    }

    public Integer getAnchors_count() {
        return anchors_count;
    }

    public void setAnchors_count(Integer anchors_count) {
        this.anchors_count = anchors_count;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getAnchors() {
        return anchors;
    }

    public void setAnchors(String anchors) {
        this.anchors = anchors;
    }

    @Override
    public String toString() {
        return "Node{" +
                "node_id=" + node_id +
                ", node_label='" + node_label + '\'' +
                ", refernces_count=" + refernces_count +
                ", anchors_count=" + anchors_count +
                ", references='" + references + '\'' +
                ", anchors='" + anchors + '\'' +
                '}';
    }
}
