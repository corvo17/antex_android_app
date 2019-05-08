package uz.codic.ahmadtea.data;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import uz.codic.ahmadtea.data.db.DbHelper;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Currencies;
import uz.codic.ahmadtea.data.db.entities.ErrorInfo;
import uz.codic.ahmadtea.data.db.entities.FileReportType;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Measurement;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Mmd;
import uz.codic.ahmadtea.data.db.entities.MmdType;
import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.PhotoG;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;
import uz.codic.ahmadtea.data.db.entities.Stocks;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMerchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMmd;
import uz.codic.ahmadtea.data.db.entities.WorkspacePaymentType;
import uz.codic.ahmadtea.data.db.entities.WorkspacePrice;
import uz.codic.ahmadtea.data.network.ApiCentral;
import uz.codic.ahmadtea.data.network.ApiClient;
import uz.codic.ahmadtea.data.network.ApiEndPoint;
import uz.codic.ahmadtea.data.network.ApiService;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.BeforeSyncObject;
import uz.codic.ahmadtea.data.network.model.CentralObject;
import uz.codic.ahmadtea.data.network.model.DailyBody;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.data.network.model.Employee;
import uz.codic.ahmadtea.data.network.model.EmployeeLocation;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.data.network.model.LocationResponse;
import uz.codic.ahmadtea.data.network.model.Login;
import uz.codic.ahmadtea.data.network.model.Message;
import uz.codic.ahmadtea.data.network.model.ObjectsForEmployee;
import uz.codic.ahmadtea.data.network.model.Send;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.data.network.model.SharedObject;
import uz.codic.ahmadtea.data.network.model.Synchronisation;
import uz.codic.ahmadtea.data.network.model.Token;
import uz.codic.ahmadtea.data.network.model.WorkspaceRelations;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.data.network.model.api_objects.Payload;
import uz.codic.ahmadtea.data.prefs.AppPrefHelper;
import uz.codic.ahmadtea.data.prefs.PrefHelper;
import uz.codic.ahmadtea.di.ApplicationContext;
import uz.codic.ahmadtea.ui.orders.adapter.OrderedList;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;


public class AppDataManager implements DataManager {

    ApiService apiService;
    ApiCentral apiCentral;
    //ApiService apiServiceBook;

    DbHelper dbHelper;
    PrefHelper prefHelper;
    Context mContext;
    ApiClient apiClient;

    //Inject in the constructor accumulates all the Dependent objects from
    // dagger(ApiService, dbHelper)
    public AppDataManager(@ApplicationContext Context context) {
        mContext = context;
        //apiServiceBook = ApiClient.getApiClient2(context).create(ApiService.class);
        dbHelper = DbHelper.getDatabase(context);
        prefHelper = new AppPrefHelper(context);
        //apiClient = new ApiClient(context, prefHelper.getBaseUrl());
        //apiServiceBook = apiClient.getApiClient().create(ApiService.class);
        if (isLocalized()){
            //apiService = ApiClient.getApiClient(context, prefHelper.getBaseUrl()).create(ApiService.class);
            ApiClient api_client = new ApiClient(mContext, prefHelper.getBaseUrl());
            apiService = api_client.getApiClient2(mContext).create(ApiService.class);
        }
        ApiClient updateApiClient =new  ApiClient(context, ApiEndPoint.URL_CHECK_FOR_UPDATE);
        apiCentral = updateApiClient.getApiClient().create(ApiCentral.class);

    }

    /**
     * Shared Preference calls
     */
    @Override
    public void putKey(String key) {
        prefHelper.putKey(key);
    }

    @Override
    public String getKey() {
        return prefHelper.getKey();
    }

    @Override
    public void putToken(String token) {
        prefHelper.putToken(token);
    }

    @Override
    public String getToken() {
        return prefHelper.getToken();
    }

    @Override
    public void setId_employee(String id_employee) {
        prefHelper.setId_employee(id_employee);
    }

    @Override
    public String getId_employee() {
        return prefHelper.getId_employee();
    }

    @Override
    public String getLastSyncTime() {
        return prefHelper.getLastSyncTime();
    }

    @Override
    public void setLastSyncTime(String lastSyncTime) {
        prefHelper.setLastSyncTime(lastSyncTime);
    }

    @Override
    public boolean isAdmin() {
        return prefHelper.isAdmin();
    }

    @Override
    public void changeIsAdmin(boolean bool) {
        prefHelper.changeIsAdmin(bool);
    }

