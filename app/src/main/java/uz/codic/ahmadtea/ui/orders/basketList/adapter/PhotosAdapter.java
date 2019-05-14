package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{

    List<BasketProduct> list;
    public void updateList(List<BasketProduct> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photos, viewGroup, false);
        return new PhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ViewHolder viewHolder, int i) {

        viewHolder.img.setImageResource(R.drawable.map);

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
