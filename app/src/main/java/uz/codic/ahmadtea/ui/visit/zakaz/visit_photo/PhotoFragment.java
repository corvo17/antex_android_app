package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.VisitPhoto;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.PhotosAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter.CameraAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

import static uz.codic.ahmadtea.utils.Consts.paymentTag;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

@SuppressLint("ValidFragment")
public class PhotoFragment extends BaseFragment implements PhotoFragmentView {

    Integer id;
    String photo_title;
    private OnFragmentInteractionListener mListener;
    File file;
    int TAKE_PHOTO_CODE = 14;
    private String destinationDirectoryPath;
    PhotoFragmentPresenterView<PhotoFragmentView> presenter;
    RecyclerView recyclerView;
    VisitPhotoAdapter adapter;


    public static PhotoFragment newInstance(Integer id, String photo_title) {

        PhotoFragment photoFragment = new PhotoFragment(id, photo_title);

        return photoFragment;
    }

    @SuppressLint("ValidFragment")
    public PhotoFragment(Integer id, String photo_title) {
        this.id = id;
        this.photo_title = photo_title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_vizit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView cameraBtn = getActivity().findViewById(R.id.cameraBtn);
        getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> {
            mListener.transactionFragments(VisitFragment.newInstance(), visitTag);
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.GONE);
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.VISIBLE);
            cameraBtn.setVisibility(View.GONE);
        });
        checkFolder();

        presenter = new PhotoFragmentPresenter<>(getContext());
        presenter.onAttach(this);
        cameraBtn.setVisibility(View.VISIBLE);
        cameraBtn.setOnClickListener(v ->{
            openCamera();
        });

        recyclerView = view.findViewById(R.id.recycler_view_visit_photo);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new VisitPhotoAdapter();
        recyclerView.setAdapter(adapter);
        presenter.getVisitPhotos(mListener.getCompleteObject().getMerchant().getId(), mListener.getCompleteObject().getWorkspace().getId());


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResponseAllVisitPhotos(List<VisitPhoto> visitPhotos) {
        adapter.setPhotos(visitPhotos);
    }

    private void checkFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "AntEx");
        boolean success = true;
        if (!folder.exists()){
            success = folder.mkdir();
            folder = new File(Environment.getExternalStorageDirectory() + File.separator + "AntEx/Camera");
            folder.mkdir();
            folder = new File(Environment.getExternalStorageDirectory() + File.separator + "AntEx/new Photos");
            folder.mkdir();
        }

    }

    private void openCamera() {
        String mPath ="sdcard/AntEx/Camera/";
        File folder = new File(mPath);
        String name = System.currentTimeMillis() + ".jpg";
        File photo = new File(folder,name);
        file = photo;
//        try {
//            photo.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        String author = this.getApplicationContext().getPackageName()+".fileprovider";
//        Uri uri  = FileProvider.getUriForFile(this,author,photo);

        Uri uri;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            String author = getActivity().getApplicationContext().getPackageName()+".fileprovider";
            uri = FileProvider.getUriForFile(getContext(),author,photo);
        }else{
            uri = Uri.fromFile(photo);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, TAKE_PHOTO_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("baxtiyor", "onActivityResult: ");
        if (requestCode == TAKE_PHOTO_CODE && resultCode ==getActivity().RESULT_OK){
            //compressPhoto();
            Log.d("baxtiyor", "onActivityResult: ");
            try {
                goPhotoCompress();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void goPhotoCompress() throws IOException {
        FileOutputStream fileOutputStream = null;
        destinationDirectoryPath = "sdcard/AntEx/new Photos/" + File.separator + file.getName();

        File compressFile = new File("sdcard/AntEx/new Photos/").getParentFile();
        VisitPhoto visitPhoto = new VisitPhoto();
        visitPhoto.setPhoto_url(destinationDirectoryPath);
        visitPhoto.setPhoto_path(compressFile.getPath());
        visitPhoto.setMerchant_id(mListener.getCompleteObject().getMerchant().getId());
        visitPhoto.setWorkspace_id(mListener.getCompleteObject().getWorkspace().getId());
        visitPhoto.setVisit_id(mListener.getCompleteApi().getVisitObject().getId());
        visitPhoto.setPhoto_title(photo_title);
        visitPhoto.setPhoto_title_id(id);
        presenter.insertVisitPhoto(visitPhoto);
        if (!compressFile.exists()){
            compressFile.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationDirectoryPath);
            Bitmap bitmap =   decodeSampledBitmapFromFile();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            presenter.getVisitPhotos(mListener.getCompleteObject().getMerchant().getId(), mListener.getCompleteObject().getWorkspace().getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
    }

    private Bitmap decodeSampledBitmapFromFile() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

//----------

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        int height;
        int width;
        int isSamplesize = 1;

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Log.d("baxtiyor", "actualHeight: " + options.outHeight);
        Log.d("baxtiyor", "actualWidth: " + options.outWidth);
        if (actualWidth > actualHeight){
            width = 1000;
            height = actualHeight * 1000 / actualWidth;
        }else {
            height = 1000;
            width = actualWidth * 1000 / actualHeight;
        }
        Log.d("baxtiyor", "height: " + height);
        Log.d("baxtiyor", "width: " + width);
        Log.d("baxtiyor", "inSimpleSize: " + options.inSampleSize);
        if (actualWidth > width){
            int count = actualWidth/width;
            while (isSamplesize*2 <= count){
                isSamplesize *=2;
            }
            Log.d("baxtiyor", "count: " + actualWidth/width);
            Log.d("baxtiyor", "decodeSampledBitmapFromFile: " + isSamplesize);

        }
        options.inSampleSize = isSamplesize;
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

//----------

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);




        return  bitmap;
    }

}
