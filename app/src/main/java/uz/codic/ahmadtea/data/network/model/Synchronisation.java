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

public class Synchronisation {

    List<Comment> comments;
    List<Currencies> currencies;
    List<Measurement> measurements;
    List<Merchant> merchants;
    List<Mmd> mmds;
    List<MmdType> mmdTypes;
    List<PaymentType> paymentTypes;
    List<Price> prices;
    List<Stocks> w_stocks;
    List<Workspace> workspaces;
    List<Product> products;
    List<ProductPrice> productPrices;

    public Synchronisation() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public List<Mmd> getMmds() {
        return mmds;
    }

    public void setMmds(List<Mmd> mmds) {
        this.mmds = mmds;
    }

    public List<MmdType> getMmdTypes() {
        return mmdTypes;
    }

    public void setMmdTypes(List<MmdType> mmdTypes) {
        this.mmdTypes = mmdTypes;
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Stocks> getW_stocks() {
        return w_stocks;
    }

    public void setW_stocks(List<Stocks> w_stocks) {
        this.w_stocks = w_stocks;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    @Override
    public String toString() {
        return "Synchronisation{" +
                "comments=" + comments +
                ", currencies=" + currencies +
                ", measurements=" + measurements +
                ", merchants=" + merchants +
                ", mmds=" + mmds +
                ", mmdTypes=" + mmdTypes +
                ", paymentTypes=" + paymentTypes +
                ", prices=" + prices +
                ", w_stocks=" + w_stocks +
                ", workspaces=" + workspaces +
                ", products=" + products +
                ", productPrices=" + productPrices +
                '}';
    }
}
