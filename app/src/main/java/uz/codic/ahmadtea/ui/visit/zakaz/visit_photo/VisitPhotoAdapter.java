package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.io.File;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.VisitPhoto;

class VisitPhotoAdapter extends RecyclerView.Adapter<VisitPhotoAdapter.Holder> {

    List<VisitPhoto> photos;

    public void setPhotos(List<VisitPhoto> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.camera_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        VisitPhoto visitPhoto = photos.get(holder.getAdapterPosition());
        File file = new File(visitPhoto.getPhoto_url());
        Bitmap photo = BitmapFactory.decodeFile(file.getPath());
        Log.d("photo", " path: " + visitPhoto.getPhoto_path());
        Log.d("photo", "url: " + visitPhoto.getPhoto_url());
        holder.imageView1.setImageBitmap(photo);
        holder.title.setText(visitPhoto.getPhoto_title());

    }

    @Override
    public int getItemCount() {
        if (photos != null) return photos.size();
        else return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView imageView;
        ImageView imageView1;
        TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.StoredImages);
            imageView1 = itemView.findViewById(R.id.visit_photo);
            title = itemView.findViewById(R.id.titleImage);
        }
    }
}
