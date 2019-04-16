package uz.codic.ahmadtea.data.network.model;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Currencies;
import uz.codic.ahmadtea.data.db.entities.Measurement;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Mmd;
import uz.codic.ahmadtea.data.db.entities.MmdType;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;
import uz.codic.ahmadtea.data.db.entities.Stocks;
import uz.codic.ahmadtea.data.db.entities.Workspace;

public class SharedObject {

    List<Comment> comments;
    List<MmdType> mmd_types;
    List<PaymentType> payment_types;
    List<Merchant> merchants;
    List<ProductPrice> product_prices;
    List<Product> products;
    List<Measurement> measurements;
    List<Currencies> currencies;
    List<Price> prices;
    List<Mmd> mmds;
    List<Stocks> w_stocks;

    public SharedObject(){

    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public List<ProductPrice> getProduct_prices() {
        return product_prices;
    }

    public void setProduct_prices(List<ProductPrice> product_prices) {
        this.product_prices = product_prices;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<MmdType> getMmd_types() {
        return mmd_types;
    }

    public void setMmd_types(List<MmdType> mmd_types) {
        this.mmd_types = mmd_types;
    }

    public List<PaymentType> getPayment_types() {
        return payment_types;
    }

    public void setPayment_types(List<PaymentType> payment_types) {
        this.payment_types = payment_types;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Mmd> getMmds() {
        return mmds;
    }

    public void setMmds(List<Mmd> mmds) {
        this.mmds = mmds;
    }

    public List<Stocks> getW_stocks() {
        return w_stocks;
    }

    public void setW_stocks(List<Stocks> w_stocks) {
        this.w_stocks = w_stocks;
    }
}
