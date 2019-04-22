package uz.codic.ahmadtea.ui.synchronisation.setOrder;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.ApiOrderBasket;
import uz.codic.ahmadtea.data.network.model.ApiVisit;
import uz.codic.ahmadtea.data.network.model.Payload;
import uz.codic.ahmadtea.data.network.model.Send;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.Consts;

public class SerOrderPresenter<V extends SetOrderMvpView> extends BasePresenter<V> implements SetOrderMvpPresenter<V> {
    public SerOrderPresenter(Context context) {
        super(context);
    }

    @Override
    public void getOrderBaskets(String id_order) {
        getDataManager().getOrderBaskets(id_order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<OrderBasket>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<OrderBasket> baskets) {
                        Log.d("baxtiyor", "onSuccess: " + baskets.size() +" " + baskets);
                        getMvpView().resultOrderBaskets(baskets);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void getProductItems(int id_price, String id_workspace, String id_product) {

        getMvpView().resultProductItems(getDataManager().getProductItems(id_price, id_workspace, id_product));

    }

    @Override
    public void requestSend(List<OrderBasket> baskets, Order order, Visit visit) {
        ApiObeject<Payload> send = collectApiObjects(baskets, visit, order);
        send.getPayload().get(0).getOrder().setOrder_status_id(5);

        getDataManager().requestSend(getDataManager().getToken(), send)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Payload>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Payload> apiObeject) {
                        if (apiObeject.getMeta().getStatus().equals("200")) {
                            updateOrder(baskets, order, visit, Consts.statusSaveAsDraft);
                            //TODO add info action
                            getMvpView().back();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @Override
    public void requestSendDraft(List<OrderBasket> baskets, Order order, Visit visit) {
        ApiObeject<Payload> send = collectApiObjects(baskets, visit, order);
        send.getPayload().get(0).getOrder().setOrder_status_id(5);

        getDataManager().requestSend(getDataManager().getToken(), send)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Payload>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Payload> apiObeject) {
                        if (apiObeject.getMeta().getStatus().equals("200")) {
                            updateOrder(baskets, order, visit, Consts.statusSendAsDraft);
                            getMvpView().back();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private void updateOrder(List<OrderBasket> baskets, Order order, Visit visit, String status){
        if (visit !=null){
            visit.setStatus(status);
            getDataManager().updateVisit(visit);
        }
        order.setStatus(status);
        getDataManager().updateOrders(order);
        for (OrderBasket basket:baskets) {
            basket.setStatus(status);
            getDataManager().updateBasket(basket);
        }
    }


    @Override
    public void setOrder(List<OrderBasket> baskets, Order order, Visit visit) {
        updateOrder(baskets, order, visit, order.getStatus());
    }

    @Override
    public Visit getVisit(String id_visit) {
         return getDataManager().getVisitByIdOrder(id_visit);
    }

    private ApiObeject<Payload> collectApiObjects(List<OrderBasket> baskets, Visit visit, Order order) {
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
        apiOrder.setFilial_id(order.getId_filial());
        apiOrder.setPrice_id(order.getId_price());
        apiOrder.setWorkspace_id(order.getId_workspace());
        apiOrder.setDelivery_date(order.getDelivery_date());


        if (visit != null) {
            apiVisit.setId(visit.getId());
            apiVisit.setMerchant_id(visit.getId_merchant());
            apiVisit.setComment_id(visit.getId_comment());
            apiVisit.setNotes(visit.getNotes());
            apiVisit.setTime_start(visit.getTime_start());
            apiVisit.setTime_end(visit.getTime_end());
            apiVisit.setLatitude(visit.getLatitude());
            apiVisit.setLongitude(visit.getLongitude());
            apiVisit.setFilial_id(visit.getId_filial());
        }

        Payload payload = new Payload(apiVisit, apiOrder, apiOrderBaskets);
        ApiObeject<Payload> apiObeject = new ApiObeject<>();
        List<Payload> payloads = new ArrayList<>();
        payloads.add(payload);
        apiObeject.setPayload(payloads);
        return apiObeject;

    }
}
