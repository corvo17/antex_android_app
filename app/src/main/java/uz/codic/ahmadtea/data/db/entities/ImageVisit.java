package uz.codic.ahmadtea.data.db.entities;

public class ImageVisit {

    int pid;

    String imagr_src;

    Integer id_image_label;

    Integer id_status;

    public ImageVisit() {
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getImagr_src() {
        return imagr_src;
    }

    public void setImagr_src(String imagr_src) {
        this.imagr_src = imagr_src;
    }

    public Integer getId_image_label() {
        return id_image_label;
    }

    public void setId_image_label(Integer id_image_label) {
        this.id_image_label = id_image_label;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }
}
