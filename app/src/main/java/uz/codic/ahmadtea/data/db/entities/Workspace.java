package uz.codic.ahmadtea.data.db.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Workspace implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "status_id")
    private int status_id;

    private int filial_id;

    private int warehouse_id;

    public Workspace(){

    }

    public void setFilial_id(int filial_id) {
        this.filial_id = filial_id;
    }

    public int getFilial_id() {
        return filial_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String toString() {
        return "Workspace{" +
                "i_id=" + pid +
                ", id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", address='" + filial_id + '\'' +
                ", latitude=" + warehouse_id +
                ", longitude=" + status_id +
                '}';
    }
}
