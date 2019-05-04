package uz.codic.ahmadtea.errors.error_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.db.entities.ErrorInfo;
import uz.codic.ahmadtea.data.network.model.ErrorBody;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.utils.Consts;

public class ErrorActivity extends AppCompatActivity {

    TextView errortext;
    ImageView back;
    Toolbar toolbar;
    AppDataManager appDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errortext = findViewById(R.id.info_error);
        back = findViewById(R.id.back);
        toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("Ошибка");

        appDataManager = new AppDataManager(this);
        checkErrors();

        String id = getIntent().getStringExtra("id");
        String message = null;
        try {
            message = getIntent().getStringExtra("message");
        }catch (Exception e){
        }

        if (message != null){
            errortext.setText("Произошла ошибка!\n" +
                    "\n"+ message +"!\n" +
                    "\n"+
                    "№: " + id+ "\n" +
                    "\n"+
                    "просим синхронизировать приложение" +
                    "и отправить скриншот этой ошибки" +
                    "в техподдержку,\n" +
                    "\n"+
                    "телеграм: +998908228585");
        }else {
            errortext.setText("Произошла ошибка!\n" +"\n"+
                    "код: общая ошибка\n" +"\n"+
                    "№: " + id+ "\n" +"\n"+
                    "просим синхронизировать приложение" +
                    "и отправить скриншот этой ошибки" +
                    "в техподдержку,\n" +"\n"+
                    "телеграм: +998908228585");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorActivity.super.onBackPressed();
                finish();
            }
        });
    }

    private void checkErrors() {
        appDataManager.getErrorInfoIsntSent(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ErrorInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ErrorInfo> errorInfos) {
                        if (errorInfos.size() > 0){
                            sendErrors(errorInfos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void sendErrors(List<ErrorInfo> errorInfos){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ErrorInfo info:errorInfos) {
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
                    appDataManager.sendError(appDataManager.getToken(), errorObject)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<ApiObeject<ErrorObject>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(ApiObeject<ErrorObject> apiObeject) {
                                    if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count()>0){
                                        info.setSent(true);
                                        appDataManager.updateErrorInfo(info);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
