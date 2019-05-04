package uz.codic.ahmadtea.data.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Currencies;
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
import uz.codic.ahmadtea.ui.orders.adapter.OrderedList;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;

@Dao
public interface DaoAccess {


    @Insert
    void insertPhoto(PhotoG photo);  //Inserting photo

    @Insert
    void insertUser(User user);

    //insert myWorkspace
    @Insert
    void insertMyWorkspaces(List<MyWorkspace> myWorkspaces);

    /*
     *       # Inset Workspace relations
     * */
    @Insert
    void insertWorkspaceMmd(List<WorkspaceMmd> workspaceMmds);

    @Insert
    void insertWorkspacePrice(List<WorkspacePrice> workspacePrices);

    @Insert
    void insertWorkspace(List<Workspace> workspaces);

    @Insert
    void insertWorkspaceMerchant(List<WorkspaceMerchant> workspaceMerchants);

    @Insert
    void insertWorkspacePaymentType(List<WorkspacePaymentType> workspacePaymentTypes);

    /*
    *       # end Workspace relations
    * */


    //insert stocks
    @Insert
    void insertStocks(List<Stocks> stocks);

    @Insert
    void insertPaymentType(List<PaymentType> paymentTypes);

    @Insert
    void insertPrice(List<Price> prices);

    @Insert
    void insertMmds(List<Mmd> Mmd);

    //Inserting shared objects
    @Insert
    void insertComments(List<Comment> comments);

    @Insert
    void insertMmdtype(List<MmdType> mmdTypes);

    @Insert
    void insertMerchant(List<Merchant> merchants);

    @Insert
    void insertProductPrices(List<ProductPrice> productPrices);

    @Insert
    void insertProducts(List<Product> products);

    @Insert
    void insertMeasurements(List<Measurement> measurements);

    @Insert
    void insertCurrencies(List<Currencies> currencies);

    @Insert
    void insertInfoAction(InfoAction infoAction);

    @Update
    void updateInfoAction(InfoAction... infoActions);

//------------------------------------------------------------------------------------------------------------
    // Synchronisation start
    // insert synchronisation
    @Insert
    void insertComment(Comment comment);

    @Insert
    void insertCurrencies(Currencies currencies);

    @Insert
    void insertMeasurmuent(Measurement measurement);

    @Insert
    void insertMerchant(Merchant merchant);

    @Insert
    void insertMmd(Mmd mmd);

    @Insert
    void insertMmdType(MmdType mmdType);

    @Insert
    void insertPaymentType(PaymentType paymentType);

    @Insert
    void insertPrice(Price price);

    @Insert
    void insertStocks(Stocks stocks);

    @Insert
    void insertWorkspace(Workspace workspace);

    @Insert
    void insertProduct(Product product);

    @Insert
    void insertProductPrice(ProductPrice productPrice);





    // updates synchronisation
    @Update
    void updateCommets(Comment... comments);

    @Update
    void updateOrders(Order... orders);

    @Update
    void uptadeCurrencies(Currencies... currencies);

    @Update
    void updateMeasurment(Measurement... measurements);

    @Update
    void updateMerchant(Merchant... merchants);

    @Update
    void updateMmd(Mmd... mmds);

    @Update
    void updateMmdType(MmdType... mmdTypes);

    @Update
    void updatePaymentType(PaymentType... paymentTypes);

    @Update
    void updatePrice(Price... prices);

    @Update
    void updateStocks(Stocks... stocks);

    @Update
    void updateWorkspace(Workspace... workspaces);

    @Update
    void updateProduct(Product... products);

    @Update
    void updateProductPrice(ProductPrice... productPrices);

    @Update
    void updateVisit(Visit... visits);

    @Update
    void updateBasket(OrderBasket... baskets);

    // synchronisation queries
    @Query("select * from comment where id=:id")
    Comment geComment(int id);

    @Query("select * from currencies where id=:id")
    Currencies getCurrencies(int id);

    @Query("select * from merchant where id=:id")
    Merchant getMerchant(String id);

    @Query("select * from measurement where id=:id")
    Measurement getMeasurement(int id);

    @Query("select * from mmd where id=:id")
    Mmd getMmd(int id);

    @Query("select * from mmdtype where id=:id")
    MmdType getMmdType(Integer id);

    @Query("select * from paymenttype where id = :id")
    PaymentType getPaymentType(int id);

    @Query("select * from price where id =:id")
    Price getPrice(int id);

    @Query("select * from stocks where id=:id")
    Stocks getStocks(int id);

    @Query("select * from stocks where workspace_id=:id_workspace and product_id=:id_product")
    Stocks getStocks(String id_workspace, String id_product);

    @Query("select * from workspace where id=:id")
    Workspace getWorkspace(String id);

    @Query("select * from product where id=:id")
    Product getProduct(String id);

    @Query("select * from productprice where id=:id")
    ProductPrice getProductPrice(int id);

    @Query("select * from productprice where price_id=:id_price and product_id=:id_product")
    ProductPrice getProductPrice(int id_price, String id_product);

    @Query("select * from comment")
    List<Comment> getCommentSize();