    @Override
    public String getBaseUrl() {
        return prefHelper.getBaseUrl();
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        prefHelper.setBaseUrl(baseUrl);
        ApiClient apiClient1 = new ApiClient(mContext, baseUrl);
//        apiService = ApiClient.getApiClient(mContext, baseUrl).create(ApiService.class);
        apiService = apiClient1.getApiClient2(mContext).create(ApiService.class);
    }

    @Override
    public boolean isLocalized() {
        return prefHelper.isLocalized();
    }

    @Override
    public void setisLocalized(boolean isLogin) {
        prefHelper.setisLocalized(isLogin);
    }

    @Override
    public boolean isLogin() {
        return prefHelper.isLogin();
    }

    @Override
    public void setIslogin(boolean islogin) {
        prefHelper.setIslogin(islogin);
    }

    /***
     Api calls
     **/
    @Override
    public Single<Token> requestToken(Login login) {
        return apiService.requestToken(login);
    }

    @Override
    public Single<ApiObeject<MyWorkspace>> requestWorkspace(String token) {
        return apiService.requestWorkspace(token);
    }

    @Override
    public Single<ApiObeject<Employee>> requestEmployeInfo(String token) {
        return apiService.requestEmployeInfo(token);
    }

    @Override
    public Single<ApiObeject<Payload>> requestAllSharedObjects(String token) {
        return apiService.requestAllSharedObjects(token);
    }

    @Override
    public Single<ApiObeject<uz.codic.ahmadtea.data.network.model.Payload>> requestSend(String token, ApiObeject<uz.codic.ahmadtea.data.network.model.Payload> apiObeject) {
        return apiService.requestSend(token, apiObeject);
    }

    @Override
    public Single<SendResponse> requestSendDraft(String token, Send send) {
        return apiService.requestSendDraft(token, send);
    }

    @Override
    public Single<SendResponse> requestSendPending(String token, Send send) {
        return apiService.requestSendPending(token, send);
    }

    @Override
    public Single<ApiObeject<DailyMerchants>> requestDailyMerchants(String token, HashMap<String , HashMap<String , String >> body) {
        return apiService.requestDailyMerchants(token, body);
    }

    @Override
    public Call<LocationResponse> sendLocation(String token, List<EmployeeLocation> locations) {
        return apiService.sendLocation(token, locations);
    }

    // request Synchronisation
    @Override
    public Single<ApiObeject<Synchronisation>> requestSynchronization(String token) {
        return apiService.requestSynchronization(token);
    }

    // request before Sync
    @Override
    public Single<BeforeSyncObject> requesBeforeSync(String token) {
        return apiService.requesBeforeSync(token);
    }

    @Override
    public Single<ResponseBody> uploadeBook(RequestBody description, MultipartBody.Part fileAttached) {
        return null;//apiServiceBook.uploadeBook(description,fileAttached);
    }

    // request Get Workspace relations

    @Override
    public Single<ApiObeject<WorkspaceRelations>> getWorkspaceRelations(String token) {
        return apiService.getWorkspaceRelations(token);
    }

    @Override
    public void insertPhoto(PhotoG photo) {
        dbHelper.daoAccess().insertPhoto(photo);
        Log.d("WroteMM","Wrote database: "+photo.getLocation());
    }

    // request add merchant
    @Override
    public Single<ApiObeject<uz.codic.ahmadtea.data.network.model.Merchant>> requestAddMerchant(String token, ApiObeject<uz.codic.ahmadtea.data.network.model.Merchant> apiObeject) {
        return apiService.requestAddMerchant(token, apiObeject);
    }

    @Override
    public Single<ApiObeject<ErrorObject>> sendError(String token, ErrorObject errorObject) {
        return apiService.sendError(token, errorObject);
    }

    @Override
    public Single<CentralObject> getBaseUrlReq(HashMap<String, String> hashMap) {
        return apiCentral.getBaseUrlReq(hashMap);
    }

    @Override
    public Single<HashMap<String, HashMap<String, String>>> getMobileCurrentVersion() {
        return apiCentral.getMobileCurrentVersion();
    }

    /**
     * Database calls
     */
    @Override
    public void insertUser(final User user) {
        dbHelper.daoAccess().insertUser(user);
    }

    @Override
    public void insertMerchant(List<Merchant> merchants) {
        dbHelper.daoAccess().insertMerchant(merchants);
    }

