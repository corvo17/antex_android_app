package uz.codic.ahmadtea.ui.report.basketList;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.ApiOrderBasket;
import uz.codic.ahmadtea.data.network.model.ApiVisit;
import uz.codic.ahmadtea.data.network.model.Payload;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;
import uz.codic.ahmadtea.utils.CommonUtils;

import static uz.codic.ahmadtea.utils.Consts.isSaved;
import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSent;

public class BasketPresenter<V extends BasketMvpView> extends BasePresenter<V> implements BasketMvpPresenter<V> {

    private static IUpdateFromPresenter updater;
    public BasketPresenter(Context context) {
        super(context);
    }

    @Override
    public void getBasketList(int priceId, String orderId, Integer id_payment_type) {
        getDataManager().getBasketList(priceId, orderId, id_payment_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BasketProduct>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BasketProduct> orderBaskets) {
                        for (BasketProduct basketProduct: orderBaskets
                             ) {

                        }
                        getMvpView().onBasketListReady(orderBaskets);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public Visit getVisit(String id) {
        return getDataManager().getVisitByIdOrder(id);
    }

    @Override
    public List<Comment> getComments(List<Integer> ids) {
        return getDataManager().getComments(ids);
    }

    @Override
    public void requestSend(CompleteApi completeApi) {
        ApiObeject<Payload> send = collectApiObjects(completeApi.getOrderBasketList(), completeApi.getVisitObject(), completeApi.getOrderObject());
        send.getPayload().get(0).getOrder().setStatus_id(5);
        getDataManager().requestSend(getDataManager().getToken(),send)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Payload>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Payload> apiObeject) {
                        if (apiObeject.getMeta().getStatus() == 200){
                            Log.d("baxtiyor", "sent ordet: ");
                            saveObjectsAsSent(completeApi.getOrderBasketList(),completeApi.getVisitObject(), completeApi.getOrderObject());
                            if (getMvpView().getInfoAction() != null)getMvpView().getInfoAction().setSend(true);
//                            if (!isSaved){
//                                getDataManager().updateInfoAction(getMvpView().getInfoAction());
//                            }
                            getMvpView().goBack();
                        }else {
                            ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
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


//        new SingleObserver<SendResponse>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(SendResponse sendResponse) {
//                saveObjectsAsSent(completeApi.getOrderBasketList(),completeApi.getVisitObject(), completeApi.getOrderObject());
//                getMvpView().getInfoAction().setSend(true);
//                getDataManager().updateInfoAction(getMvpView().getInfoAction());
//                getMvpView().goBack();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//        }
    }
    @Override
    public void requestSendDraft(CompleteApi completeApi) {

        ApiObeject<Payload> send = collectApiObjects(completeApi.getOrderBasketList(), completeApi.getVisitObject(), completeApi.getOrderObject());
        send.getPayload().get(0).getOrder().setStatus_id(3);
        getDataManager().requestSend(getDataManager().getToken(),send)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Payload>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Payload> apiObeject) {
                        if (apiObeject.getMeta().getStatus() == 200){
                            saveObjectsAsSendDraft(completeApi);
                        }else {
                            ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
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

//        ApiObeject<Payload> send = collectApiObjects(completeApi.getOrderBasketList(), completeApi.getVisitObject(), completeApi.getOrderObject());
//        send.getPayload().get(0).getOrder().setOrder_status_id(3);
//
//        getDataManager().requestSendDraft(getDataManager().getToken(), send)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<SendResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(SendResponse sendResponse) {
//                        saveObjectsAsDraft(completeApi);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
    }
    @Override
    public void saveAsPending(CompleteApi completeApi, String status) {
        Completable.fromAction(()->{
            completeApi.getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
            for(OrderBasket orderBasket : completeApi.getOrderBasketList()){
                orderBasket.setStatus(status);
            }
            completeApi.getOrderObject().setStatus(status);
            completeApi.getOrderObject().setIdEmployee(getDataManager().getId_employee());
            completeApi.getVisitObject().setStatus(status);
            completeApi.getVisitObject().setId_employee(getDataManager().getId_employee());
            getDataManager().updateOrders( completeApi.getOrderObject());
            getDataManager().updateVisit( completeApi.getVisitObject());
            getDataManager().updateOrderBasket(completeApi.getOrderBasketList());
            updater.updateVisits();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        //updater.updateVisits();
//                        if (status.equals(statusPending)){
//                            getMvpView().getInfoAction().setSave_pending(true);
//                        }else  if (status.equals(statusSaveAsDraft)) {
//                            getMvpView().getInfoAction().setSave(true);
//                        }

                     //   getDataManager().updateInfoAction(getMvpView().getInfoAction());
                        getMvpView().goBack();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

    @Override
    public void requestCompleteObject(int merchantId, String workspaceId) {

    }

    @Override
    public void requestCompleteObject(String id_merchant, String workspaceId) {

    }

    @Override
    public InfoAction getInfoAction(String id_workspace, String id_merchant) {
        return null;
    }

    private ApiObeject<Payload> collectApiObjects(List<OrderBasket> baskets, Visit visit, Order order){
        ApiOrder apiOrder = new ApiOrder();
        ApiVisit apiVisit = new ApiVisit();
        List<ApiOrderBasket> apiOrderBaskets = new ArrayList<>();

        for(OrderBasket orderBasket : baskets){
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
        ApiObeject<Payload> apiObeject = new ApiObeject<>();
        List<Payload> payloads = new ArrayList<>();
        payloads.add(payload);
        apiObeject.setPayload(payloads);
        return apiObeject;

    }
    private void saveObjectsAsSendDraft(CompleteApi completeApi){
        Completable.fromAction(()->{
            completeApi.getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
            for(OrderBasket orderBasket : completeApi.getOrderBasketList()){
                orderBasket.setStatus(Consts.statusSendAsDraft);
            }
            completeApi.getOrderObject().setStatus(Consts.statusSendAsDraft);
            completeApi.getOrderObject().setIdEmployee(getDataManager().getId_employee());
            completeApi.getVisitObject().setStatus(Consts.statusSendAsDraft);
            completeApi.getVisitObject().setId_employee(getDataManager().getId_employee());
            getDataManager().updateOrders(completeApi.getOrderObject());
            getDataManager().updateVisit(completeApi.getVisitObject());
            getDataManager().updateOrderBasket(completeApi.getOrderBasketList());
            updater.updateVisits();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                    //    updater.updateVisits();
                       // getMvpView().getInfoAction().setSend_draft(true);
                     //   getDataManager().updateInfoAction(getMvpView().getInfoAction());
                        getMvpView().goBack();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
    private void saveObjectsAsSent(List<OrderBasket> orderBaskets, Visit visit, Order order){
        Completable.fromAction(()->{
            visit.setTime_end(CommonUtils.getCurrentTimeMilliseconds());
            for(OrderBasket orderBasket : orderBaskets){
                orderBasket.setStatus(statusSent);
            }
            order.setStatus(statusSent);
            order.setIdEmployee(getDataManager().getId_employee());
            visit.setStatus(statusSent);
            visit.setId_employee(getDataManager().getId_employee());
                getDataManager().updateOrders(order);
                getDataManager().updateVisit(visit);
                getDataManager().updateOrderBasket(orderBaskets);
            updater.updateVisits();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("baxtiyor", "onComplete: ");
                        //updater.updateVisits();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
    public static void setUpdater(IUpdateFromPresenter updateSavedVisits){
        updater = updateSavedVisits;
    }
    public interface IUpdateFromPresenter{
        void updateVisits();
    }
}
