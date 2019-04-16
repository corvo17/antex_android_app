package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Visit {

    @PrimaryKey(autoGenerate = true)
    int pid;

    String id;
    String id_merchant;
    String id_comment;
    String notes;
    long time_start;
    long time_end;
    double latitude;
    double longitude;
    String id_workspace;
    Integer id_filial;
    String status;
    String id_employee;
    String date;

    public Visit() {

    }

    @Override
    public String toString() {
        return "Visit{" +
                "i_id=" + pid +
                ", id='" + id + '\'' +
                ", id_merchant='" + id_merchant + '\'' +
                ", id_comment='" + id_comment + '\'' +
                ", notes='" + notes + '\'' +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", id_workspace='" + id_workspace + '\'' +
                ", id_filial=" + id_filial +
                ", status='" + status + '\'' +
                ", id_employee='" + id_employee + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTime_start() {
        return time_start;
    }

    public void setTime_start(long time_start) {
        this.time_start = time_start;
    }

    public long getTime_end() {
        return time_end;
    }

    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getId_filial() {
        return id_filial;
    }

    public void setId_filial(Integer id_filial) {
        this.id_filial = id_filial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_workspace() {
        return id_workspace;
    }

    public void setId_workspace(String id_workspace) {
        this.id_workspace = id_workspace;
    }
}
