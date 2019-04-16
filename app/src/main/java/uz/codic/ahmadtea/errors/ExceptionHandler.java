package uz.codic.ahmadtea.errors;

import android.app.Activity;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    //private final Activity myContext;


    public ExceptionHandler() {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ErrorClass.log((Exception) e);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
