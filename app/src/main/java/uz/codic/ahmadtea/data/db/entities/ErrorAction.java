package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;

@Entity
public class ErrorAction {

    @PrimaryKey(autoGenerate = true)
    Integer pid;
    String label;
    Long timestamp;
    Integer active_activity_id;

    public ErrorAction() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getActive_activity_id() {
        return active_activity_id;
    }

    public void setActive_activity_id(Integer active_activity_id) {
        this.active_activity_id = active_activity_id;
    }

    @Override
    public String toString() {
        return "ErrorAction{" +
                "pid=" + pid +
                ", label='" + label + '\'' +
                ", timestamp=" + timestamp +
                ", active_activity_id=" + active_activity_id +
                '}';
    }
}
