package uz.codic.ahmadtea.ui.report.basketList;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;

public interface BasketMvpView extends MvpView {

    void onBasketListReady(List<BasketProduct> basketProducts);
}

