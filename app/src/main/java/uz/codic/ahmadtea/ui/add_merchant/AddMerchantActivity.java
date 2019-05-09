package uz.codic.ahmadtea.ui.add_merchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.MapsActivity;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;

public class AddMerchantActivity extends BaseActivity implements AddMerchantMvpView, OnMapReadyCallback {

    private GoogleMap mMap;
    private int REQUEST_CODE = 1414;

    AddMerchantMvpPresenter<AddMerchantMvpView> presenter;
    EditText et_name, et_address, et_phone, et_inn;
    ImageView back;
    TextView  tv_name_value, tv_address_value, tv_phone_value, tv_inn_value, tv_some_text;
    ImageView img_add;
    LinearLayout linearLayoutEdit;
    RelativeLayout relativeLayoutInfo;
    Button visitbn, orderbn;
    NewMerchant merchant;
    Spinner spinner;
    List<Workspace> workspaces;
    boolean isCreateMerchant = false;
    ErrorClass errorClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merchant);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!isOnline()) {
            showMessage("Please Internet Turn On");
        }
        init();
    }

    private void init() {
        presenter = new AddMerchantPresenter<>(this);
        presenter.onAttach(this);

        et_name = findViewById(R.id.et_add_merchant_name);
        et_address = findViewById(R.id.et_add_merchant_address);
        et_phone = findViewById(R.id.et_add_merchant_phone);
        et_inn = findViewById(R.id.et_add_merchant_inn);
        //tv
        img_add = findViewById(R.id.img_add_merchat);
        tv_name_value = findViewById(R.id.id_add_merchant_name_value);
        tv_address_value = findViewById(R.id.id_add_merchant_address_value);
        tv_phone_value = findViewById(R.id.id_add_merchant_phone_value);
        tv_inn_value = findViewById(R.id.id_add_merchant_inn_value);
        tv_some_text = findViewById(R.id.id_add_merchant_some_text);

        linearLayoutEdit = findViewById(R.id.id_lnl_input_add_merchant);
        relativeLayoutInfo = findViewById(R.id.id_add_merchant_info);

        visitbn = findViewById(R.id.id_add_merchant_visit);
        orderbn = findViewById(R.id.id_add_merchant_order);


        back = findViewById(R.id.id_add_merchant_back);
        spinner = findViewById(R.id.id_add_merchant_spinner);
        merchant = new NewMerchant();
        merchant.setId(String.valueOf(UUID.randomUUID()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCreateMerchant) {
                    if (validate()) {
                        setMerchantFilts();
                    } else {
                        showMessage("complate name or address");
                    }
                }
            }
        });
        et_inn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (validate()) {
                        setMerchantFilts();
                    } else {
                        showMessage("complate name or address");
                    }
                    return true;
                }
                return false;
            }
        });

        presenter.getMyWorkspaces();

        //spinner;

        visitbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("onClick");

            }
        });

        orderbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("order");
            }
        });
    }

    private void openActivity(String value) {
        Intent intent = new Intent(this, MerchantActivity.class);
        intent.putExtra("name", merchant.getName());
        intent.putExtra("id_merchant", merchant.getId());
        intent.putExtra("id_workspace", merchant.getId_workspace());
        intent.putExtra("click", value);
        startActivity(intent);
        finish();
    }

    private void setMerchantFilts() {
        merchant.setAddress(et_address.getText().toString());
        merchant.setName(et_name.getText().toString());
        if (et_phone.getText().toString().trim().isEmpty()) {
            merchant.setPhone(null);
        } else merchant.setPhone(Integer.valueOf(et_phone.getText().toString()));
        if (et_inn.getText().toString().trim().isEmpty()) {
            merchant.setInn(null);
        } else merchant.setInn(et_inn.getText().toString());
        if (isOnline()) {
            presenter.requestAddMerchant(merchant);
        }else {
            presenter.seveMerchant(merchant);
            hideEdit();
            showInfo();
            tv_some_text.setText("Merchant yaratildi lekin serverga jonatilmadi");

        }
    }

    private boolean validate() {
        if (et_name.getText().toString().trim().isEmpty()) return false;
        if (et_address.getText().toString().trim().isEmpty()) return false;
        return true;
    }

    @Override
    public void onMyWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
        merchant.setId_workspace(workspaces.get(0).getId());
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < workspaces.size(); i++) {
            items.add(workspaces.get(i).getLabel());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                merchant.setId_workspace(workspaces.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void resultAddMerchant() {
            //TODO add merchant success
            presenter.seveMerchant(merchant);
            hideEdit();
            showInfo();
            tv_some_text.setText("");
            isCreateMerchant = true;
    }

    private void showInfo() {
        relativeLayoutInfo.setVisibility(View.VISIBLE);
        tv_name_value.setText(merchant.getName());
        tv_address_value.setText(merchant.getAddress());
        if (merchant.getPhone() != null) {
            tv_phone_value.setText(merchant.getPhone().toString());

        } else tv_phone_value.setText("");
        if (merchant.getInn() != null) {
            tv_inn_value.setText(merchant.getInn().toString());
        } else tv_inn_value.setText("");
    }

    private void hideEdit() {
        linearLayoutEdit.setVisibility(View.GONE);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void goMapActiity(View view) {
        startActivityForResult(new Intent(this, MapsActivity.class), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                double longlitude = data.getDoubleExtra("longitude", 14);
                double latitude = data.getDoubleExtra("latitude", 14);
                merchant.setLatitude(latitude);
                merchant.setLongitude(longlitude);
                mMap.clear();
                LatLng citizenLocation = new LatLng(data.getDoubleExtra("latitude", 41.311162), data.getDoubleExtra("longitude", 69.279623));
                MarkerOptions marker = new MarkerOptions().position(citizenLocation).title("Location");
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(citizenLocation, 15f));
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(41.311081, 69.240562);
        mMap.addMarker(new MarkerOptions().position(location).title("Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.setMinZoomPreference(7f);

    }
}