    @Override
    public void insertPaymentType(List<PaymentType> paymentTypes) {
        dbHelper.daoAccess().insertPaymentType(paymentTypes);
    }

    @Override
    public void insertPrice(List<Price> prices) {
        dbHelper.daoAccess().insertPrice(prices);
    }

    @Override
    public void insertMmds(List<Mmd> Mmd) {
        dbHelper.daoAccess().insertMmds(Mmd);
    }

    @Override
    public Single<Integer> isUserAlreadyLoggedIn(String login) {
        return dbHelper.daoAccess().isUserAlreadyLoggedIn(login);
    }

    @Override
    public Single<List<User>> getAllUsers() {
        return dbHelper.daoAccess().getAllUsers();
    }

    @Override
    public Single<List<Merchant>> getAllMerchants() {
        return dbHelper.daoAccess().getAllMerchants();
    }

    @Override
    public Single<Merchant> getMerchantById(int id) {
        return dbHelper.daoAccess().getMerchantById(id);
    }

    @Override
    public void insertComments(List<Comment> comments) {
        dbHelper.daoAccess().insertComments(comments);
    }

    @Override
    public void insertMmdtype(List<MmdType> mmdTypes) {
        dbHelper.daoAccess().insertMmdtype(mmdTypes);
    }

    @Override
    public void insertProductPrices(List<ProductPrice> productPrices) {
        dbHelper.daoAccess().insertProductPrices(productPrices);
    }

    @Override
    public void insertProducts(List<Product> products) {
        dbHelper.daoAccess().insertProducts(products);
    }

    @Override
    public void insertMeasurements(List<Measurement> measurements) {
        dbHelper.daoAccess().insertMeasurements(measurements);
    }

    @Override
    public void insertCurrencies(List<Currencies> currencies) {
        dbHelper.daoAccess().insertCurrencies(currencies);
    }

    @Override
    public void insertInfoAction(InfoAction infoAction) {
        dbHelper.daoAccess().insertInfoAction(infoAction);
    }

    @Override
    public void updateInfoAction(InfoAction... infoActions) {
        dbHelper.daoAccess().updateInfoAction(infoActions);
    }

    //    @Override
//    public Single<List<Merchant>> getMerchantsInWorkspace(String id) {
//        return dbHelper.daoAccess().getMerchantsInWorkspace(id);
//    }

    @Override
    public Single<List<Merchant>> getDailyMerchants(List<String> merchantsId) {
        return dbHelper.daoAccess().getDailyMerchants(merchantsId);
    }



    @Override
    public Single<List<ProductAndProductPrice>> getProductsWithValue(int priceId, String workspace_id) {
        return dbHelper.daoAccess().getProductsWithValue(priceId, workspace_id);
    }

    @Override
    public Single<List<Product>> getAllProducts() {
        return dbHelper.daoAccess().getAllProducts();
    }


    @Override
    public long isMerchantListFull() {
        return dbHelper.daoAccess().isMerchantListFull();
    }

//    @Override
//    public Single<List<String>> getAttachedWorkspaces(String id) {
//        return dbHelper.daoAccess().getAttachedWorkspaces(id);
//    }

    @Override
    public void insertOrder(Order order) {
        dbHelper.daoAccess().insertOrder(order);
    }

    @Override
    public void insertOrderBasket(List<OrderBasket> orderBaskets) {
        dbHelper.daoAccess().insertOrderBasket(orderBaskets);
    }

    @Override
    public void insertVisit(Visit visitObject) {
        dbHelper.daoAccess().insertVisit(visitObject);
    }

    @Override
    public Single<List<OrderedList>> getOrderedList() {
        return dbHelper.daoAccess().getOrderedList();
    }

    @Override
    public Single<List<BasketProduct>> getBasketList(int priceId, String orderId) {
        return dbHelper.daoAccess().getBasketList(priceId, orderId);
    }

    //insert Stocks to db
    @Override
    public void insertStocks(List<Stocks> stocks) {
        dbHelper.daoAccess().insertStocks(stocks);
    }

    // get Workspace by id
    @Override
    public Single<Workspace> getWorkspaceById(String id) {
        return dbHelper.daoAccess().getWorkspaceById(id);
    }

    //insert MyWorkspace to db
    @Override
    public void insertMyWorkspaces(List<MyWorkspace> myWorkspaces) {
        dbHelper.daoAccess().insertMyWorkspaces(myWorkspaces);
    }