    @Query("select `Order`.*, m.label as m_label  from `Order` INNER JOIN Merchant as m on id_merchant = m.id where status=:status")
    Single<List<OrderedList>> getPendingOrders(String status);

    @Query("select * from visit where id=:id")
    Visit getVisitByIdOrder(String id);

    @Query("select * from orderbasket where orderId=:id_order")
    Single<List<OrderBasket>> getOrderBaskets(String id_order);


    @Query("SELECT Product.*, ProductPrice.value as pp_value, Stocks.total_count as s_total_count FROM Product INNER JOIN productprice ON ProductPrice.product_id = Product.id and ProductPrice.price_id =:priceId INNER JOIN stocks  on Stocks.product_id = Product.id AND Stocks.workspace_id=:id_workspace  where Product.id =:id_product")
    ProductAndProductPrice getProductItems(int priceId, String  id_workspace, String id_product);
    // Synchronisation end
//------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------
    // add merchant start
    @Insert
    void insertNewMerchant(NewMerchant newMerchant);

    @Query("select * from newmerchant")
    Single<List<NewMerchant>> getNewMerchants();

    @Query("select * from newmerchant where NewMerchant.id=:id_merchant")
    NewMerchant getNewMerchant(String id_merchant);
    // add merchant finish
//------------------------------------------------------------------------------------------------------------

    //Queries
    @Query("SELECT * FROM User")
    Single<List<User>> getAllUsers();

    @Query("SELECT COUNT(*) FROM User WHERE login = :login")
    Single<Integer> isUserAlreadyLoggedIn(String login);

    @Query("SELECT * FROM MERCHANT")
    Single<List<Merchant>> getAllMerchants();

    @Query("SELECT * FROM MERCHANT WHERE pid = :id")
    Single<Merchant> getMerchantById(int id);

    @Query("SELECT COUNT(*) FROM MERCHANT")
    long isMerchantListFull();

    @Query("select * from WorkspaceMerchant")
    List<WorkspaceMerchant> getAllWorkspaceMerchants();
//TODO v1
//    @Query("select * from MERCHANT where id_workspace in  (select id from workspace where id_employee = :id)")
////    Single<List<Merchant>> getMerchantsInWorkspace(String id);

    @Query("select * from MERCHANT where id  in  (:merchantsId)")
    Single<List<Merchant>> getDailyMerchants(List<String> merchantsId);

//    @Query("select id from workspace where id_employee = :id")
//    Single<List<String>> getAttachedWorkspaces(String id);


    @Query("SELECT Product.*, ProductPrice.value as pp_value, Stocks.total_count as s_total_count FROM Product INNER JOIN productprice ON Product.id = ProductPrice.product_id INNER JOIN stocks  on Stocks.product_id = Product.id AND Stocks.workspace_id=:workspace_id  where ProductPrice.price_id = :priceId order by Product.default_serial_id")
    Single<List<ProductAndProductPrice>> getProductsWithValue(int priceId, String workspace_id);

    @Query("select * from product")
    Single<List<Product>> getAllProducts();

    // get workspace by id
    @Query("select * from workspace where id like :id limit 1")
    Single<Workspace> getWorkspaceById(String id);

    //Save orders locally
    @Insert
    void insertOrder(Order order);

    @Insert
    void insertOrderBasket(List<OrderBasket> orderBaskets);

    @Insert
    void insertVisit(Visit visitObject);

    @Query("select `Order`.*, m.label as m_label  from `Order` INNER JOIN Merchant as m on id_merchant = m.id")
    Single<List<OrderedList>> getOrderedList();


    @Query("SELECT  OrderBasket.*,p.label as p_name ,ProductPrice.value as pp_value FROM orderbasket INNER JOIN productprice, product as p ON orderbasket.id_product = ProductPrice.product_id and p.id= orderbasket.id_product where ProductPrice.price_id = :priceId and orderId = :orderId")
    Single<List<BasketProduct>> getBasketList(int priceId, String orderId);

    @Query("SELECT Workspace.id as w_id, Workspace.label as w_name, Workspace.pid as w_pid, Workspace.status_id as w_id_status, InfoAction.*,  Merchant.* from WorkspaceMerchant INNER JOIN merchant on Merchant.id = WorkspaceMerchant.merchant_id INNER JOIN Workspace ON Workspace.id=WorkspaceMerchant.workspace_id left JOIN InfoAction ON InfoAction.i_id_merchant=WorkspaceMerchant.merchant_id and InfoAction.i_date =:date and InfoAction.i_id_workspace = WorkspaceMerchant.workspace_id where workspace_id in (:id_workspaces) ORDER BY Merchant.id")
    Single<List<WorkspaceAndMerchant>> getWorkspaceAndMerchants(List<String > id_workspaces, String date);

    //
    @Query("select PaymentType.* from PaymentType inner join WorkspacePaymentType on WorkspacePaymentType.payment_type_id = PaymentType.id where WorkspacePaymentType.workspace_id = :id_workspace")
    Single<List<PaymentType>> getPayentTypeForMerchant(String id_workspace);

