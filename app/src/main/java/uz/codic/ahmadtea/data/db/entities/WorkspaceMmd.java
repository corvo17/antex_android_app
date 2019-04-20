package uz.codic.ahmadtea.data.db.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspaceMmd {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer mmd_id;
    String workspace_id;
    Integer status_id;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getMmd_id() {
        return mmd_id;
    }

    public void setMmd_id(Integer mmd_id) {
        this.mmd_id = mmd_id;
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
}
