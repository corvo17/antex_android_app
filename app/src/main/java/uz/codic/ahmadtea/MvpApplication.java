package uz.codic.ahmadtea;

import android.app.Application;

import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.errors.ExceptionHandler;


public class MvpApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ErrorClass.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }

}
