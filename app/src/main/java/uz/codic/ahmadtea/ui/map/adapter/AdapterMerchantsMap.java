package uz.codic.ahmadtea.ui.map.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;

public class AdapterMerchantsMap extends RecyclerView.Adapter<AdapterMerchantsMap.ViewHolder> {

    List<AdapterItems> adapterItems;

    public AdapterMerchantsMap() {
        adapterItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchants_map_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AdapterItems items = adapterItems.get(i);
        viewHolder.name.setText(items.name);
        viewHolder.value.setText((items.value) + " km");
    }

    @Override
    public int getItemCount() {
        if (adapterItems.isEmpty()){
            return 0;
        }else return adapterItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_tv_adepter_item_name);
            value = itemView.findViewById(R.id.id_tv_adepter_item_masofa);
        }
    }

    public void setAdapterItems(List<AdapterItems> adapterItems) {
        this.adapterItems = adapterItems;
        notifyDataSetChanged();
    }
}
