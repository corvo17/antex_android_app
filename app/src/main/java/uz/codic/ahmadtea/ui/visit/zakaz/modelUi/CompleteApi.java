package uz.codic.ahmadtea.ui.visit.zakaz.modelUi;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;

public class CompleteApi implements Serializable {
    Visit visitObject;
    Order orderObject;
    List<OrderBasket> orderBasketList;

    public Visit getVisitObject() {
        return visitObject;
    }

    public void setVisitObject(Visit visitObject) {
        this.visitObject = visitObject;
    }

    public Order getOrderObject() {
        return orderObject;
    }

    public void setOrderObject(Order orderObject) {
        this.orderObject = orderObject;
    }

    public List<OrderBasket> getOrderBasketList() {
        return orderBasketList;
    }

    public void setOrderBasketList(List<OrderBasket> orderBasketList) {
        this.orderBasketList = orderBasketList;
    }
}
