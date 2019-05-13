package uz.codic.ahmadtea.ui.visit.zakaz.visit_info;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uz.codic.ahmadtea.R;

class VisitInfoAdapter extends RecyclerView.Adapter<VisitInfoAdapter.Holder> {
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_visit_info, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView info;

        public Holder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.info);
        }
    }
}
