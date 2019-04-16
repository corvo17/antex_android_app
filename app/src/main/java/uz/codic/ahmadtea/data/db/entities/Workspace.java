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

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "id_status")
    private int id_status;

    private int id_filial;

    private int id_warehouse;

    public Workspace(){

    }

    public void setId_filial(int id_filial) {
        this.id_filial = id_filial;
    }

    public int getId_filial() {
        return id_filial;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_warehouse() {
        return id_warehouse;
    }

    public void setId_warehouse(int id_warehouse) {
        this.id_warehouse = id_warehouse;
    }

    public String toString() {
        return "Workspace{" +
                "i_id=" + pid +
                ", id='" + id + '\'' +
                ", label='" + name + '\'' +
                ", address='" + id_filial + '\'' +
                ", latitude=" + id_warehouse +
                ", longitude=" + id_status +
                '}';
    }
}
