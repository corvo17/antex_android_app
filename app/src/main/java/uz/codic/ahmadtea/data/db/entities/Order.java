package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Order implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int pid;

    String visitId;
    String id;
    Integer id_payment_type;
    Integer id_mmd;
    String id_merchant;
    Long total_cost;
    Long total_cost_with_mmd;
    String notes;
    Integer id_filial;
    Integer id_price;
    String id_workspace;
    Integer id_warehouse;
    String status;
    String delivery_date;
    String idEmployee;
    @Ignore
    boolean orderComplete;

    @Override
    public String toString() {
        return "visitId "+ getVisitId() + "" +
                "\nid " + getId()+
                "\nid_payment_type " + getId_payment_type() +
                "\nid_mmd " + getId_mmd()+
                "\nid_merchant " + getId_merchant()+
                "\ntotal_cost " + getTotal_cost()+
                "\ntotal_cost_with_mmd " + getTotal_cost_with_mmd()+
                "\nnotes " + getNotes()+
                "\nid_filial " + getId_filial()+
                "\nid_price " + getId_price()+
                "\nstatus " + getStatus()+
                "\nid_workspace " + getId_workspace()+
                "\nid_workspace " + id_warehouse +
                "\ndelivery_date " +delivery_date+
                "\ndelivery_date " +isOrderComplete()+
                "\nidEmployee " +getIdEmployee();
    }
    public Order(){

    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setOrderComplete(boolean orderComplete) {
        this.orderComplete = orderComplete;
    }

    public boolean isOrderComplete() {
        return orderComplete;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitId() {
        return visitId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getId_payment_type() {
        return id_payment_type;
    }

    public void setId_payment_type(Integer id_payment_type) {
        this.id_payment_type = id_payment_type;
    }

    public Integer getId_mmd() {
        return id_mmd;
    }

    public void setId_mmd(Integer id_mmd) {
        this.id_mmd = id_mmd;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public Long getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Long total_cost) {
        this.total_cost = total_cost;
    }

    public Long getTotal_cost_with_mmd() {
        return total_cost_with_mmd;
    }

    public void setTotal_cost_with_mmd(Long total_cost_with_mmd) {
        this.total_cost_with_mmd = total_cost_with_mmd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getId_filial() {
        return id_filial;
    }

    public void setId_filial(Integer id_filial) {
        this.id_filial = id_filial;
    }

    public Integer getId_price() {
        return id_price;
    }

    public void setId_price(Integer id_price) {
        this.id_price = id_price;
    }

    public String getId_workspace() {
        return id_workspace;
    }

    public void setId_workspace(String id_workspace) {
        this.id_workspace = id_workspace;
    }

    public Integer getId_warehouse() {
        return id_warehouse;
    }

    public void setId_warehouse(Integer id_warehouse) {
        this.id_warehouse = id_warehouse;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
}

