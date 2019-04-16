package uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.CameraFragmentView;

public class CameraAdapter extends RecyclerView.Adapter<CameraViewHolde>{

    List<CameraModule> allImages;
    CameraFragmentView context;

    public CameraAdapter(List<CameraModule> allImages, CameraFragmentView activity) {
        this.allImages = allImages;
        context = activity;
    }

    @NonNull
    @Override
    public CameraViewHolde onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CameraViewHolde(LayoutInflater.
                from(viewGroup.getContext())
                .inflate(R.layout.camera_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolde cameraViewHolde, int i) {
        File imageFile = allImages.get(i).getImage();
       // File imageFile = new File("sdcard/AhmadTeaImage/kotlin/1549178085323.jpg");
        Log.d("MakhmudjonPath",imageFile.getAbsolutePath());
        Bitmap myImage = BitmapFactory.decodeFile(imageFile.getPath());
        cameraViewHolde.imageIllustrater.setImageBitmap(myImage);
        cameraViewHolde.titleImage.setText(allImages.get(i).getTitle());
        cameraViewHolde.itemView.setOnClickListener(action->{
            context.openImage(imageFile);
        });
    }

    public void loadImages(List<CameraModule> allImages){
        this.allImages = allImages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allImages!=null)
            return allImages.size();

        return 0;
    }
}
