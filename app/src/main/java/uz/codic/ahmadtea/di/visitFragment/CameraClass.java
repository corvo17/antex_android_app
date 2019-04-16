package uz.codic.ahmadtea.di.visitFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.db.entities.PhotoG;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;


public class CameraClass {

    Context context;
    List<PhotoG> allData;

    AppDataManager dbManager;

    List<String> allCommentStrings;
    String CompressionPath;
    private static final int IMAGE_REQ_CODE = 107;

    File base;

    File OldimgFile;

    VisitFragment fragment;

    CompositeDisposable compositeDisposable;

    public CameraClass(Context context,AppDataManager dataManager,VisitFragment fragment) {
        this.context = context;
        this.dbManager = dataManager;
        this.fragment=fragment;
        compositeDisposable = new CompositeDisposable();
        base = new File("sdcard/AhmadTeaImage/Compressed");
        updateData();
    }

    public boolean isEmptyy() {
        updateData();
        return allData == null || allData.isEmpty();
    }

    public void updateData(){
        compositeDisposable.add(dbManager.allPhoto()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data->{
                    allData = data;
                }));
    }

    private void sendImage(String desc,File file){

        RequestBody reqBody = RequestBody.create(MediaType.parse("*/*"),file);

        PhotoG myphoto = new PhotoG();

        MultipartBody.Part myFileToUpload = MultipartBody.Part.createFormData("imgProd",file.getName(),reqBody);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),desc);

        dbManager.uploadeBook(description,myFileToUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        Toast.makeText(context,"Successfully send",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,"Error to senda: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void writeDb(PhotoG myphoto){
        dbManager.insertPhoto(myphoto);
    }

    public void capture(String imgTitle){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imgFile = getPath(imgTitle);
        Uri fileUri;
        /**
         * Check whether android version greater than lolipop
         */
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            String author = context.getApplicationContext().getPackageName()+".fileprovider";
            fileUri = FileProvider.getUriForFile(context,author,imgFile);
        }else{
            fileUri = Uri.fromFile(imgFile);
        }
        camera.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        OldimgFile=imgFile;
        fragment.startActivityForResult(camera,IMAGE_REQ_CODE);
    }

    String namePhoto;
    private File getPath(String nameFolder){
        namePhoto = nameFolder;
        File Basefolder = new File("sdcard/AhmadTeaImage");
        if(!Basefolder.exists())
            Basefolder.mkdir();

        File folder = new File(Basefolder,nameFolder);
        if (!folder.exists())
            folder.mkdir();

        //region Compression
        File compressed = new File(Basefolder,"/Compressed");
        if(!compressed.exists())
            compressed.mkdir();

        File compressedFolder = new File(compressed,nameFolder);
        if(!compressedFolder.exists())
            compressedFolder.mkdir();

        CompressionPath = "sdcard/AhmadTeaImage/Compressed/"+nameFolder;
        //endregion

        File image = new File(folder,System.currentTimeMillis()+".jpg");
        return image;
    }

    public void onResultActivity(){
        try {
            File compressedImage = new Compressor(context)
                    .setMaxWidth(640)
                    .setMaxHeight(420)
                    .setQuality(70)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(CompressionPath)
                    .compressToFile(OldimgFile);

             PhotoG photoG = new PhotoG();
             photoG.setLocation(compressedImage.getAbsolutePath());
             writeDb(photoG);
        } catch (IOException e) {
            Toast.makeText(context,"Hey what is your name",Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(context,"Captured",Toast.LENGTH_SHORT).show();
    }

    public void sendAllImages(){
        updateData();
       for(PhotoG x : allData){
           sendImage("Hey",new File(x.getLocation()));
       }
    }
}
