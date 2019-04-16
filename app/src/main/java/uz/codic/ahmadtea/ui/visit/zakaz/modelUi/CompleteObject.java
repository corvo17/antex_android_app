package uz.codic.ahmadtea.ui.visit.zakaz.modelUi;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.Workspace;

public class CompleteObject {

    List<Price> priceList;
    List<PaymentType> paymentTypeList;
    Merchant merchant;
    Workspace workspace;
    List<ProductAndProductPrice> productList;

    List<Comment> comments;

    String TAG = "ZipOperator";
    public void setPaymentTypeList(List<PaymentType> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public List<PaymentType> getPaymentTypeList() {
        return paymentTypeList;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setProductList(List<ProductAndProductPrice> productList) {
        this.productList = productList;
    }

    public List<ProductAndProductPrice> getProductList() {
        return productList;
    }

    public String toStringPrices(){
        String object = "";
        for (Price price: getPriceList()){
            object += price.getLabel() + "\n";
        }
        return object;
    }

    public String toStringPayments(){
        String object = "";
        for (PaymentType paymentType :getPaymentTypeList()){
            object += paymentType.getLabel() + "\n";
        }
        return object;
    }



}
