package affle.com.fitstreet.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResFsStore;

/**
 * Created by akash on 3/8/16.
 */
public class FsStoreViewPagerAdapter extends PagerAdapter {
    ArrayList<ResFsStore.ProductBanner> productBanners;
    LayoutInflater inflater;
    Context context;
    ImageView ivImageProducts;

    public FsStoreViewPagerAdapter(FragmentManager fm, Context context, ArrayList<ResFsStore.ProductBanner> arrayList) {
        //this.imageUrlArray =imageUrlArray;
        this.productBanners = arrayList;
        this.context = context;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fs_store_view_pager_fragment_layout, container, false);
        ivImageProducts = (ImageView) view.findViewById(R.id.iv_view_pager_products_image);
        Glide.with(context).load(productBanners.get(position).getBannerImage())
                .placeholder(R.drawable.no_image_available)
                .into(ivImageProducts);
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return (productBanners.size() > 0 ? productBanners.size() : 0);
    }
}
