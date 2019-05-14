package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class Comment implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    long pid;

    int id;
    String label;
    int status_id;
    String status_label;
    String link_id;


    public Comment(){

    }

    protected Comment(Parcel in) {
        pid = in.readLong();
        id = in.readInt();
        label = in.readString();
        status_id = in.readInt();
        status_label = in.readString();
        link_id = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_label() {
        return status_label;
    }

    public void setStatus_label(String status_label) {
        this.status_label = status_label;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pid=" + pid +
                ", id=" + id +
                ", label='" + label + '\'' +
                ", status_id=" + status_id +
                ", status_label='" + status_label + '\'' +
                ", link_id='" + link_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(pid);
        dest.writeInt(id);
        dest.writeString(label);
        dest.writeInt(status_id);
        dest.writeString(status_label);
        dest.writeString(link_id);
    }
}
