package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class OrderBasket {

    @PrimaryKey(autoGenerate = true)
    int pid;

    String orderId;
    String id_product;
    int count;
    int count_boxes;
    int total_count;
    Integer price_value;
    String status;

    @Ignore
    int price;

    @Override
    public String toString() {
        return "orderId "+ getOrderId() + "" +
                "\nidProduct " + getId_product()+
                "\ncount " + getCount() +
                "\ncount_boxes " + getCount_boxes()+
                "\ntotal_count " + getTotal_count()+
                "\nstatus " + getStatus()+
                "\nprice " + getPrice()+
                "\nprice_value " + price_value ;
    }

    public OrderBasket(){

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount_boxes() {
        return count_boxes;
    }

    public void setCount_boxes(int count_boxes) {
        this.count_boxes = count_boxes;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public Integer getPrice_value() {
        return price_value;
    }

    public void setPrice_value(Integer price_value) {
        this.price_value = price_value;
    }
}
