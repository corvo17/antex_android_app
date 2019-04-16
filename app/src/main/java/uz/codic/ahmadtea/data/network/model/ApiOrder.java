package uz.codic.ahmadtea.data.network.model;

public class ApiOrder {

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
    String delivery_date;


    public ApiOrder(){

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

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
}
