package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ActiveStock {

    @PrimaryKey
    int id;

    String workspace_id;
    String product_id;
    String product_label;
    Integer warehouse_id;
    String warehouse_label;
    Integer total_active_count;
    Integer total_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_label() {
        return product_label;
    }

    public void setProduct_label(String product_label) {
        this.product_label = product_label;
    }

    public Integer getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(Integer warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWarehouse_label() {
        return warehouse_label;
    }

    public void setWarehouse_label(String warehouse_label) {
        this.warehouse_label = warehouse_label;
    }

    public Integer getTotal_active_count() {
        return total_active_count;
    }

    public void setTotal_active_count(Integer total_active_count) {
        this.total_active_count = total_active_count;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "ActiveStock{" +
                "id=" + id +
                ", workspace_id='" + workspace_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_label='" + product_label + '\'' +
                ", warehouse_id=" + warehouse_id +
                ", warehouse_label='" + warehouse_label + '\'' +
                ", total_active_count=" + total_active_count +
                ", total_count=" + total_count +
                '}';
    }
}
