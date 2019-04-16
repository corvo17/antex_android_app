package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;

public class ProductAndProductPrice {

    @Embedded
    Product product;

    @Embedded(prefix = "pp_")
    ProductPrice productPrices;

    @Embedded(prefix = "s_")
    Stocks stocks;

    @Ignore
    int quantity;

    @Ignore
    int count_boxes;

    @Ignore
    int count;

    public ProductAndProductPrice(){

    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductPrice getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(ProductPrice productPrices) {
        this.productPrices = productPrices;
    }

    public int getCount_boxes() {
        return count_boxes;
    }

    public void setCount_boxes(int count_boxes) {
        this.count_boxes = count_boxes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
