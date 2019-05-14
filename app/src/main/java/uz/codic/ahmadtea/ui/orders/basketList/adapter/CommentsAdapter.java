package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<Comment> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, number;

        public MyViewHolder(View view) {
            super(view);
            comment = view.findViewById(R.id.tv_comment );
            number = view.findViewById(R.id.tv_number);
        }
    }

    public CommentsAdapter(List<Comment> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.comment.setText(list.get(position).getLabel());
        holder.number.setText((position + 1) + ".");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


