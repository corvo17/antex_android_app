package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspacePrice {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer id_price;
    String id_workspace;
    Integer id_status;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getId_price() {
        return id_price;
    }

    public void setId_price(Integer id_price) {
        this.id_price = id_price;
    }

    public String getId_workspace() {
        return id_workspace;
    }

    public void setId_workspace(String id_workspace) {
        this.id_workspace = id_workspace;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    @Override
    public String toString() {
        return "WorkspacePrice{" +
                "i_id=" + pid +
                ", id_price=" + id_price +
                ", id_workspace='" + id_workspace + '\'' +
                ", status_id=" + id_status +
                '}';
    }
}
