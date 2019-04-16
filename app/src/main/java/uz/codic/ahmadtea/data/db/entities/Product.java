package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    long pid;
    String id;
    String label;
    String short_name;
    String product_value_id;
    int value;
    int count_in_box;
    int measurement_id;
    String barcode;
    int status_id;
    int default_serial_id;
    int remaining_amount;
    String link_id;


    public Product() {

    }

    public int getMeasurement_id() {
        return measurement_id;
    }

    public void setMeasurement_id(int measurement_id) {
        this.measurement_id = measurement_id;
    }

    public int getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(int remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getCount_in_box() {
        return count_in_box;
    }

    public void setCount_in_box(int count_in_box) {
        this.count_in_box = count_in_box;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDefault_serial_id() {
        return default_serial_id;
    }

    public void setDefault_serial_id(int default_serial_id) {
        this.default_serial_id = default_serial_id;
    }

    public String getProduct_value_id() {
        return product_value_id;
    }

    public void setProduct_value_id(String product_value_id) {
        this.product_value_id = product_value_id;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", short_name='" + short_name + '\'' +
                ", product_value_id='" + product_value_id + '\'' +
                ", value=" + value +
                ", count_in_box=" + count_in_box +
                ", measurement_id=" + measurement_id +
                ", barcode='" + barcode + '\'' +
                ", status_id=" + status_id +
                ", default_serial_id=" + default_serial_id +
                ", remaining_amount=" + remaining_amount +
                ", link_id='" + link_id + '\'' +
                '}';
    }
}
