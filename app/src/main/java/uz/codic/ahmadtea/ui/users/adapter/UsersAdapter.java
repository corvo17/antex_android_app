package uz.codic.ahmadtea.ui.users.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.ui.MainActivity;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    List<User> userList;
    UsersCallback callback;

    public UsersAdapter() {
    }

    public void updataList(List<User> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    public void setCallback(UsersCallback usersCallback){
        this.callback = usersCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.userName.setText(userList.get(i).getName());
        viewHolder.syncTime.setText(userList.get(i).getSync_time());

        viewHolder.itemView.setOnClickListener(v -> {
            Intent dashboard = new Intent(viewHolder.itemView.getContext(), MainActivity.class);
            //main_menu.putExtra("id_employee", userList.get(i).getId());
            callback.setId_employee(userList.get(i).getId(), userList.get(i).getRole_label());
            callback.setToken(userList.get(i).getToken());
            viewHolder.itemView.getContext().startActivity(dashboard);
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView syncTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            syncTime = itemView.findViewById(R.id.sync_time);
        }
    }
}
