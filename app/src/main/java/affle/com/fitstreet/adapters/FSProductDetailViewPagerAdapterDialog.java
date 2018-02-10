package affle.com.fitstreet.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;

/**
 * Created by akash on 2/9/16.
 */
public class FSProductDetailViewPagerAdapterDialog extends PagerAdapter implements View.OnClickListener {
    ArrayList<String> arrayList;
    Context context;
    private static IOnItemClickListener onItemClickListener;
    private ImageView mImageView;

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        FSProductDetailViewPagerAdapterDialog.onItemClickListener = onItemClickListener;
    }

    public FSProductDetailViewPagerAdapterDialog(Context context, ArrayList<String> imageList) {
        this.arrayList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (arrayList.size() > 0 ? arrayList.size() : 0);
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
    public Object instantiateItem(final ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fs_store_view_pager_fragment_layout, container, false);
        mImageView = (ImageView) view.findViewById(R.id.iv_view_pager_products_image);
        // mImageViewCross = (ImageView) view.findViewById(R.id.iv_cross);
        // mImageViewCross.setOnClickListener(this);
        Glide.with(context)
                .load(arrayList.get(position))
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(mImageView);
        container.addView(view);
        mImageView.setTag(this);
        mImageView.setOnClickListener(this);
        view.setOnClickListener(this);
        view.setTag(position);
        return view;
    }


    @Override
    public void onClick(View v) {
    }
}