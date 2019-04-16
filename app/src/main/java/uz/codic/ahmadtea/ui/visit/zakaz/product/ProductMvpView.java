package uz.codic.ahmadtea.ui.visit.zakaz.product;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface ProductMvpView extends MvpView {

    void onProductListReady(List<ProductAndProductPrice> products);
}