    /*
     *       begin insert Workspace relations
     * */

    @Override
    public void insertWorkspaceMmd(List<WorkspaceMmd> workspaceMmds) {
        dbHelper.daoAccess().insertWorkspaceMmd(workspaceMmds);
    }

    @Override
    public void insertWorkspacePrice(List<WorkspacePrice> workspacePrices) {
        dbHelper.daoAccess().insertWorkspacePrice(workspacePrices);
    }

    @Override
    public void insertWorkspace(List<Workspace> workspaces) {
        dbHelper.daoAccess().insertWorkspace(workspaces);
    }

    @Override
    public void insertWorkspaceMerchant(List<WorkspaceMerchant> workspaceMerchants) {
        dbHelper.daoAccess().insertWorkspaceMerchant(workspaceMerchants);
    }

    @Override
    public void insertWorkspacePaymentType(List<WorkspacePaymentType> workspacePaymentTypes) {
        dbHelper.daoAccess().insertWorkspacePaymentType(workspacePaymentTypes);
    }

    /*
     *       end Workspace relations
     * */
    @Override
    public List<WorkspaceMerchant> getAllWorkspaceMerchants() {
        return dbHelper.daoAccess().getAllWorkspaceMerchants();
    }

    @Override
    public Single<List<WorkspaceAndMerchant>> getWorkspaceAndMerchants(List<String > id_workspaces, String date) {
        return dbHelper.daoAccess().getWorkspaceAndMerchants(id_workspaces, date);
    }

    @Override
    public List<WorkspacePaymentType> getWorspacePaymentTypes() {
        return dbHelper.daoAccess().getWorspacePaymentTypes();
    }

    @Override
    public List<PaymentType> getPaymentTypes() {
        return dbHelper.daoAccess().getPaymentTypes();
    }

    @Override
    public Single<List<PaymentType>> getPayentTypeForMerchant(String id_workspace) {
        return dbHelper.daoAccess().getPayentTypeForMerchant(id_workspace);
    }

    @Override
    public Single<List<Price>> getPricesmerchant(String id_workspace) {
        return dbHelper.daoAccess().getPricesmerchant(id_workspace);
    }

    @Override
    public Single<List<Mmd>> getMmdForMerchant(String id_workspace) {
        return dbHelper.daoAccess().getMmdForMerchant(id_workspace);
    }

    //TODO test

    @Override
    public List<WorkspacePrice> getWorkspacePrices() {
        return dbHelper.daoAccess().getWorkspacePrices();
    }

    @Override
    public List<Price> getPrices() {
        return dbHelper.daoAccess().getPrices();
    }

    @Override
    public List<WorkspacePrice> getWorkspacePricesById_4(String id_workspace) {
        return dbHelper.daoAccess().getWorkspacePricesById_4(id_workspace);
    }

    // get id_workspace from WorkspaceMerchant like : id_merchant
    @Override
    public String getId_workspace(String id_merchant) {
        return dbHelper.daoAccess().getId_workspace(id_merchant);
    }

    @Override
    public Single<List<Comment>> allComments() {
        return dbHelper.daoAccess().allComments();
    }

    @Override
    public Single<List<String>> allCommentNames() {
        return dbHelper.daoAccess().allCommentNames();
    }

    // get my Workspace
    @Override
    public Single<List<Workspace>> getMyWorkspaces(String id_employee) {
        return dbHelper.daoAccess().getMyWorkspaces(id_employee);
    }

    @Override
    public List<String> getMyWorkspaceIds(String id_employee) {
        return dbHelper.daoAccess().getMyWorkspaceIds(id_employee);
    }

    //get merchants in workspace
    @Override
    public Single<List<WorkspaceAndMerchant>> getMerchantsInWorkspace(String... id_workspace) {
        return dbHelper.daoAccess().getMerchantsInWorkspace(id_workspace);
    }

    @Override
    public Single<List<WorkspaceAndMerchant>> getMerchantsInWorkspace(List<String> id_workspaces, String date) {
        return dbHelper.daoAccess().getMerchantsInWorkspace(id_workspaces, date);
    }

    @Override
    public WorkspaceAndMerchant getWorkspaceAndMerchants(String id_merchant, String id_workspace) {
        return dbHelper.daoAccess().getWorkspaceAndMerchants(id_merchant, id_workspace);
        }
        
    @Override
    public Single<List<PhotoG>> allPhoto() {
        return dbHelper.daoAccess().allPhoto();
    }

