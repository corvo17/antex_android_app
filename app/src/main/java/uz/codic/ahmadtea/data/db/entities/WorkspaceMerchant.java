package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspaceMerchant {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    String id_workspace;
    String id_merchant;
    Integer id_status;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getId_workspace() {
        return id_workspace;
    }

    public void setId_workspace(String id_workspace) {
        this.id_workspace = id_workspace;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    @Override
    public String toString() {
        return "WorkspaceMerchant{" +
                "i_id=" + pid +
                ", id_workspace='" + id_workspace + '\'' +
                ", id_merchant='" + id_merchant + '\'' +
                ", status_id=" + id_status +
                '}';
    }
}
