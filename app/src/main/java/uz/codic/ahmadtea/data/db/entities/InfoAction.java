package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class InfoAction {

    @PrimaryKey(autoGenerate = true)
    int i_id;


    private boolean send;

    private boolean send_draft;

    private boolean save_pending;

    private boolean save;

    private boolean error;

    @ColumnInfo(name = "i_id_merchant")
    private String id_merchant;

    @ColumnInfo(name = "i_id_workspace")
    private String id_workspace;

    @ColumnInfo(name = "i_date")
    private String date;


    public InfoAction() {

    }

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isSend_draft() {
        return send_draft;
    }

    public void setSend_draft(boolean send_draft) {
        this.send_draft = send_draft;
    }

    public boolean isSave_pending() {
        return save_pending;
    }

    public void setSave_pending(boolean save_pending) {
        this.save_pending = save_pending;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public String getId_workspace() {
        return id_workspace;
    }

    public void setId_workspace(String id_workspace) {
        this.id_workspace = id_workspace;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InfoAction{" +
                "i_id=" + i_id +
                ", send=" + send +
                ", send_draft=" + send_draft +
                ", save_pending=" + save_pending +
                ", save=" + save +
                ", error=" + error +
                ", id_merchant='" + id_merchant + '\'' +
                ", id_workspace='" + id_workspace + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
