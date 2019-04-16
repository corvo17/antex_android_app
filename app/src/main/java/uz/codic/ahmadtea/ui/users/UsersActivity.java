package uz.codic.ahmadtea.ui.users;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.ui.MainActivity;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.login.LoginActivity;
import uz.codic.ahmadtea.ui.users.adapter.UsersAdapter;
import uz.codic.ahmadtea.ui.users.adapter.UsersCallback;

public class UsersActivity extends BaseActivity implements UsersMvpView, UsersCallback {

    FloatingActionButton add_user;

    RecyclerView users_recycler;

    UsersAdapter usersAdapter;

    UsersPresenter<UsersMvpView> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        users_recycler = findViewById(R.id.users_recycler_view);
        add_user = findViewById(R.id.fab_add_user);

        presenter= new UsersPresenter<>(this);
        presenter.onAttach(this);
        usersAdapter = new UsersAdapter();
        usersAdapter.setCallback(this);
        users_recycler.setLayoutManager(new LinearLayoutManager(this));

        users_recycler.setAdapter(usersAdapter);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(UsersActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    @Override
    public void onUserListReady(List<User> list) {
        usersAdapter.updataList(list);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllUsers();
    }

    @Override
    public void goNext() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }

    @Override
    public void onItemClick() {

    }

    //user click set id_employee
    @Override
    public void setId_employee(String id_employee, String role) {
        presenter.setId_employee(id_employee, role);
    }

    @Override
    public void setToken(String token) {
        presenter.setToken(token);
    }
}
