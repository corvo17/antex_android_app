package uz.codic.ahmadtea.data.db.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspaceMmd {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer id_mmd;
    String id_workspace;
    Integer id_status;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getId_mmd() {
        return id_mmd;
    }

    public void setId_mmd(Integer id_mmd) {
        this.id_mmd = id_mmd;
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
}
