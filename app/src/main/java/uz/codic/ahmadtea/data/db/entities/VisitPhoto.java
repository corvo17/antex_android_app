package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VisitPhoto {

    @PrimaryKey(autoGenerate = true)
    int pid;

    String photo_url;
    String photo_path;
    String photo_title;
    Integer photo_title_id;
    String visit_id;
    String merchant_id;
    String workspace_id;
    String status;
    boolean isSend;

    public VisitPhoto() {

    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(String workspace_id) {
        this.workspace_id = workspace_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getPhoto_title() {
        return photo_title;
    }

    public void setPhoto_title(String photo_title) {
        this.photo_title = photo_title;
    }

    public Integer getPhoto_title_id() {
        return photo_title_id;
    }

    public void setPhoto_title_id(Integer photo_title_id) {
        this.photo_title_id = photo_title_id;
    }

    @Override
    public String toString() {
        return "VisitPhoto{" +
                "pid=" + pid +
                ", photo_url='" + photo_url + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", photo_title='" + photo_title + '\'' +
                ", photo_title_id=" + photo_title_id +
                ", visit_id='" + visit_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", workspace_id='" + workspace_id + '\'' +
                ", status='" + status + '\'' +
                ", isSend=" + isSend +
                '}';
    }
}
