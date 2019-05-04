package uz.codic.ahmadtea.data.db.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FileReportType {

    @PrimaryKey
    Integer id;

    String label;
    Integer status_id;
    String link_id;


    public FileReportType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    @Override
    public String toString() {
        return "FileReportType{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", status_id=" + status_id +
                ", link_id='" + link_id + '\'' +
                '}';
    }
}
