package uz.codic.ahmadtea.ui.visit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.MainActivity;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.visit.zakaz.InformationFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visit_info.MerchantPageFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.PaymentFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PricesFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.product.ProductFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.shippingDate.ShippingDateFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.warehouse.WareHouseFragment;
import uz.codic.ahmadtea.utils.CommonUtils;

import static uz.codic.ahmadtea.utils.Consts.informationTag;
import static uz.codic.ahmadtea.utils.Consts.paymentTag;
import static uz.codic.ahmadtea.utils.Consts.pricesTag;
import static uz.codic.ahmadtea.utils.Consts.productTag;
import static uz.codic.ahmadtea.utils.Consts.shippingTag;
import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.visitTag;
import static uz.codic.ahmadtea.utils.Consts.warehouseTag;

public class MerchantActivity extends BaseActivity
        implements OnFragmentInteractionListener, MerchantMvpView {

    Toolbar merchantToolbar;
    TextView toolbarTitle;
    ImageView btnBack;
    ImageView btn_forward;
    LinearLayout lnrButtons;

    String merchant_name;//merchantToolbar title
    int pid_merchant;//to get the full object of merchant
    String id_workspace;
    String stringClick;
    MerchantPresenter<MerchantMvpView> presenter;

    LocationManager locationManager;
    LocationListener mLocationListener;

    //Send Objects
    CompleteObject completeObject;
    CompleteApi completeApi;

    Button btnSaveAsDraft;
    CardView save_locally;
    InfoAction infoAction;

    StateProgressBar stateProgressBar;
    String[] descriptionData = {"Warehouse", "Price", "Products", "Payment", "Shipping"};
    private int REQUEST_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        merchantToolbar = findViewById(R.id.visit_toolbar);
        setSupportActionBar(merchantToolbar);

        stateProgressBar = findViewById(R.id.progress_state);
        stateProgressBar.setStateDescriptionData(descriptionData);

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                switch (stateNumber) {
                    case 1:
                        transactionFragments(WareHouseFragment.newInstance(), warehouseTag);
                        break;
                    case 2:
                        transactionFragments(PricesFragment.newInstance(), pricesTag);
                        break;
                    case 3:
                        if (getCompleteApi().getOrderObject().getId_price() != null) {
                            transactionFragments(ProductFragment.newInstance(), productTag);
                        } else {
                            showMessage("Please select price first");
                        }
                        break;
                    case 4:
                        transactionFragments(PaymentFragment.newInstance(), paymentTag);
                        break;
                    case 5:
                        transactionFragments(ShippingDateFragment.newInstance(), shippingTag);
                        break;
                }
            }
        });


        //Intent extra values
        stringClick = getIntent().getStringExtra("click");
        pid_merchant = getIntent().getIntExtra("id", -1);
        merchant_name = getIntent().getStringExtra("name");
        id_workspace = getIntent().getStringExtra("id_workspace");


        completeApi = new CompleteApi();
        completeApi.setOrderObject(new Order());
        completeApi.getOrderObject().setDate(CommonUtils.getToday());
        completeApi.setVisitObject(new Visit());
        completeApi.getVisitObject().setDate(CommonUtils.getToday());
        completeApi.setOrderBasketList(new ArrayList<>());

        //Presenter
        presenter = new MerchantPresenter<>(this);
        presenter.onAttach(this);
//        presenter.getMerchantById(pid_merchant);
        if (pid_merchant == -1) {
            String id_merchant = getIntent().getStringExtra("id_merchant");
            presenter.requestCompleteObject(id_merchant, id_workspace);

        } else presenter.requestCompleteObject(pid_merchant, id_workspace);
        //Ui

        btnBack = findViewById(R.id.btn_back);
        toolbarTitle = findViewById(R.id.merchant_tb_title);
        lnrButtons = findViewById(R.id.lnr_buttons);
        btnSaveAsDraft = findViewById(R.id.btn_send_as_draft);
//
        findViewById(R.id.btn_save_as_draft).setOnClickListener(v -> {
            if (!completeApi.getOrderBasketList().isEmpty() && getCompleteApi().getOrderObject().isOrderComplete()) {
                completeApi.getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
                presenter.saveAsPending(completeApi, statusSaveAsDraft);
            } else{
                showMessage("Please complete order");
            }
        });

