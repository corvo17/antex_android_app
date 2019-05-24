package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PhysicalWareHouse {

    @PrimaryKey
    Integer id;

    String label;
    String subject_id;
    String subject_label;
    String warehouse_grouo_id;
    String warehouse_grouo_label;
    String filial_id;
    String filial_label;
    Integer status_id;
    String status_label;
    String link_id;
    String node;
    boolean is_physical;
    String parent_id;

    @Ignore
    boolean isChecked;




    public PhysicalWareHouse() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_label() {
        return subject_label;
    }

    public void setSubject_label(String subject_label) {
        this.subject_label = subject_label;
    }

    public String getWarehouse_grouo_id() {
        return warehouse_grouo_id;
    }

    public void setWarehouse_grouo_id(String warehouse_grouo_id) {
        this.warehouse_grouo_id = warehouse_grouo_id;
    }

    public String getWarehouse_grouo_label() {
        return warehouse_grouo_label;
    }

    public void setWarehouse_grouo_label(String warehouse_grouo_label) {
        this.warehouse_grouo_label = warehouse_grouo_label;
    }

    public String getFilial_id() {
        return filial_id;
    }

    public void setFilial_id(String filial_id) {
        this.filial_id = filial_id;
    }

    public String getFilial_label() {
        return filial_label;
    }

    public void setFilial_label(String filial_label) {
        this.filial_label = filial_label;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String  getStatus_label() {
        return status_label;
    }

    public void setStatus_label(String status_label) {
        this.status_label = status_label;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public boolean isIs_physical() {
        return is_physical;
    }

    public void setIs_physical(boolean is_physical) {
        this.is_physical = is_physical;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "PhysicalWareHouse{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", subject_label='" + subject_label + '\'' +
                ", warehouse_grouo_id='" + warehouse_grouo_id + '\'' +
                ", warehouse_grouo_label='" + warehouse_grouo_label + '\'' +
                ", filial_id='" + filial_id + '\'' +
                ", filial_label='" + filial_label + '\'' +
                ", status_id=" + status_id +
                ", status_label=" + status_label +
                ", link_id='" + link_id + '\'' +
                ", node='" + node + '\'' +
                ", is_physical=" + is_physical +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