    @Override
    public InfoAction getInfoAction(String id_merchant, String id_workspace, String date) {
        return dbHelper.daoAccess().getInfoAction(id_merchant, id_workspace, date);
    }

    @Override
    public User getUserById(String id) {
        return dbHelper.daoAccess().getUserById(id);
    }

    @Override
    public void updateUser(User... users) {
        dbHelper.daoAccess().updateUser(users);
    }

    @Override
    public void removeUser(User... users) {
        dbHelper.daoAccess().removeUser(users);
    }

    @Override
    public void removeUserWorkspaces(List<MyWorkspace> myWorkspaces) {
        dbHelper.daoAccess().removeUserWorkspaces(myWorkspaces);
    }

    @Override
    public List<MyWorkspace> getUserWorkspaceForLogOut(String id_employee) {
        return dbHelper.daoAccess().getUserWorkspaceForLogOut(id_employee);
    }

    @Override
    public void insertFileReportType(List<FileReportType> fileReportTypes) {
        dbHelper.daoAccess().insertFileReportType(fileReportTypes);
    }

    @Override
    public Single<List<FileReportType>> getFileReportTypes() {
        return dbHelper.daoAccess().getFileReportTypes();
    }

    // query error

    @Override
    public void insertErrorInfo(ErrorInfo... errorInfos) {
        dbHelper.daoAccess().insertErrorInfo(errorInfos);
    }

    @Override
    public Single<List<ErrorInfo>> getErrorInfoIsntSent(boolean isSent) {
        return dbHelper.daoAccess().getErrorInfoIsntSent(isSent);
    }

    @Override
    public void updateErrorInfo(ErrorInfo errorInfo) {
        dbHelper.daoAccess().updateErrorInfo(errorInfo);
    }


    // query error ^


    //-------------------------------------------------------------------------------------------------------------------------------
    //region Synchronisation start

    @Override
    public void insertComment(Comment comment) {
        dbHelper.daoAccess().insertComment(comment);
    }

    @Override
    public void updateCommets(Comment... comments) {
        dbHelper.daoAccess().updateCommets(comments);
    }

    @Override
    public Comment geComment(int id) {
        return dbHelper.daoAccess().geComment(id);
    }

    @Override
    public List<Comment> getCommentSize() {
        return dbHelper.daoAccess().getCommentSize();
    }

    @Override
    public Single<List<OrderedList>> getPendingOrders(String status) {
        return dbHelper.daoAccess().getPendingOrders(status);
    }

    @Override
    public Visit getVisitByIdOrder(String id) {
        return dbHelper.daoAccess().getVisitByIdOrder(id);
    }

    @Override
    public Single<List<OrderBasket>> getOrderBaskets(String id_order) {
        return dbHelper.daoAccess().getOrderBaskets(id_order);
    }

    @Override
    public void updateOrders(Order... orders) {
        dbHelper.daoAccess().updateOrders(orders);
    }

    @Override
    public void insertCurrencies(Currencies currencies) {
        dbHelper.daoAccess().insertCurrencies(currencies);
    }

    @Override
    public void uptadeCurrencies(Currencies... currencies) {
        dbHelper.daoAccess().uptadeCurrencies(currencies);
    }

    @Override
    public Currencies getCurrencies(int id) {
        return dbHelper.daoAccess().getCurrencies(id);
    }

    @Override
    public void insertMeasurmuent(Measurement measurement) {
        dbHelper.daoAccess().insertMeasurmuent(measurement);
    }

    @Override
    public void updateMeasurment(Measurement... measurements) {
        dbHelper.daoAccess().updateMeasurment(measurements);
    }

    @Override
    public Measurement getMeasurement(int id) {
        return dbHelper.daoAccess().getMeasurement(id);
    }

    @Override
    public void updateMerchant(Merchant... merchants) {
        dbHelper.daoAccess().updateMerchant(merchants);
    }

    @Override
    public Merchant getMerchant(String id) {
        return dbHelper.daoAccess().getMerchant(id);
    }

    @Override
    public Mmd getMmd(int id) {
        return dbHelper.daoAccess().getMmd(id);
    }

    @Override
    public MmdType getMmdType(Integer id) {
        return dbHelper.daoAccess().getMmdType(id);
    }

    @Override
    public PaymentType getPaymentType(int id) {
        return dbHelper.daoAccess().getPaymentType(id);
    }

