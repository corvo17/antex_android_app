package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment;

import java.util.HashMap;
import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface VisitFragmentView extends MvpView {
    void loadComments(List<Comment> titles);
}
