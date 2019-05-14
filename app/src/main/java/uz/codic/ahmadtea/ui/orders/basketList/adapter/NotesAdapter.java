package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<String> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, number;

        public MyViewHolder(View view) {
            super(view);
            comment = view.findViewById(R.id.tv_comment );
            number = view.findViewById(R.id.tv_number);
        }
    }

    public NotesAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);

        return new NotesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.MyViewHolder holder, int position) {
        if (position == 0)
        holder.number.setText("Order : ");
        else holder.number.setText("Visit : ");
        holder.comment.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
