package uz.codic.ahmadtea.ui.report.basketList;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;

public interface BasketMvpView extends MvpView {

    void onBasketListReady(List<BasketProduct> basketProducts);
    InfoAction getInfoAction();
    void goBack();
    void onCompleteObjectReady(CompleteObject order);
}

