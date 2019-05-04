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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

import static android.app.Activity.RESULT_OK;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

@SuppressLint("ValidFragment")
public class PhotoFragment extends BaseFragment implements PhotoFragmentView {

    Integer id;
    private OnFragmentInteractionListener mListener;
    File file;
    int TAKE_PHOTO_CODE = 14;
    private String destinationDirectoryPath;


    public static PhotoFragment newInstance(Integer id) {

        PhotoFragment photoFragment = new PhotoFragment(id);

        return photoFragment;
    }

    @SuppressLint("ValidFragment")
    public PhotoFragment(Integer id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_vizit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.id_text);
        textView.setText(String.valueOf(id));
        ImageView cameraBtn = getActivity().findViewById(R.id.cameraBtn);
        getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> {
            mListener.transactionFragments(VisitFragment.newInstance(), visitTag);
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.GONE);
            cameraBtn.setVisibility(View.GONE);
        });
        checkFolder();

        cameraBtn.setVisibility(View.VISIBLE);
        cameraBtn.setOnClickListener(v ->{
            openCamera();
        });
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
                testCompress();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void testCompress() throws IOException {
        FileOutputStream fileOutputStream = null;
        destinationDirectoryPath = "sdcard/AntEx/new Photos/" + File.separator + file.getName();

        File compressFile = new File("sdcard/AntEx/new Photos/").getParentFile();
        if (!compressFile.exists()){
            compressFile.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationDirectoryPath);
            Bitmap bitmap =   decodeSampledBitmapFromFile();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
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
