package uz.codic.ahmadtea.ui.orders.basketList;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;

public interface BasketMvpView extends MvpView {

    void onBasketListReady(List<BasketProduct> basketProducts);
}

