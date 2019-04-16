package uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import uz.codic.ahmadtea.R;

public class CameraViewHolde extends RecyclerView.ViewHolder {

    public SelectableRoundedImageView imageIllustrater;
    TextView titleImage;

    public CameraViewHolde(@NonNull View itemView) {
        super(itemView);
        imageIllustrater = itemView.findViewById(R.id.StoredImages);
        titleImage = itemView.findViewById(R.id.titleImage);
    }
}
