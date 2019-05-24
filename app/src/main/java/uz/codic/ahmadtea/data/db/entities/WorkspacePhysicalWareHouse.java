package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorkspacePhysicalWareHouse {

    @PrimaryKey
    Integer id;
    Integer warehouse_id;
    String workspace_id;
    String priority;

    public WorkspacePhysicalWareHouse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(Integer warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "WorkspacePhysicalWareHouse{" +
                "id=" + id +
                ", warehouse_id=" + warehouse_id +
                ", workspace_id='" + workspace_id + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
