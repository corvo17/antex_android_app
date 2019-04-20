package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspaceMerchant {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    String workspace_id;
    String merchant_id;
    Integer status_id;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "WorkspaceMerchant{" +
                "i_id=" + pid +
                ", id_workspace='" + workspace_id + '\'' +
                ", id_merchant='" + merchant_id + '\'' +
                ", status_id=" + status_id +
                '}';
    }
}
