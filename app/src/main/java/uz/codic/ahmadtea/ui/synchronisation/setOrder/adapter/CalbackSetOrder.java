package uz.codic.ahmadtea.ui.synchronisation.setOrder.adapter;

import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;

public interface CalbackSetOrder {

    void itemClick(ProductAndProductPrice products, int position);
}
