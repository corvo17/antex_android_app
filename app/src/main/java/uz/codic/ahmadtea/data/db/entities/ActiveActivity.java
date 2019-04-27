package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ActiveActivity {

    @PrimaryKey(autoGenerate = true)
    Integer pid;

    String label;
    Long timestamp;
    Integer after_activity_id;

    public ActiveActivity() {
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

    public Integer getAfter_activity_id() {
        return after_activity_id;
    }

    public void setAfter_activity_id(Integer after_activity_id) {
        this.after_activity_id = after_activity_id;
    }

    @Override
    public String toString() {
        return "ActiveActivity{" +
                "pid=" + pid +
                ", label='" + label + '\'' +
                ", timestamp=" + timestamp +
                ", after_activity_id=" + after_activity_id +
                '}';
    }
}
