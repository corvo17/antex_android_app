package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.arch.persistence.room.Embedded;

import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;

public class BasketProduct {

    @Embedded
    OrderBasket orderBasket;

    @Embedded(prefix = "pp_")
    ProductPrice productPrice;

    @Embedded(prefix = "p_")
    Product product;

    public BasketProduct(){

    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public OrderBasket getOrderBasket() {
        return orderBasket;
    }

    public void setOrderBasket(OrderBasket orderBasket) {
        this.orderBasket = orderBasket;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }
}
