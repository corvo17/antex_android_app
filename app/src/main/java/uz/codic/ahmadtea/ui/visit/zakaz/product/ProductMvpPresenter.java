package uz.codic.ahmadtea.ui.visit.zakaz.product;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface ProductMvpPresenter<V extends ProductMvpView> extends MvpPresenter<V> {

    void reqeustProductList(int priceId, String workspace_id, int warehouse_id);



}
