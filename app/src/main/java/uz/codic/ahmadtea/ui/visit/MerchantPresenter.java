package uz.codic.ahmadtea.ui.visit;

import android.content.Context;
import android.util.Log;

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
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.ApiOrderBasket;
import uz.codic.ahmadtea.data.network.model.ApiVisit;
import uz.codic.ahmadtea.data.network.model.Payload;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSent;

public class MerchantPresenter<V extends MerchantMvpView> extends BasePresenter<V> implements MerchantMvpPresenter<V> {

    public MerchantPresenter(Context context) {
        super(context);
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
                        getMvpView().getInfoAction().setSend(true);
                        getDataManager().updateInfoAction(getMvpView().getInfoAction());
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
            getDataManager().insertOrder(completeApi.getOrderObject());
            getDataManager().insertVisit(completeApi.getVisitObject());
            getDataManager().insertOrderBasket(completeApi.getOrderBasketList());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().getInfoAction().setSend_draft(true);
                        getDataManager().updateInfoAction(getMvpView().getInfoAction());
                        getMvpView().goBack();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
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
            getDataManager().insertOrder( completeApi.getOrderObject());
            getDataManager().insertVisit( completeApi.getVisitObject());
            getDataManager().insertOrderBasket(completeApi.getOrderBasketList());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        if (status.equals(statusPending)){
                        getMvpView().getInfoAction().setSave_pending(true);
                        }else  if (status.equals(statusSaveAsDraft)) {
                            getMvpView().getInfoAction().setSave(true);
                        }

                        getDataManager().updateInfoAction(getMvpView().getInfoAction());
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
            getDataManager().insertOrder(order);
            getDataManager().insertVisit(visit);
            getDataManager().insertOrderBasket(orderBaskets);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("baxtiyor", "onComplete: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void requestCompleteObject(int merchantId, String workspaceId) {
        Single.zip(getMerchantAndWorkspace(merchantId, workspaceId), getCompletPriceAndPayment(workspaceId),
                new BiFunction<CompleteObject, CompleteObject, CompleteObject>() {
                    @Override
                    public CompleteObject apply(CompleteObject completeObject1, CompleteObject completeObject2) throws Exception {
                        CompleteObject completeObject3 = new CompleteObject();
                        completeObject3.setMerchant(completeObject1.getMerchant());
                        completeObject3.setWorkspace(completeObject1.getWorkspace());
                        completeObject3.setPaymentTypeList(completeObject2.getPaymentTypeList());
                        completeObject3.setPriceList(completeObject2.getPriceList());
                        return completeObject3;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CompleteObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CompleteObject completeObject) {
                        Log.d("ZipOperator", completeObject.getMerchant().toString());
                        Log.d("ZipOperator", completeObject.getWorkspace().toString());
                        Log.d("ZipOperator", completeObject.toStringPayments());
                        Log.d("ZipOperator", completeObject.toStringPrices());
                        getMvpView().onCompleteObjectReady(completeObject);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    @Override
    public void requestCompleteObject(String id_merchant, String workspaceId) {
        Single.zip(getMerchantAndWorkspace(id_merchant, workspaceId), getCompletPriceAndPayment(workspaceId),
                new BiFunction<CompleteObject, CompleteObject, CompleteObject>() {
                    @Override
                    public CompleteObject apply(CompleteObject completeObject1, CompleteObject completeObject2) throws Exception {
                        CompleteObject completeObject3 = new CompleteObject();
                        completeObject3.setMerchant(completeObject1.getMerchant());
                        completeObject3.setWorkspace(completeObject1.getWorkspace());
                        completeObject3.setPaymentTypeList(completeObject2.getPaymentTypeList());
                        completeObject3.setPriceList(completeObject2.getPriceList());
                        return completeObject3;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CompleteObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CompleteObject completeObject) {
                        Log.d("ZipOperator", completeObject.getMerchant().toString());
                        Log.d("ZipOperator", completeObject.getWorkspace().toString());
                        Log.d("ZipOperator", completeObject.toStringPayments());
                        Log.d("ZipOperator", completeObject.toStringPrices());
                        getMvpView().onCompleteObjectReady(completeObject);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    public Single<CompleteObject> getMerchantAndWorkspace(int merchantId, String workspaceId){
        return Single.zip(getDataManager().getMerchantById(merchantId), getDataManager().getWorkspaceById(workspaceId),
                new BiFunction<Merchant, Workspace, CompleteObject>() {
                    @Override
                    public CompleteObject apply(Merchant merchant, Workspace workspace) throws Exception {
                        CompleteObject completeObject = new CompleteObject();
                        completeObject.setMerchant(merchant);
                        completeObject.setWorkspace(workspace);
                        return completeObject;
                    }
                });
    }

    public Single<CompleteObject> getMerchantAndWorkspace(String  id_merchant, String workspaceId){
        return Single.zip(getDataManager().getMerchantById(1), getDataManager().getWorkspaceById(workspaceId),
                new BiFunction<Merchant, Workspace, CompleteObject>() {
                    @Override
                    public CompleteObject apply(Merchant merchant, Workspace workspace) throws Exception {
                        CompleteObject completeObject = new CompleteObject();
                        completeObject.setMerchant(getNewMerchant(id_merchant));
                        completeObject.setWorkspace(workspace);
                        return completeObject;
                    }
                });
    }

    private Merchant getNewMerchant(String id_merchant) {
        Merchant merchant = new Merchant();
        NewMerchant newMerchant = getDataManager().getNewMerchant(id_merchant);
        merchant.setLabel(newMerchant.getName());
        merchant.setId(newMerchant.getId());
        merchant.setAddress(newMerchant.getAddress());
        merchant.setPhone(String.valueOf(newMerchant.getPhone()));
        merchant.setInn(newMerchant.getInn());
        merchant.setLatitude(newMerchant.getLatitude());
        merchant.setLongitude(newMerchant.getLongitude());
        return merchant;
    }


    public Single<CompleteObject> getCompletPriceAndPayment(String workspaceId){
        return  Single.zip(getDataManager().getPricesmerchant(workspaceId),
                getDataManager().getPayentTypeForMerchant(workspaceId),
                new BiFunction<List<Price>, List<PaymentType>, CompleteObject>() {

                    @Override
                    public CompleteObject apply(List<Price> prices, List<PaymentType> paymentTypes) throws Exception {
                        CompleteObject completeObject = new CompleteObject();
                        completeObject.setPriceList(prices);
                        completeObject.setPaymentTypeList(paymentTypes);
                        return completeObject;
                    }
                });
    }

    @Override
    public InfoAction getInfoAction(String id_workspace, String id_merchant) {
        Log.d("baxtiyor", "getInfoAction: " + getDataManager().getInfoAction(id_merchant, id_workspace, CommonUtils.getToday()));
        if (getDataManager().getInfoAction(id_merchant, id_workspace, CommonUtils.getToday()) != null)
        return getDataManager().getInfoAction(id_merchant, id_workspace, CommonUtils.getToday());
        else {
            InfoAction infoAction = new InfoAction();
            infoAction.setId_merchant(id_merchant);
            infoAction.setId_workspace(id_workspace);
            infoAction.setDate(CommonUtils.getToday());
            getDataManager().insertInfoAction(infoAction);
            return getDataManager().getInfoAction(id_merchant, id_workspace, CommonUtils.getToday());
        }
    }


}
