package uz.codic.ahmadtea.ui.visit.zakaz.camera;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.PhotoG;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter.CameraModule;

public class CameraFragmentPresenter<v extends CameraFragmentView> extends BasePresenter<v> implements CameraFragmentPresenterView<v> {

    List<String> allPaths;


    public CameraFragmentPresenter(Context context) {
        super(context);
        allPaths = new ArrayList<>();
        allPaths.add("sdcard/AhmadTeaImage/Compressed/kotlin");
        allPaths.add("sdcard/AhmadTeaImage/Compressed/java");
        allPaths.add("sdcard/AhmadTeaImage/Compressed/android");
        allPaths.add("sdcard/AhmadTeaImage/Compressed/allofthem");
    }

    @Override
    public void LoadImages() {
        List<CameraModule> allmoduls = new ArrayList<>();
        //region Get Files from Storage
       /* for(int i=0;i< allPaths.size();i++){
            File image = new File(allPaths.get(i));
                if(image.exists()){
                    for(File y: image.listFiles()){
                        allmoduls.add(new CameraModule(getString(i),image));
                    }
                }
        }*/
       //endregion
        getDisposable().add(getDataManager().allPhoto()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io()).subscribe(data->{
                    for(PhotoG x : data){
                        allmoduls.add(new CameraModule("Hey",new File(x.getLocation())));
                    }
                    getMvpView().loadDataImage(allmoduls);
                }));
    }

    public List<CameraModule> AllImages() {
        List<CameraModule> allmoduls = new ArrayList<>();
        for(int i=0;i< allPaths.size();i++){
            File image = new File(allPaths.get(i));
            if(image.exists()){
                for(File y: image.listFiles()){
                    allmoduls.add(new CameraModule(getString(i),y));
                }
            }
        }
        return allmoduls;
    }


    @Override
    public String getString(int index) {
        switch (index){
            case 0:
                return "Kotlin";
            case 1:
                return "Java";
            case 2:
                return "Android";
            case 3:
                return "All of them";
        }
        return "Empty";
    }


}
