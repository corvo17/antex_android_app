package uz.codic.ahmadtea.data.network.model;

public class ApiOrderBasket {

    String id_product;
    int total_count;

    public ApiOrderBasket(){

    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
