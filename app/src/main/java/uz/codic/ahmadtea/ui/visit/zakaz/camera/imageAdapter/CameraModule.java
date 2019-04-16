package uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter;

import java.io.File;

public class CameraModule {
    private String Title;
    private File image;

    public CameraModule(String title, File image) {
        Title = title;
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
