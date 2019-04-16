package uz.codic.ahmadtea.di.visitFragment;

import dagger.Component;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.CameraFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

@ActivityScope
@Component(modules = {VisitModule.class})
public interface ActivityComponent {

    void inject(VisitFragment fragment);

    void inject(CameraFragment fragment);

}
