package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspacePaymentType {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    Integer id_payment_type;
    String id_workspace;
    Integer id_status;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getId_payment_type() {
        return id_payment_type;
    }

    public void setId_payment_type(Integer id_payment_type) {
        this.id_payment_type = id_payment_type;
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
        return "WorspacePaymentType{" +
                "i_id=" + pid +
                ", id_payment_type=" + id_payment_type +
                ", id_workspace='" + id_workspace + '\'' +
                ", status_id=" + id_status +
                '}';
    }
}
