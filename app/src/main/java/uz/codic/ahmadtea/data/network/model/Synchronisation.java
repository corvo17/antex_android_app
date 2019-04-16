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
    List<ProductPrice> productPrices;
    List<Stocks> workspaces_product_stocks;
    List<Product> products;
    List<MmdType> mmdTypes;
    List<PaymentType> paymentTypes;
    List<Workspace> workspaces;
    List<Merchant> merchants;
    List<Mmd> mmds;
    List<Price> prices;
    List<Currencies> currencies;
    List<Measurement> measurements;

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

    public List<Stocks> getWorkspaces_product_stocks() {
        return workspaces_product_stocks;
    }

    public void setWorkspaces_product_stocks(List<Stocks> workspaces_product_stocks) {
        this.workspaces_product_stocks = workspaces_product_stocks;
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
                ", w_stocks=" + workspaces_product_stocks +
                ", workspaces=" + workspaces +
                ", products=" + products +
                ", productPrices=" + productPrices +
                '}';
    }
}
