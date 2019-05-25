package uz.codic.ahmadtea.ui.visit.zakaz.warehouse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter.Callback;

public class AdapterWarehouse extends RecyclerView.Adapter<AdapterWarehouse.ViewHolder> {

    List<PhysicalWareHouse> wareHouses;

    Callback callback;
    private static int lastCheckedPos = -2;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void updateList(List<PhysicalWareHouse> wareHouses) {
        this.wareHouses = wareHouses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choice, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (wareHouses.get(viewHolder.getAdapterPosition()).isChecked())
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);

        viewHolder.checkBox.setText(wareHouses.get(viewHolder.getAdapterPosition()).getLabel());
        int position = viewHolder.getAdapterPosition();
        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if(lastCheckedPos != position && lastCheckedPos >= 0){
                    wareHouses.get(lastCheckedPos).setChecked(false);
                    notifyItemChanged(lastCheckedPos);
                }
                wareHouses.get(position).setChecked(true);
                lastCheckedPos = position;
                callback.onItemClick(wareHouses.get(position).getId());
            }
        });

        if (wareHouses.size()==1){
            wareHouses.get(position).setChecked(true);
            lastCheckedPos = position;
            callback.onItemClick(wareHouses.get(position).getId());
        }

    }

    @Override
    public int getItemCount() {
        if (wareHouses != null)
            return wareHouses.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_choice);
        }
    }
}
