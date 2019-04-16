package uz.codic.ahmadtea.data.db.entities;

public class PhotoLabel {

    private int id;
    private String name;
    private int id_status;
    private String link_id;

    public PhotoLabel(int id, String name, int id_status, String link_id) {
        this.id = id;
        this.name = name;
        this.id_status = id_status;
        this.link_id = link_id;
    }

    public PhotoLabel() {
    }

    public PhotoLabel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }
}
