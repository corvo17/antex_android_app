package uz.codic.ahmadtea.ui.login;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.ErrorInfo;
import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.network.model.CentralObject;
import uz.codic.ahmadtea.data.network.model.Employee;
import uz.codic.ahmadtea.data.network.model.ErrorBody;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.data.network.model.Login;
import uz.codic.ahmadtea.data.network.model.Token;
import uz.codic.ahmadtea.data.network.model.WorkspaceRelations;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.data.network.model.api_objects.Payload;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    public LoginPresenter(Context context) {
        super(context);
    }

    //1.Check if user already logged in
    //2.If not logged request onRequestLogin() -> id_employee,key, token
    //3.User info request onRequestLoginInfo()
    //4 Workspace, Payment Types, Prices, MMDs -> onRequestWorkspace()
    //5.If shared objects do not exist in database, requestSharedObjectList()
    //6.Check user even exist in database

    @Override
    public void userCheckFromDb(final Login login) {

        getDataManager().
                isUserAlreadyLoggedIn(login.getLogin()).
                //Run on background thread
                        subscribeOn(Schedulers.io())
                //Notify on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d("baxtiyor", "onSuccess: " + integer);
                        if (integer == 0) {
                            //       getMvpView().changeProgressStatus("Loading..." , 30);
                            onRequestLogin(login);
                        } else {
                            getMvpView().showMessage("User already logged in");
                            ErrorClass.log("User already logged in", new Exception());
                            getMvpView().hideProgress();
                        }
                        //getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("baxtiyor", "onError: " + e.getMessage());
                        e.printStackTrace();
                        ErrorClass.log(e.getMessage(), (Exception) e);
                        getMvpView().showMessage(e.getMessage());
                        getMvpView().hideProgress();
                        //getMvpView().hideLoading();
                    }
                });

    }

    private void onRequestLogin(final Login login) {
        getDataManager()
                .requestToken(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Token token) {
                        //id_employee
                        //key
                        //token
                        //login
                        User user = new User();
                        user.setToken(token.getToken());
                        user.setLogin(login.getLogin());
                        getDataManager().putKey(token.getKey());
                        user.setId(token.getId_employee());
                        getMvpView().changeProgressStatus("Sing in...", 0);
                        onRequestLoginInfo(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ErrorClass.log(e.getMessage(), (Exception) e);
                        getMvpView().showMessage(e.getMessage());
                        getMvpView().hideProgress();
                        // getMvpView().hideLoading();
                    }
                });

    }

    @Override
    public void onRequestLoginInfo(final User user) {

        getDisposable().add(
                getDataManager().requestEmployeInfo("Bearer " + user.getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiObeject<Employee>>() {
                            @Override
                            public void onSuccess(ApiObeject<Employee> apiObeject) {
                                if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                                    user.setName(apiObeject.getPayload().get(0).getName());
                                    user.setRole_label(apiObeject.getPayload().get(0).getRole_label());
                                    user.setSync_time(CommonUtils.getCurrentTime());
                                    getDataManager().insertUser(user);
                                    getDataManager().setIslogin(true);
                                    getDataManager().setId_employee(user.getId());
                                    getMvpView().changeProgressStatus("Please wait... Getting data from server", 30);
                                    onRequestWorkspace(apiObeject.getPayload().get(0).getId(), user.getToken());
                                } else {
                                    ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                                    getMvpView().showMessage(apiObeject.getMeta().getMessage());
                                    //getMvpView().hideLoading();
                                    getMvpView().hideProgress();
                                    Log.d(Consts.TEST_TAG, "onError: " + apiObeject.getMeta().getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ErrorClass.log(e.getMessage(), (Exception) e);
                                e.printStackTrace();
                                getMvpView().showMessage(e.getMessage());
                                //getMvpView().hideLoading();
                                getMvpView().hideProgress();
                                Log.d(Consts.TEST_TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    @Override
    public void onRequestWorkspace(String id, final String token) {

        getDisposable().add(
                getDataManager().requestWorkspace("Bearer " + token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiObeject<MyWorkspace>>() {
                            @Override
                            public void onSuccess(ApiObeject<MyWorkspace> apiObeject) {
                                if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                                    Log.d("p", "onSuccess: " + apiObeject.getPayload());
                                    getDataManager().insertMyWorkspaces(apiObeject.getPayload());
                                    getMvpView().changeProgressStatus("Please wait... Writing date to database", 50);
                                    requestSharedObjectsList(token);
                                } else {
                                    ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                                    getMvpView().showMessage(apiObeject.getMeta().getMessage());
                                    //getMvpView().hideLoading();
                                    getMvpView().hideProgress();
                                    Log.d(Consts.TEST_TAG, "onError: " + apiObeject.getMeta().getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ErrorClass.log(e.getMessage(), (Exception) e);
                                e.printStackTrace();
                                getMvpView().showMessage(e.getMessage());
                                //getMvpView().hideLoading();
                                getMvpView().hideProgress();
                                Log.d(Consts.TEST_TAG, "onError: " + e.getMessage());
                            }
                        })
        );

//        getDataManager().insertMyWorkspaces(apiObeject.getPayload());
//        requestSharedObjectsList(token);
//
//        getMvpView().showMessage(e.getMessage());
//        getMvpView().hideLoading();
//        Log.d(Consts.TEST_TAG, "onError: " + e.getMessage());


    }

    public void requestSharedObjectsList(String token) {

        //put the token in shared preference
        getDataManager().putToken(token);
        Log.d("TokenTag", "requestSharedObjectsList: " + getDataManager().getToken());

        //Check db if shared objects already there
        if (getDataManager().isMerchantListFull() > 0) {
            getMvpView().showMessage("Already got shared objects");
            getMvpView().hideLoading();
            getMvpView().dontStay();
            getMvpView().hideProgress();
        } else {
            getDisposable().add(
                    getDataManager().requestAllSharedObjects("Bearer " + token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<ApiObeject<Payload>>() {
                                @Override
                                public void onSuccess(ApiObeject<Payload> apiObeject) {
                                    Log.d("baxtiyor", "onSuccess: " + apiObeject.getMeta().getStatus());
                                    if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                                        getDataManager().insertComments(apiObeject.getPayload().get(0).getComments());
                                        getDataManager().insertMmdtype(apiObeject.getPayload().get(0).getMmd_types());
                                        getDataManager().insertPaymentType(apiObeject.getPayload().get(0).getPayment_types());
                                        getDataManager().insertMerchant(apiObeject.getPayload().get(0).getMerchants());
                                        getDataManager().insertProductPrices(apiObeject.getPayload().get(0).getProduct_prices());
                                        getDataManager().insertProducts(apiObeject.getPayload().get(0).getProducts());
                                        getDataManager().insertMeasurements(apiObeject.getPayload().get(0).getMeasurements());
                                        getDataManager().insertCurrencies(apiObeject.getPayload().get(0).getCurrencies());
                                        getDataManager().insertPrice(apiObeject.getPayload().get(0).getPrices());
                                        getDataManager().insertMmds(apiObeject.getPayload().get(0).getMmds());
                                        getDataManager().insertStocks(apiObeject.getPayload().get(0).getWorkspaces_product_stocks());
                                        getDataManager().insertFileReportType(apiObeject.getPayload().get(0).getFile_report_types());
                                        getDataManager().insertWorkspaceWareHouse(apiObeject.getPayload().get(0).getPhysical_warehouse_workspace());
                                        getDataManager().insertPhysicalWareHouse(apiObeject.getPayload().get(0).getPhysical_warehouse());

                                        getMvpView().changeProgressStatus("Please wait... Writing date to database", 75);
                                        //go insert Workspace relations to db
                                    } else {
                                        getMvpView().showMessage(apiObeject.getMeta().getMessage());
                                        //getMvpView().hideLoading();
                                        getMvpView().hideProgress();
                                        Log.d(Consts.TEST_TAG, "onError: " + apiObeject.getMeta().getMessage());
                                        ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                                    }
                                    onRequestGetWorkspaceRelations(token);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ErrorClass.log(e.getMessage(), (Exception) e);
                                    e.printStackTrace();
                                    Log.d("baxtiyor", "onError: " + e.getMessage());
                                    getMvpView().showMessage(e.getMessage());
                                    //getMvpView().hideLoading();
                                    getMvpView().hideProgress();
                                    getMvpView().showMessage(e.getMessage());
                                }
                            })
            );
        }
    }

    @Override
    public void onRequestGetWorkspaceRelations(String token) {

        getDisposable().add(
                getDataManager().getWorkspaceRelations("Bearer " + token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiObeject<WorkspaceRelations>>() {
                            @Override
                            public void onSuccess(ApiObeject<WorkspaceRelations> apiObeject) {
                                if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                                    getDataManager().insertWorkspaceMmd(apiObeject.getPayload().get(0).getWorkspaces_mmds());
                                    getDataManager().insertWorkspacePrice(apiObeject.getPayload().get(0).getWorkspaces_prices());
                                    getDataManager().insertWorkspace(apiObeject.getPayload().get(0).getAll_workspaces());
                                    getDataManager().insertWorkspaceMerchant(apiObeject.getPayload().get(0).getWorkspaces_merchants());
                                    getDataManager().insertWorkspacePaymentType(apiObeject.getPayload().get(0).getWorkspaces_payment_types());
                                    //getMvpView().hideLoading();
                                    getMvpView().changeProgressStatus("Please wait... Writing date to database", 100);
                                    getMvpView().hideProgress();
                                    getMvpView().dontStay();
                                } else {
                                    ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                                    getMvpView().showMessage(apiObeject.getMeta().getMessage());
                                    //getMvpView().hideLoading();
                                    getMvpView().hideProgress();
                                    Log.d(Consts.TEST_TAG, "onError: " + apiObeject.getMeta().getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ErrorClass.log(e.getMessage(), (Exception) e);
                                e.printStackTrace();
                                Log.d("baxtiyor", "onError: " + e.getMessage());
                                getMvpView().showMessage(e.getMessage());
                                getMvpView().hideLoading();
                                getMvpView().showMessage(e.getMessage());
                            }
                        })
        );
    }

    @Override
    public void checkUser() {

        if (true) {
            if (getDataManager().isLogin()) {
                Log.d("baxtiyor", "checkUser: dontStay");
                checkErrors();
                getMvpView().dontStay();
            } else {
                Log.d("baxtiyor", "checkUser: onStay");
                getMvpView().onStay();
            }
        } else {
            getDisposable().add(getDataManager().getAllUsers()
                    .observeOn(AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .subscribe(users -> {
                        if (users == null || users.size() <= 0) {
                            //not found any user from database
                            getMvpView().onStay();// ☺
                        } else {
                            //if found a user
                            getMvpView().dontStay(); // :-D ☺
                        }
                    }));
        }
    }

    @Override
    public void onRequestBaseUrl(String code, String key) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("code", code);
        hashMap.put("key", key);
        getDataManager().getBaseUrlReq(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CentralObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CentralObject centralObject) {
                        Log.d("baxtiyor", "response central");
                        getDataManager().setBaseUrl(centralObject.getPayload().get("baseUrl"));
                        getDataManager().setisLocalized(true);
                        getMvpView().onResponseCentral();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorClass.log(e.getMessage(), (Exception) e);
                        e.printStackTrace();
                        getMvpView().hideProgress();
                        Log.d("baxtiyor", "onError: ");
                    }
                });
    }

    @Override
    public void onRequestResetToken(Login login) {
        getDataManager()
                .requestToken(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Token token) {
                        //id_employee
                        //key
                        //token
                        //login
                        Log.d("baxtiyor", "token onSuccess: " + token.getToken());
                        Log.d("baxtiyor", "token : " + getDataManager().getToken());
                        getDataManager().putToken(token.getToken());
                        User user = getDataManager().getUserById(getDataManager().getId_employee());
                        user.setToken(token.getToken());
                        getDataManager().updateUser(user);
                        getMvpView().hideLoading();
                        getMvpView().dontStay();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorClass.log(e.getMessage(), (Exception) e);
                        e.printStackTrace();
                        getMvpView().showMessage(e.getMessage());
                        getMvpView().hideLoading();
                    }
                });
    }

    @Override
    public void checkErrors() {

        getDataManager().getErrorInfoIsntSent(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ErrorInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ErrorInfo> errorInfos) {
                        if (errorInfos.size() > 0) {
                            sendErrors(errorInfos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorClass.log(e.getMessage(), (Exception) e);
                    }
                });

    }

    void sendErrors(List<ErrorInfo> errorInfos) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ErrorInfo info : errorInfos) {
                    ErrorObject errorObject = new ErrorObject();
                    ErrorBody body = new ErrorBody();
                    body.setAPI_version(info.getApi_version());
                    body.setOS_version(info.getOs_version());
                    body.setDevice_model(info.getDevice_model());
                    body.setTimestamp(info.getTimestamp());
                    body.setError_log(info.getErro_log());
                    body.setActive_internet_connection(info.isActive_internet_connection());
                    body.setError_message(info.getError_message());
                    errorObject.setBody(body);
                    errorObject.setId(info.getId());
                    errorObject.setApp_client_type(Consts.APP_CLIENT_TYPE);
                    getDataManager().sendError(getDataManager().getToken(), errorObject)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<ApiObeject<ErrorObject>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(ApiObeject<ErrorObject> apiObeject) {
                                    if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                                        info.setSent(true);
                                        getDataManager().updateErrorInfo(info);
                                    } else {
                                        ErrorClass.log(apiObeject.getMeta().getMessage(), new Exception());
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    ErrorClass.log("12548", (Exception) e);
                                }
                            });
                }
            }
        });
        thread.start();


    }
}
