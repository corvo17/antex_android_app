package uz.codic.ahmadtea.ui.synchronisation;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Currencies;
import uz.codic.ahmadtea.data.db.entities.Measurement;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Mmd;
import uz.codic.ahmadtea.data.db.entities.MmdType;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;
import uz.codic.ahmadtea.data.db.entities.Stocks;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.ApiOrderBasket;
import uz.codic.ahmadtea.data.network.model.ApiVisit;
import uz.codic.ahmadtea.data.network.model.BeforeSyncObject;
import uz.codic.ahmadtea.data.network.model.Payload;
import uz.codic.ahmadtea.data.network.model.Send;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.data.network.model.Synchronisation;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.orders.adapter.OrderedList;
import uz.codic.ahmadtea.utils.Consts;

public class SynchronisationPresenter<V extends SynchronisationMvpView> extends BasePresenter<V> implements SynchronisationMvpPresenter<V> {
    public SynchronisationPresenter(Context context) {
        super(context);
    }

    List<OrderedList> pendingList;
    List<Visit> visits;
    List<List<OrderBasket>> orderBaskets;
    List<Integer> positions = new ArrayList<>();

    @Override
    public void startSynchronisation() {
        getDataManager().requestSynchronization(getDataManager().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Synchronisation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("baxtiyor", "onSubscribe: " + d);
                    }

                    @Override
                    public void onSuccess(ApiObeject<Synchronisation> apiObeject) {
                        if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                            nextStep(apiObeject.getPayload().get(0));
                        }else {
                            ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                            Log.d("baxtiyor", "onSuccess: ");
                            getMvpView().hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                        e.printStackTrace();

                        getMvpView().hideLoading();
                        HttpException exception = (HttpException) e;
                        ResponseBody responseBody = exception.response().errorBody();
                        try {
                            JSONObject array = new JSONObject(responseBody.string()).getJSONObject("meta");

                            Log.d("baxtiyor", "onError: status " + array.getLong("status"));
                            Log.d("baxtiyor", "onError: " + responseBody.string());
                            ErrorClass.log(e.getMessage(), (Exception) e, array.getJSONObject("error").getString("error_id"));

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }

                    }



                });

        //nextStep(synchronisation);
    }

    private void nextStep(Synchronisation synchronisation) {
        if (isEmpty(synchronisation)) {
            getMvpView().relustSync("Nothing Changed \nSynchronisation Success");

            if (pendingList != null) {
//                for (int i = 0; i < pendingList.size(); i++) {
//                    sendPending(orderBaskets.get(i), visits.get(i), pendingList.get(i).getOrder());
//                }
                startSendPending();
            }
            requestBeforeSync();
            getMvpView().hideLoading();
        } else {
            getMvpView().relustSync("Yes data");
            getData(synchronisation).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
            if (pendingList != null) {
                startSendPending();
            }
            requestBeforeSync();
            getMvpView().hideLoading();
        }

    }

    private Single getData(Synchronisation synchronisation) {
        // comments
        if (!synchronisation.getComments().isEmpty()) {
            for (int i = 0; i < synchronisation.getComments().size(); i++) {
                Comment newComment = synchronisation.getComments().get(i);
                if (getDataManager().geComment(newComment.getId()) != null) {
                    Comment comment = getDataManager().geComment(newComment.getId());
                    newComment.setPid(comment.getPid());
                    getDataManager().updateCommets(newComment);
                } else {
                    getDataManager().insertComment(newComment);
                }
            }
        }

        //currencies
        if (!synchronisation.getCurrencies().isEmpty()) {
            for (Currencies currencies : synchronisation.getCurrencies()) {
                if (getDataManager().getCurrencies(currencies.getId()) != null) {
                    currencies.setPid(getDataManager().getCurrencies(currencies.getId()).getPid());
                    getDataManager().uptadeCurrencies(currencies);
                } else {
                    getDataManager().insertCurrencies(currencies);
                }
            }
        }

        //measurements
        if (!synchronisation.getMeasurements().isEmpty()) {
            for (Measurement measurement : synchronisation.getMeasurements()) {
                if (getDataManager().getMeasurement(measurement.getId()) != null) {
                    measurement.setPid(getDataManager().getMeasurement(measurement.getId()).getPid());
                    getDataManager().updateMeasurment(measurement);
                } else {
                    getDataManager().insertMeasurmuent(measurement);
                }
            }
        }

        //merchants
        if (!synchronisation.getMerchants().isEmpty()) {
            for (Merchant merchant : synchronisation.getMerchants()) {
                if (getDataManager().getMerchant(merchant.getId()) != null) {
                    merchant.setPid(getDataManager().getMerchant(merchant.getId()).getPid());
                    getDataManager().updateMerchant(merchant);
                } else {
                    getDataManager().insertMerchant(merchant);
                }
            }
        }

        //mmds
        if (!synchronisation.getMmds().isEmpty()) {
            for (Mmd mmd : synchronisation.getMmds()) {
                if (getDataManager().getMmd(mmd.getId()) != null) {
                    mmd.setPid(getDataManager().getMmd(mmd.getId()).getPid());
                    getDataManager().updateMmd(mmd);
                } else {
                    getDataManager().insertMmd(mmd);
                }
            }
        }

        //mmdType
        if (!synchronisation.getMmdTypes().isEmpty()) {
            for (MmdType mmdType : synchronisation.getMmdTypes()) {
                if (getDataManager().getMmdType(mmdType.getId()) != null) {
                    mmdType.setPid(getDataManager().getMmdType(mmdType.getId()).getPid());
                    getDataManager().updateMmdType(mmdType);
                } else {
                    getDataManager().insertMmdType(mmdType);
                }
            }
        }

        //paymentType
        if (!synchronisation.getPaymentTypes().isEmpty()) {
            for (PaymentType paymentType : synchronisation.getPaymentTypes()) {
                if (getDataManager().getPaymentType(paymentType.getId()) != null) {
                    paymentType.setPid(getDataManager().getPaymentType(paymentType.getId()).getPid());
                    getDataManager().updatePaymentType(paymentType);
                } else {
                    getDataManager().insertPaymentType(paymentType);
                }
            }
        }

        //price
        if (!synchronisation.getPrices().isEmpty()) {
            for (Price price : synchronisation.getPrices()) {
                if (getDataManager().getPrice(price.getId()) != null) {
                    price.setPid(getDataManager().getPrice(price.getId()).getPid());
                    getDataManager().updatePrice(price);
                } else {
                    getDataManager().insertPrice(price);
                }
            }
        }

        //stocks
        if (!synchronisation.getWorkspaces_product_stocks().isEmpty()) {
            for (Stocks stocks : synchronisation.getWorkspaces_product_stocks()) {
                Stocks oldStocks = getDataManager().getStocks(stocks.getId());
                if (oldStocks != null) {
                    stocks.setPid(oldStocks.getPid());
                    if (pendingList != null) {
                        checkOrderTotalCount(stocks).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                    getDataManager().updateStocks(stocks);
                } else {
                    getDataManager().insertStocks(stocks);
                }
            }
        }

        //workspace
        if (!synchronisation.getWorkspaces().isEmpty()) {
            for (Workspace workspace : synchronisation.getWorkspaces()) {
                if (getDataManager().getWorkspace(workspace.getId()) != null) {
                    workspace.setPid(getDataManager().getWorkspace(workspace.getId()).getPid());
                    getDataManager().updateWorkspace(workspace);
                } else {
                    getDataManager().insertWorkspace(workspace);
                }
            }
        }

        //product
        if (!synchronisation.getProducts().isEmpty()) {
            for (Product product : synchronisation.getProducts()) {
                if (getDataManager().getProduct(product.getId()) != null) {
                    product.setPid(getDataManager().getProduct(product.getId()).getPid());
                    getDataManager().updateProduct(product);
                } else {
                    getDataManager().insertProduct(product);
                }
            }
        }

        //product_price
        if (!synchronisation.getProductPrices().isEmpty()) {
            for (ProductPrice productPrice : synchronisation.getProductPrices()) {
                ProductPrice oldProductPrice = getDataManager().getProductPrice(productPrice.getId());
                if (oldProductPrice != null) {
                    productPrice.setPid(oldProductPrice.getPid());
                    if (pendingList != null) {
                        checkPrice(productPrice).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                    getDataManager().updateProductPrice(productPrice);
                } else {
                    getDataManager().insertProductPrice(productPrice);
                }
            }
        }

        return new Single() {
            @Override
            protected void subscribeActual(SingleObserver observer) {

            }
        };
    }

    private boolean isEmpty(Synchronisation synchronisation) {
        boolean b = false;
        if (synchronisation.getComments().isEmpty()
                && synchronisation.getCurrencies().isEmpty()
                && synchronisation.getMeasurements().isEmpty()
                && synchronisation.getMerchants().isEmpty()
                && synchronisation.getMmds().isEmpty()
                && synchronisation.getMmdTypes().isEmpty()
                && synchronisation.getPaymentTypes().isEmpty()
                && synchronisation.getPrices().isEmpty()
                && synchronisation.getWorkspaces_product_stocks().isEmpty()
                && synchronisation.getWorkspaces().isEmpty()
                && synchronisation.getProducts().isEmpty()
                && synchronisation.getProductPrices().isEmpty()) b = true;
        else b = false;
        return b;
    }

    private Single checkPrice(ProductPrice productPrice) {

        for (int i = 0; i < pendingList.size(); i++) {
            for (int j = 0; j < orderBaskets.get(i).size(); j++) {
                if (productPrice.getProduct_id().equals(orderBaskets.get(i).get(j).getId_product()) && productPrice.getPrice_id() == pendingList.get(i).getOrder().getId_price()) {

                    if (productPrice.getValue() != orderBaskets.get(i).get(j).getPrice_value()) {
                        positions.add(i);
                    }
                } else {

                }
            }
        }

        return new Single() {
            @Override
            protected void subscribeActual(SingleObserver observer) {

            }
        };
    }

    private Single checkOrderTotalCount(Stocks stocks) {

        for (int i = 0; i < pendingList.size(); i++) {
            for (int j = 0; j < orderBaskets.get(i).size(); j++) {

                Log.d("baxtiyor", "checkOrderTotalCount: " + stocks);
                if (stocks.getProduct_id().equals(orderBaskets.get(i).get(j).getId_product()) && stocks.getWorkspace_id().equals(pendingList.get(i).getOrder().getId_workspace())) {
                    int newRemaing_amount, order_count;
                    newRemaing_amount = stocks.getTotal_count();
                    order_count = orderBaskets.get(i).get(j).getTotal_count();
                    Log.d("baxtiyor", "check: " + newRemaing_amount + " " + order_count);
                    if (newRemaing_amount < order_count) {
                        positions.add(i);
                    } else {

                    }

                } else {

                }
            }
        }

        return new Single() {
            @Override
            protected void subscribeActual(SingleObserver observer) {

            }
        };
    }

    @Override
    public void getPendingSize() {
        int pentingSize;
        getDataManager().getPendingOrders("pending")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<OrderedList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<OrderedList> orderedLists) {
                        if (orderedLists.size() > 0) {
                            getPendingdate(orderedLists);
                            getMvpView().relustPendingSize(orderedLists.size());
                            pendingNames();

                        } else {
                            getMvpView().relustPendingSize(0);
                            pendingList = null;
                            getMvpView().showPendings(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Consts.TEST_TAG, "onError: " + e.getMessage());

                    }
                });
    }

    public void requestBeforeSync() {
        getDataManager().requesBeforeSync(getDataManager().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BeforeSyncObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BeforeSyncObject beforeSyncObject) {
                        if (beforeSyncObject.getError() == null) {
                            getMvpView().relustSync(beforeSyncObject.getMessage());
                            setLastSyncTime();
                        } else {
                            getMvpView().relustSync(beforeSyncObject.getError() + "\n" + beforeSyncObject.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    private void setLastSyncTime() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        getDataManager().setLastSyncTime(df.format("yyyy-MM-dd kk:mm:ss", new java.util.Date()).toString());
        getLastSyncTime();
    }

    private void startSendPending() {

        for (int i = 0; i < pendingList.size(); i++) {
            boolean bool = false;
            for (int j = 0; j < orderBaskets.get(i).size(); j++) {
                Order thisOrder = pendingList.get(i).getOrder();
                OrderBasket thisBasket = orderBaskets.get(i).get(j);
                Product product = getDataManager().getProduct(thisBasket.getId_product());
                Stocks stocks = getDataManager().getStocks(thisOrder.getId_workspace(), thisBasket.getId_product());
                ProductPrice productPrice = getDataManager().getProductPrice(thisOrder.getId_price(), thisBasket.getId_product());

                int remaing_ammount = stocks.getTotal_count();
                Log.d("stocks", "remaing_ammount: " + remaing_ammount);
                Log.d("stocks", "total_count: " + remaing_ammount);
                int price_value = productPrice.getValue();
                if (thisBasket.getTotal_count() > remaing_ammount || productPrice.getValue() != thisBasket.getPrice_value()) {
                    bool = true;
                    break;
                }
            }

            if (bool) {
                getMvpView().relustSync(pendingList.get(i).getMerchant().getLabel() + " zakas jonatilmadi chunki bu zakas eski malumot bilan to'ldirilgan");
            } else {
                sendPending(orderBaskets.get(i), visits.get(i), pendingList.get(i).getOrder());
            }
        }

    }

    public void sendPending(List<OrderBasket> baskets, Visit visit, Order order) {
        Send send = collectApiObjects(baskets, visit, order);

        getDataManager().requestSendPending(getDataManager().getToken(), send)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SendResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SendResponse sendResponse) {
                        if (sendResponse.getMessage().equals("Success!!!")) {
                            getMvpView().relustSync("Pending sent");
                            order.setStatus(Consts.statusSaveAsDraft);
                            getDataManager().updateOrders(order);
                            getPendingSize();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private Send collectApiObjects(List<OrderBasket> baskets, Visit visit, Order order) {
        ApiOrder apiOrder = new ApiOrder();
        ApiVisit apiVisit = new ApiVisit();
        List<ApiOrderBasket> apiOrderBaskets = new ArrayList<>();

        for (OrderBasket orderBasket : baskets) {
            ApiOrderBasket apiOrderBasket = new ApiOrderBasket();
            apiOrderBasket.setProduct_id(orderBasket.getId_product());
            apiOrderBasket.setTotal_count(orderBasket.getTotal_count());
            apiOrderBaskets.add(apiOrderBasket);
        }
        apiOrder.setId(order.getId());
        apiOrder.setPayment_type_id(order.getId_payment_type());
        apiOrder.setMmd_id(order.getId_mmd());
        apiOrder.setMerchant_id(order.getId_merchant());
        apiOrder.setTotal_cost(order.getTotal_cost());
        apiOrder.setTotal_cost_with_mmd(order.getTotal_cost_with_mmd());
        apiOrder.setNote(order.getNotes());
        apiOrder.setVisit_id(order.getVisitId());
        apiOrder.setPrice_id(order.getId_price());
        apiOrder.setWorkspace_id(order.getId_workspace());
        apiOrder.setDelivery_date(order.getDelivery_date());


        apiVisit.setId(visit.getId());
        apiVisit.setMerchant_id(visit.getId_merchant());
        apiVisit.setComment_id(visit.getId_comment());
        apiVisit.setNotes(visit.getNotes());
        apiVisit.setTime_start(visit.getTime_start());
        apiVisit.setTime_end(visit.getTime_end());
        apiVisit.setLatitude(visit.getLatitude());
        apiVisit.setLongitude(visit.getLongitude());
        apiVisit.setFilial_id(visit.getId_filial());

        Payload payload = new Payload(apiVisit, apiOrder, apiOrderBaskets);
        return new Send(payload);

    }

    private void getPendingdate(List<OrderedList> orderedList) {
        pendingList = orderedList;
        visits = new ArrayList<>();
        orderBaskets = new ArrayList<>();
        for (OrderedList order : orderedList) {
            getVisit(order.getOrder().getVisitId());
            getOrderBaskets(order.getOrder().getId());
        }
    }

    private void getOrderBaskets(String id_order) {
        getDataManager().getOrderBaskets(id_order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<OrderBasket>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<OrderBasket> baskets) {
                        orderBaskets.add(baskets);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        //orderBaskets.add(getDataManager().getOrderBaskets(id_order));
    }

    private void getVisit(String id_visit) {
        visits.add(getDataManager().getVisitByIdOrder(id_visit));
    }

    @Override
    public void pendingNames() {
        if (pendingList != null) {
            List<String> pendingNames = new ArrayList<>();
            for (int i = 0; i < pendingList.size(); i++) {
                pendingNames.add(pendingList.get(i).getMerchant().getLabel());
            }
            getMvpView().showPendings(pendingNames);
        }
    }

    @Override
    public void getLastSyncTime() {
        getMvpView().showLastSyncTime(getDataManager().getLastSyncTime());
    }

    @Override
    public OrderedList getOrder(int which) {
        return pendingList.get(which);
    }
}
