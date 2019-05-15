package uz.codic.ahmadtea.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.favre.lib.crypto.bcrypt.BCrypt;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.network.model.Login;
import uz.codic.ahmadtea.ui.MainActivity;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.utils.Consts;

public class LoginActivity extends BaseActivity implements LoginMvpView {


    ImageView btn_back;
    ImageView btn_eye;
    EditText edtxt_password;
    EditText edtxt_userName;
    EditText edtxt_code;
    RelativeLayout btn_add;
    Button txt_add;
    ProgressBar progressBar;
    LinearLayout lnl_code;
    boolean is_eye_open;
    String error_label = null;
    boolean isFirstTime;

    LoginPresenter<LoginMvpView> presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
        }

        is_eye_open = true;
        bindViews();

        presenter = new LoginPresenter<>(this);
        presenter.onAttach(this);
        //presenter.checkErrors();

        isFirstTime = getIntent().getBooleanExtra("isFirstTime", true);
        error_label = getIntent().getStringExtra("error_label");
        if (isFirstTime) {
            btn_back.setVisibility(View.GONE);
            presenter.checkUser();
            // generatePrivateHash();
        } else {
            lnl_code.setVisibility(View.GONE);
        }


    }

    private String generatePrivateHash() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        String password = Consts.KEY + dateString;
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public void bindViews() {
        btn_eye = findViewById(R.id.btn_eye);
        edtxt_password = findViewById(R.id.edtxt_password);
        edtxt_userName = findViewById(R.id.edtxt_username);
        edtxt_code = findViewById(R.id.edtxt_code);
        btn_back = findViewById(R.id.btn_back1);
        btn_add = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progress_bar);
        txt_add = findViewById(R.id.txt_add);
        lnl_code = findViewById(R.id.lnl_code);

    }

    public void onBtnAddClick(View view) {
        if (isAllFieldsFilled()) {
            if (lnl_code.getVisibility() == View.VISIBLE) {
                hideKeyboard(this);
                txt_add.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                new MyTask().execute();
            } else {
                goLogin();
            }
        } else {
            showMessage("All fields must be filled");
        }
    }

    private void goLogin() {
        Login login = new Login();
        login.setLogin(edtxt_userName.getText().toString());
        login.setPassword(edtxt_password.getText().toString());
        login.setImei(getImei());

        //show loading
        hideKeyboard(this);
        txt_add.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (error_label == null) {
            presenter.userCheckFromDb(login);
        } else {
            presenter.onRequestResetToken(login);
        }
    }

    private boolean isAllFieldsFilled() {
        if (lnl_code.getVisibility() == View.VISIBLE) {
            return !edtxt_userName.getText().toString().trim().isEmpty() &&
                    !edtxt_password.getText().toString().trim().isEmpty() && !edtxt_code.getText().toString().trim().isEmpty();
        } else {
            return !edtxt_userName.getText().toString().isEmpty() &&
                    !edtxt_password.getText().toString().isEmpty();
        }
    }

    public void onBtnBackClick(View view) {
        finish();
    }

    public void onBtnEyeClick(View view) {
        if (is_eye_open) {
            edtxt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edtxt_password.setSelection(edtxt_password.length());
            is_eye_open = false;
            changeEyeIcon();
        } else {
            edtxt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edtxt_password.setSelection(edtxt_password.length());
            is_eye_open = true;
            changeEyeIcon();
        }
    }

    public void changeEyeIcon() {
        btn_eye.setImageResource(is_eye_open ? R.drawable.ic_eye : R.drawable.ic_eye_hidden_design_black_interface_symbol);
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String telInfo = telephonyManager.getDeviceId();
        if (telInfo != null) {
            return telInfo;
        } else
            return "352717092485070";
        //TODO remove else

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        txt_add.setVisibility(View.VISIBLE);

    }

    @Override
    public void onStay() {

    }

    @Override
    public void dontStay() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onResponseCentral() {
        goLogin();
    }

    @Override
    public void onBackPressed() {
        if (isFirstTime) {
            finishAffinity();
        } else super.onBackPressed();
    }

    private class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            long a = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = format.format(new Date());
            String password = Consts.KEY + dateString;
            String code = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            long b = System.currentTimeMillis();
            Log.d("GGG", "vaqt " + (b - a));
            return code;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            presenter.onRequestBaseUrl(edtxt_code.getText().toString(), s);
        }
    }

}
