package uz.codic.ahmadtea.ui.synchronisation.setOrder;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface SetOrderMvpView extends MvpView {

    void resultOrderBaskets(List<OrderBasket> orderBaskets);

    void resultProductItems(ProductAndProductPrice productItems);

    void back();
}
