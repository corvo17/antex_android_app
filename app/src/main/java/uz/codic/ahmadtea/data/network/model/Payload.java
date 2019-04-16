package uz.codic.ahmadtea.data.network.model;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;

public class Payload {
    ApiVisit visit;
    ApiOrder order;
    List<ApiOrderBasket> order_basket;

    public Payload(){}

    public Payload(ApiVisit visit, ApiOrder order, List<ApiOrderBasket> order_basket) {
        this.visit = visit;
        this.order = order;
        this.order_basket = order_basket;
    }

    public ApiVisit getVisit() {
        return visit;
    }

    public void setVisit(ApiVisit visit) {
        this.visit = visit;
    }

    public ApiOrder getOrder() {
        return order;
    }

    public void setOrder(ApiOrder order) {
        this.order = order;
    }

    public List<ApiOrderBasket> getOrder_basket() {
        return order_basket;
    }

    public void setOrder_basket(List<ApiOrderBasket> order_basket) {
        this.order_basket = order_basket;
    }
}
