package uz.codic.ahmadtea.data.network.model;

public class ApiOrder {

    String id;
    Integer payment_type_id;
    Integer mmd_id;
    String merchant_id;
    Long total_cost;
    Long total_cost_with_mmd;
    String note;
//    Integer filial_id;
    Integer price_id;
    String workspace_id;
    String delivery_date;
    String visit_id;
    Integer status_id;


    public ApiOrder(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(Integer payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public Integer getMmd_id() {
        return mmd_id;
    }

    public void setMmd_id(Integer mmd_id) {
        this.mmd_id = mmd_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

//    public Integer getFilial_id() {
//        return filial_id;
//    }

//    public void setFilial_id(Integer filial_id) {
//        this.filial_id = filial_id;
//    }

    public Integer getPrice_id() {
        return price_id;
    }

    public void setPrice_id(Integer price_id) {
        this.price_id = price_id;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }
}