    @Override
    public Price getPrice(int id) {
        return dbHelper.daoAccess().getPrice(id);
    }

    @Override
    public Stocks getStocks(int id) {
        return dbHelper.daoAccess().getStocks(id);
    }

    @Override
    public Workspace getWorkspace(String id) {
        return dbHelper.daoAccess().getWorkspace(id);
    }

    @Override
    public Product getProduct(String id) {
        return dbHelper.daoAccess().getProduct(id);
    }

    @Override
    public ProductPrice getProductPrice(int id) {
        return dbHelper.daoAccess().getProductPrice(id);
    }

    @Override
    public Stocks getStocks(String  id_workspace, String id_product) {
        return dbHelper.daoAccess().getStocks(id_workspace, id_product);
    }

    @Override
    public ProductPrice getProductPrice(int id_price, String id_product) {
        return dbHelper.daoAccess().getProductPrice(id_price, id_product);
    }

    @Override
    public void insertMerchant(Merchant merchant) {
        dbHelper.daoAccess().insertMerchant(merchant);
    }

    @Override
    public void insertMmd(Mmd mmd) {
        dbHelper.daoAccess().insertMmd(mmd);
    }

    @Override
    public void insertMmdType(MmdType mmdType) {
        dbHelper.daoAccess().insertMmdType(mmdType);
    }

    @Override
    public void insertPaymentType(PaymentType paymentType) {
        dbHelper.daoAccess().insertPaymentType(paymentType);
    }

    @Override
    public void insertPrice(Price price) {
        dbHelper.daoAccess().insertPrice(price);
    }

    @Override
    public void insertStocks(Stocks stocks) {
        dbHelper.daoAccess().insertStocks(stocks);
    }

    @Override
    public void insertWorkspace(Workspace workspace) {
        dbHelper.daoAccess().insertWorkspace(workspace);
    }

    @Override
    public void insertProduct(Product product) {
        dbHelper.daoAccess().insertProduct(product);
    }

    @Override
    public void insertProductPrice(ProductPrice productPrice) {
        dbHelper.daoAccess().insertProductPrice(productPrice);
    }

    @Override
    public void updateMmd(Mmd... mmds) {
        dbHelper.daoAccess().updateMmd(mmds);
    }

    @Override
    public void updateMmdType(MmdType... mmdTypes) {
        dbHelper.daoAccess().updateMmdType(mmdTypes);
    }

    @Override
    public void updatePaymentType(PaymentType... paymentTypes) {
        dbHelper.daoAccess().updatePaymentType(paymentTypes);
    }

    @Override
    public void updatePrice(Price... prices) {
        dbHelper.daoAccess().updatePrice(prices);
    }

    @Override
    public void updateStocks(Stocks... stocks) {
        dbHelper.daoAccess().updateStocks(stocks);
    }

    @Override
    public void updateWorkspace(Workspace... workspaces) {
        dbHelper.daoAccess().updateWorkspace(workspaces);
    }

    @Override
    public void updateProduct(Product... products) {
        dbHelper.daoAccess().updateProduct(products);
    }

    @Override
    public void updateProductPrice(ProductPrice... productPrices) {
        dbHelper.daoAccess().updateProductPrice(productPrices);
    }

    @Override
    public void updateVisit(Visit... visits) {
        dbHelper.daoAccess().updateVisit(visits);
    }

    @Override
    public void updateBasket(OrderBasket... baskets) {
        dbHelper.daoAccess().updateBasket(baskets);
    }

    @Override
    public ProductAndProductPrice getProductItems(int priceId, String  id_workspace, String id_product) {
        return dbHelper.daoAccess().getProductItems(priceId, id_workspace, id_product);
    }



    //endregion Synchronisation end
//-------------------------------------------------------------------------------------------------------------------------------



//-------------------------------------------------------------------------------------------------------------------------------
    // add merchant start

    @Override
    public void insertNewMerchant(NewMerchant newMerchant) {
        dbHelper.daoAccess().insertNewMerchant(newMerchant);
    }

    @Override
    public Single<List<NewMerchant>> getNewMerchants() {
        return dbHelper.daoAccess().getNewMerchants();
    }

    @Override
    public NewMerchant getNewMerchant(String id_merchant) {
        return dbHelper.daoAccess().getNewMerchant(id_merchant);
    }

    // add merchant finish
//-------------------------------------------------------------------------------------------------------------------------------
}

