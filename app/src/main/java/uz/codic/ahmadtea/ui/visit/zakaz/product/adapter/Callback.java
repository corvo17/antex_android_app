package uz.codic.ahmadtea.ui.visit.zakaz.product.adapter;

import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;

public interface Callback {

    void onProductClick(ProductAndProductPrice products, int position);

    boolean isOrderBasket(String id_product);

    void changedPosition(int position);

    void changedBoxOrCount(int type, int position, String id_product);
}