    @Query("select Price.* from Price inner join WorkspacePrice on WorkspacePrice.price_id = Price.id where WorkspacePrice.workspace_id = :id_workspace")
    Single<List<Price>> getPricesmerchant(String id_workspace);

    @Query("select Mmd.* from Mmd inner join WorkspaceMmd on WorkspaceMmd.mmd_id = Mmd.id where WorkspaceMmd.workspace_id = :id_workspace")
    Single<List<Mmd>> getMmdForMerchant(String id_workspace);

    @Query("select * from WorkspacePrice")
    List<WorkspacePrice> getWorkspacePrices();

    @Query("select * from Price")
    List<Price> getPrices();

    @Query("SELECT * FROM WorkspacePrice where workspace_id = :id_workspace")
    List<WorkspacePrice> getWorkspacePricesById_4(String id_workspace);

    @Query("select * from WorkspacePaymentType")
    List<WorkspacePaymentType> getWorspacePaymentTypes();

    @Query("select * from PaymentType")
    List<PaymentType> getPaymentTypes();

    // get id_workspace from WorkspaceMerchant like : id_merchant
    @Query("select workspace_id from workspacemerchant where merchant_id = :id_merchant")
    String getId_workspace(String id_merchant);

    @Query("SELECT * FROM COMMENT")
    Single<List<Comment>> allComments();

    @Query("SELECT label FROM Comment")
    Single<List<String>> allCommentNames();

    // get my workspaces
    @Query("select Workspace.* from MyWorkspace inner join Workspace on Workspace.id = MyWorkspace.workspace_id where MyWorkspace.employee_id = :id_employee")
    Single<List<Workspace>> getMyWorkspaces(String id_employee);

    @Query("select w.id from MyWorkspace as mw inner join Workspace as w on w.id = mw.workspace_id where mw.employee_id =:id_employee")
    List<String> getMyWorkspaceIds(String id_employee);

    // get merchants in workspace
    @Query("SELECT Workspace.id as w_id, Workspace.label as w_label, Workspace.pid as w_pid, Workspace.status_id as w_id_status, InfoAction.*, Merchant.* from WorkspaceMerchant INNER JOIN merchant on Merchant.id = WorkspaceMerchant.merchant_id INNER JOIN Workspace ON Workspace.id=WorkspaceMerchant.workspace_id left JOIN InfoAction ON InfoAction.i_id_merchant=WorkspaceMerchant.merchant_id where WorkspaceMerchant.workspace_id in(:id_workspace)")
    Single<List<WorkspaceAndMerchant>> getMerchantsInWorkspace(String... id_workspace);

    @Query("SELECT Workspace.id as w_id, Workspace.label as w_label, Workspace.pid as w_pid, Workspace.status_id as w_id_status, InfoAction.*, Merchant.* from WorkspaceMerchant INNER JOIN merchant on Merchant.id = WorkspaceMerchant.merchant_id INNER JOIN Workspace ON Workspace.id=WorkspaceMerchant.workspace_id left JOIN InfoAction ON InfoAction.i_id_merchant=WorkspaceMerchant.merchant_id and InfoAction.i_date =:date and InfoAction.i_id_workspace = WorkspaceMerchant.workspace_id where WorkspaceMerchant.workspace_id in(:id_workspaces) ORDER BY Merchant.id")
    Single<List<WorkspaceAndMerchant>> getMerchantsInWorkspace(List<String> id_workspaces, String date);

    @Query("SELECT Workspace.id as w_id, Workspace.label as w_label, Workspace.pid as w_pid, Workspace.status_id as w_id_status, InfoAction.*, Merchant.*, WorkspaceMerchant.* from WorkspaceMerchant INNER JOIN merchant on Merchant.id = WorkspaceMerchant.merchant_id INNER JOIN Workspace ON Workspace.id=WorkspaceMerchant.workspace_id  left JOIN InfoAction ON InfoAction.i_id_merchant = WorkspaceMerchant.merchant_id  where WorkspaceMerchant.workspace_id =:id_workspace and WorkspaceMerchant.merchant_id =:id_merchant")
    WorkspaceAndMerchant getWorkspaceAndMerchants(String id_merchant, String id_workspace);

    @Query("SELECT * FROM PhotoG")
    Single<List<PhotoG>> allPhoto();

    @Query("select * from InfoAction where i_id_merchant=:id_merchant and i_id_workspace =:id_workspace and i_date =:date")
    InfoAction getInfoAction(String id_merchant, String id_workspace, String date);

    @Query("select * from User where id = :id")
    User getUserById(String id);

    @Update
    void updateUser(User... users);

    @Delete
    void removeUser(User... users);

    @Delete
    void removeUserWorkspaces(List<MyWorkspace> myWorkspaces);

    @Query("select * from myworkspace where employee_id=:id_employee")
    List<MyWorkspace> getUserWorkspaceForLogOut(String id_employee);

    // file_report_types
    @Insert
    void insertFileReportType(List<FileReportType> fileReportTypes);

    @Query("select * from FileReportType")
    Single<List<FileReportType>> getFileReportTypes();


}
