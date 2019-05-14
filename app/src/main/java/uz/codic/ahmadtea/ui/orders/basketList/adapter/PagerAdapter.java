package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.orders.basketList.item_fragments.ProductsFragment;
import uz.codic.ahmadtea.ui.orders.basketList.item_fragments.AdditionFragment;
import uz.codic.ahmadtea.ui.orders.basketList.item_fragments.InfoFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<BasketProduct> basketProducts;
    private Order order;
    private List<Comment> comments;
    private String visitNote;
    public PagerAdapter(FragmentManager fm, List<BasketProduct> basketProducts, Order order, List<Comment> comments, String visitNote){
        super(fm);
        this.basketProducts = basketProducts;
        this.order = order;
        this.comments = comments;
        this.visitNote = visitNote;
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return InfoFragment.getInstance(basketProducts,order);
            case 1: return ProductsFragment.getInstance(basketProducts,order);
            case 2: return AdditionFragment.getInstance(basketProducts,order,comments,visitNote);
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Info";
            case 1: return "Products";
            case 2: return "Addition";
            default: return null;
        }
    }
}
