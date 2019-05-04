package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ActivityError {

    @PrimaryKey(autoGenerate = true)
    Integer pid;

    Integer active_activity_id;
    Integer error_info_id;

    public ActivityError() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getActive_activity_id() {
        return active_activity_id;
    }

    public void setActive_activity_id(Integer active_activity_id) {
        this.active_activity_id = active_activity_id;
    }

    public Integer getError_info_id() {
        return error_info_id;
    }

    public void setError_info_id(Integer error_info_id) {
        this.error_info_id = error_info_id;
    }

    @Override
    public String toString() {
        return "ActivityError{" +
                "pid=" + pid +
                ", active_activity_id=" + active_activity_id +
                ", error_info_id=" + error_info_id +
                '}';
    }
}
