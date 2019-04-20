package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspacePaymentType {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer payment_type_id;
    String workspace_id;
    Integer status_id;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(Integer payment_type_id) {
        this.payment_type_id = payment_type_id;
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
        return "WorspacePaymentType{" +
                "i_id=" + pid +
                ", id_payment_type=" + payment_type_id +
                ", id_workspace='" + workspace_id + '\'' +
                ", status_id=" + status_id +
                '}';
    }
}
