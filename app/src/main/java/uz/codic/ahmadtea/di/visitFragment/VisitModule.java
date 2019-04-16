package uz.codic.ahmadtea.di.visitFragment;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

@Module
public class VisitModule {

    Context context;
    AppDataManager dbManager;
    VisitFragment fragment;

    public VisitModule(Context context,VisitFragment fr) {
        this.context = context;
        this.fragment =fr;
        dbManager = new AppDataManager(context);
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context getContext()
    {
        return context;
    }

    @Provides
    @ActivityScope
    public AppDataManager getDatamanager()
    {
        return this.dbManager;
    }

    @Provides
    @ActivityScope
    public CameraClass getCamera(){
        return new CameraClass(context,dbManager,fragment);
    }

    @Provides
    @ActivityScope
    public VisitFragment getFragment(){
        return fragment;
    }



}
