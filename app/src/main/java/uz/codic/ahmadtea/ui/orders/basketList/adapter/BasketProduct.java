package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;

public class BasketProduct implements Parcelable {

    @Embedded
    OrderBasket orderBasket;

    @Embedded(prefix = "pp_")
    ProductPrice productPrice;

    @Embedded(prefix = "p_")
    Product product;

    @Embedded(prefix = "pt_")
    PaymentType paymentType;

    public BasketProduct(){

    }

    protected BasketProduct(Parcel in) {
    }

    public static final Creator<BasketProduct> CREATOR = new Creator<BasketProduct>() {
        @Override
        public BasketProduct createFromParcel(Parcel in) {
            return new BasketProduct(in);
        }

        @Override
        public BasketProduct[] newArray(int size) {
            return new BasketProduct[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
