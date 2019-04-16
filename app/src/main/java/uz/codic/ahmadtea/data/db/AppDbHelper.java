package uz.codic.ahmadtea.data.db;


import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * This class is not used until we add Dagger 2
 * */
 public class AppDbHelper{

    private final String DB_NAME = "AhmadTea";

    DbHelper dbHelper;

    public AppDbHelper(Context context) {
        dbHelper = Room.databaseBuilder(context,
                                    DbHelper.class,
                                    DB_NAME)
                                    .fallbackToDestructiveMigration()
                                    .allowMainThreadQueries()
                                    .build();
    }

//    @Override
//    public Single<Integer> isUserAlreadyLoggedIn(final String login) {
//        return  dbHelper.daoAccess().isUserAlreadyLoggedIn(login);
//    }
//
//    @Override
//    public long isMerchantListFull() {
//        return 0;
//    }
//
//    @Override
//    public void insertUser(User user) {
//
//    }
//
//    @Override
//    public void insertWorkspace(List<Workspace> workspaces) {
//
//    }
//
//    @Override
//    public Single<List<Merchant>> getMerchantsInWorkspace(String id) {
//        return null;
//    }
//
//    @Override
//    public void insertMmds(List<Mmd> Mmd) {
//
//    }
//
//    @Override
//    public void insertComments(List<Comment> comments) {
//
//    }
//
//    @Override
//    public void insertMerchant(final List<Merchant> merchants) {
//        Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                dbHelper.daoAccess().insertMerchant(merchants);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }
//
//    @Override
//    public void insertProductPrices(List<ProductPrice> productPrices) {
//
//    }
//
//    @Override
//    public void insertProducts(List<Product> products) {
//
//    }
//
//    @Override
//    public void insertPaymentType(final List<PaymentType> paymentTypes) {
//        Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                dbHelper.daoAccess().insertPaymentType(paymentTypes);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(Consts.TEST_TAG, "onError: PT" + e.getMessage());
//                    }
//                });
//    }
//
//    @Override
//    public void insertPrice(final List<Price> prices) {
//        Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                dbHelper.daoAccess().insertPrice(prices);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(Consts.TEST_TAG, "onError: P" + e.getMessage());
//                    }
//                });
//    }
//
//
//    @Override
//    public Single<List<User>> getAllUsers() {
//        return dbHelper.daoAccess().getAllUsers();
//    }
//
//    @Override
//    public Single<List<Merchant>> getAllMerchants() {
//        return dbHelper.daoAccess().getAllMerchants();
//    }
//
//    @Override
//    public Single<Merchant> getMerchantById(int id) {
//        return dbHelper.daoAccess().getMerchantById(id);
//    }
}
