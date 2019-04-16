package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "label")
    private String name;

    @ColumnInfo(name = "role_label")
    private String role_label;

    @ColumnInfo(name = "sync_time")
    String sync_time;

    public User(){

    }
    public String getSync_time() {
        return sync_time;
    }

    public void setSync_time(String sync_time) {
        this.sync_time = sync_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole_label() {
        return role_label;
    }

    public void setRole_label(String role_label) {
        this.role_label = role_label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

