package uz.codic.ahmadtea.ui.report.adapter;

import android.arch.persistence.room.Embedded;

import java.io.Serializable;

import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Order;

public class OrderedList implements Serializable {
    @Embedded
    Order order;

    @Embedded(prefix = "m_")
    Merchant merchant;

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderedList{" +
                "order=" + order +
                ", merchant=" + merchant +
                '}';
    }
}
