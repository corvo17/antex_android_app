package uz.codic.ahmadtea.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uz.codic.ahmadtea.di.ApplicationContext;

//Application Module has to provide all Data related dependencies
@Module
public class ApplicationModule {

    //This reference is used to provide Context dependency
    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    //1. To provide application Context for databases
    //2. To provide Application instance

    @Provides
    @ApplicationContext
    Context provideApplicationContext(){
        return mApplication;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }


    //All singleton classes should be Provided
    //1.AppDataManager
    //2.ApiClient
    //3.DbHelper


}