package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspacePrice {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer price_id;
    String workspace_id;
    Integer status_id;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getPrice_id() {
        return price_id;
    }

    public void setPrice_id(Integer price_id) {
        this.price_id = price_id;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "WorkspacePrice{" +
                "i_id=" + pid +
                ", id_price=" + price_id +
                ", id_workspace='" + workspace_id + '\'' +
                ", status_id=" + status_id +
                '}';
    }
}