//        if (stringClick.equals("longClick")) {
//            lnrButtons.setVisibility(View.GONE);
//            findViewById(R.id.btn_save_as_pending).setVisibility(View.GONE);
//            transactionFragments(InformationFragment.newInstance(), informationTag);
//        } else if (stringClick.equals("onClick")) {
//            transactionFragments(VisitFragment.newInstance(), visitTag);
//        }


        findViewById(R.id.bnt_send).setOnClickListener(v -> {
            if (!getCompleteApi().getOrderBasketList().isEmpty() && getCompleteApi().getOrderObject().isOrderComplete()) {
                getCompleteApi().getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
                presenter.requestSend(getCompleteApi());
            } else{
                showMessage("Please complete order");
            }

        });

        findViewById(R.id.btn_send_as_draft).setOnClickListener(v -> {
            if (!getCompleteApi().getOrderBasketList().isEmpty() && getCompleteApi().getOrderObject().isOrderComplete()) {
                getCompleteApi().getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
                presenter.requestSendDraft(completeApi);
            } else{
                showMessage("Please complete order");
            }
        });

        findViewById(R.id.btn_save_as_pending).setOnClickListener(v -> {
            if (!completeApi.getOrderBasketList().isEmpty() && getCompleteApi().getOrderObject().isOrderComplete()) {
                completeApi.getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
                presenter.saveAsPending(completeApi, statusPending);
            } else{
                showMessage("Please complete order");
            }
        });

        save_locally = findViewById(R.id.btn_save_as_draft);
        //save_locally.o

        if (!checkPermissionFromDevice())
            requestPermission();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Global 2 objects getters for all fragments
     * 1. CompleteObject
     * !!!!!!!!! Once completeObject ready Visit object starts
     * 2. CompletOrder
     */

    @Override
    public void onCompleteObjectReady(CompleteObject completeObject) {
        this.completeObject = completeObject;
        infoAction = presenter.getInfoAction(completeObject.getWorkspace().getId(), completeObject.getMerchant().getId());
        startVisit();
        if (infoAction.isAction()){
            transactionFragments(MerchantPageFragment.newInstance(), "Visit Info");
            lnrButtons.setVisibility(View.GONE);
        }else if (stringClick.equals("longClick")) {
            lnrButtons.setVisibility(View.GONE);
            findViewById(R.id.btn_save_as_pending).setVisibility(View.GONE);
            transactionFragments(InformationFragment.newInstance(), informationTag);
        } else if (stringClick.equals("onClick")) {
            transactionFragments(VisitFragment.newInstance(), visitTag);
        }else
        if (stringClick.equals("order")) {
            lnrButtons.setVisibility(View.GONE);
            stateProgressBar.setVisibility(View.VISIBLE);
            findViewById(R.id.btn_forward).setVisibility(View.VISIBLE);
            transactionFragments(PricesFragment.newInstance(), pricesTag);
        }

    }


    @Override
    public CompleteApi getCompleteApi() {
        return completeApi;
    }

    @Override
    public CompleteObject getCompleteObject() {
        return completeObject;
    }

    @Override
    public void openGallery(File file) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)//
                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                android.support.v4.content.FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file) : Uri.fromFile(file),
                        "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


    private void startVisit() {
        Visit visit = getCompleteApi().getVisitObject();
        visit.setId(UUID.randomUUID().toString());
        visit.setId_merchant(getCompleteObject().getMerchant().getId());
        visit.setId_comment(null);
        visit.setNotes(null);
        visit.setTime_start(System.currentTimeMillis());
        visit.setLatitude(0.0);
        visit.setLongitude(0.0);
        visit.setId_filial(getCompleteObject().getWorkspace().getFilial_id());
    }

    /**
     * Clicks for every fragments with toolbar title change
     */

    @Override
    public void onBackClick(String title) {
        changeToolbarTitle(title);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void transactionFragments(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .addToBackStack(tag)
                .replace(R.id.merchant_container, fragment, tag)
                .commit();
        changeToolbarTitle(tag);
    }

    private void changeToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }


    @Override
    public void onBackPressed() {
        if (completeApi.getOrderObject().getId_price() != null || completeApi.getOrderObject().getId_payment_type() != null || completeApi.getOrderBasketList().size() > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Olingan ma'lumotlarni eslab qolaymi");
            builder.setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goBack();
                }

            });
            builder.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO SAVE
                    completeApi.getVisitObject().setTime_end(CommonUtils.getCurrentTimeMilliseconds());
                    presenter.saveAsPending(completeApi, statusSaveAsDraft);
                    goBack();
                }
            });
            builder.show();

        } else {
            super.onBackPressed();
            finish();
        }

    }

    public void goBack(){
        super.onBackPressed();
        finish();
    }


    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getLocation() {
        int isLocation = 0;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                completeApi.getVisitObject().setLatitude(location.getLatitude());
                completeApi.getVisitObject().setLongitude(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3000, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 3000, mLocationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationListener != null) {
            locationManager.removeUpdates(mLocationListener);
        }

    }

    public Fragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (Fragment) getSupportFragmentManager().findFragmentByTag(tag);
    }


    private Boolean checkPermissionFromDevice() {
        int k = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return k == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public LinearLayout getLinearButtons() {
        return lnrButtons;
    }

    @Override
    public InfoAction getInfoAction() {
        return infoAction;
    }

    @Override
    public List<PhysicalWareHouse> getPhysicalWareHouse() {
        return presenter.getDataManager().getPhysicalWareHouses(completeObject.getWorkspace().getId());
    }
}
