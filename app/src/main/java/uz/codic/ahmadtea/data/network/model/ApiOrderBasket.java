package uz.codic.ahmadtea.data.network.model;

public class ApiOrderBasket {

    String product_id;
    int total_count;

    public ApiOrderBasket(){

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
