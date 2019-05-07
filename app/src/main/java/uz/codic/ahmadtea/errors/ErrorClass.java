package uz.codic.ahmadtea.errors;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.nio.channels.Channel;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.MvpApplication;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.db.entities.ErrorInfo;
import uz.codic.ahmadtea.data.network.model.ErrorBody;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.error_activity.ErrorActivity;
import uz.codic.ahmadtea.utils.Consts;

public class ErrorClass{

    private static AppDataManager appDataManager;

    private static Context context;

    public static void init(Context context){
        ErrorClass.context = context;
        ErrorClass.appDataManager = new AppDataManager(context);

    }

    @SuppressLint("NewApi")
    public static void log(String message, Exception exception, String error_id) {
        //log into db
        //send
        exception.printStackTrace();
        String id = UUID.randomUUID().toString();
        Log.d("baxtiyor", "mesage: " + message);
        Log.d("baxtiyor", "id: " + id);
        Log.d("baxtiyor", "API_version: " + Build.VERSION.SDK_INT);
        Log.d("baxtiyor", "OS_version: " + Build.VERSION.RELEASE);
        Log.d("baxtiyor", "OS_version: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        Log.d("baxtiyor", "active_internet_connection: " + isOnline());
        Log.d("baxtiyor", "timestamp: " + System.currentTimeMillis());
        Log.d("baxtiyor", "error: " + exception.getMessage());
        Log.d("baxtiyor", "error_type: " + exception.getClass().getCanonicalName());
        Log.d("baxtiyor", "error_toSting: " + exception.toString());
        for (StackTraceElement stackTraceElement :exception.getStackTrace()) {
            Log.d("baxtiyor", "error_printStackTrace: " + stackTraceElement.toString());
        }
        openNotifi(message, id);
        sendError(message, id, exception, error_id);
    }

    public static void log(String message, Exception exception){
        log(message, exception, null);
    }

    public static void log(Exception exception) {
        //log into db
        //send
        exception.printStackTrace();
        String id = UUID.randomUUID().toString();
        String log = null;
        Log.d("baxtiyor", "id: " + id);
        Log.d("baxtiyor", "API_version: " + Build.VERSION.SDK_INT);
        Log.d("baxtiyor", "OS_version: " + Build.VERSION.RELEASE);
        Log.d("baxtiyor", "OS_version: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        Log.d("baxtiyor", "active_internet_connection: " + isOnline());
        Log.d("baxtiyor", "timestamp: " + System.currentTimeMillis());
        Log.d("baxtiyor", "error: " + exception.getMessage());
        Log.d("baxtiyor", "error_type: " + exception.getClass().getCanonicalName());
        Log.d("baxtiyor", "error_toSting: " + exception.toString());
        for (StackTraceElement stackTraceElement :exception.getStackTrace()) {
            Log.d("baxtiyor", "error_printStackTrace: " + stackTraceElement.toString());
            log += stackTraceElement.toString() + "\n";
        }

        openNotifi(id);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setApi_version(String.valueOf(Build.VERSION.SDK_INT));
        errorInfo.setOs_version(String.valueOf(Build.VERSION.RELEASE));
        errorInfo.setDevice_model(Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        errorInfo.setId(id);
        errorInfo.setActive_internet_connection(isOnline());
        errorInfo.setErro_log(log);
        errorInfo.setError_message(exception.getMessage());
        errorInfo.setTimestamp(System.currentTimeMillis());
        errorInfo.setSent(false);
        appDataManager.insertErrorInfo(errorInfo);

    }

    @SuppressLint("NewApi")
    public static void openNotifi(String message, String id){
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);

        final Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .setContentText("Произошла ошибка!\n" +
                        "код: общая ошибка\n" +
                        "№: " +id + "\n" +
                        "\n" +
                        "просим синхронизировать приложение\n" +
                        "и отправить скриншот этой ошибки\n" +
                        "в техподдержку,\n" +
                        "телеграм: +998908228585")
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(1, builder.build());
    }

    @SuppressLint("NewApi")
    public static void openNotifi(String id){
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);

        final Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("message", "");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .setContentText("Произошла ошибка!\n" +
                        "код: общая ошибка\n" +
                        "№: " +id + "\n" +
                        "\n" +
                        "просим синхронизировать приложение\n" +
                        "и отправить скриншот этой ошибки\n" +
                        "в техподдержку,\n" +
                        "телеграм: +998908228585")
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(1, builder.build());
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void sendError(String id, Exception e){
        ErrorObject errorObject = new ErrorObject();
        ErrorBody body = new ErrorBody();
        body.setAPI_version(String.valueOf(Build.VERSION.SDK_INT));
        body.setOS_version(String.valueOf(Build.VERSION.RELEASE));
        body.setDevice_model(Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        body.setTimestamp(System.currentTimeMillis());
        body.setError_log(e.getMessage());
        body.setActive_internet_connection(isOnline());
        errorObject.setBody(body);
        errorObject.setId(id);
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public static void sendError(String message, String id, Exception e, String id_error){

        String log = null;
        for (StackTraceElement stackTraceElement :e.getStackTrace()) {
            log += stackTraceElement.toString() + "\n";
        }

        ErrorObject errorObject = new ErrorObject();
        ErrorBody body = new ErrorBody();
        body.setAPI_version(String.valueOf(Build.VERSION.SDK_INT));
        body.setOS_version(String.valueOf(Build.VERSION.RELEASE));
        body.setDevice_model(Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        body.setTimestamp(System.currentTimeMillis());
        body.setError_log(log);
        body.setError_message(message);
        body.setError_id(id_error);
        body.setActive_internet_connection(isOnline());
        errorObject.setBody(body);
        errorObject.setId(id);
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }



}
