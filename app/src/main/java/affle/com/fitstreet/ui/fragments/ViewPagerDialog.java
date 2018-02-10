package affle.com.fitstreet.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.FSProductDetailViewPagerAdapterDialog;
import affle.com.fitstreet.ui.activities.FsStoreProductDetail;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akash on 6/9/16.
 */
public class ViewPagerDialog extends DialogFragment implements ViewPager.OnPageChangeListener ,View.OnClickListener{
    @BindView(R.id.vp_fs_store_product_dialog)
    ViewPager vpFsStoreProductDialog;
    @BindView(R.id.ll_pager_indicator)
    LinearLayout llPagerIndicator;
    @BindView(R.id.iv_cross)
    ImageView ivCross;
    int count;
    ArrayList<String> imageList= new ArrayList<>();
    private FSProductDetailViewPagerAdapterDialog fsProductDetailViewPagerAdapterDialog;
    private List<ImageView> mImageViewsList= new ArrayList<>();

    public ViewPagerDialog()
    {}

    public ViewPagerDialog(ArrayList<String> list, int count)
    {
        imageList=list;
        this.count=count;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_pager_example_dialog, container, false);
        ButterKnife.bind(this, v);
        fsProductDetailViewPagerAdapterDialog = new FSProductDetailViewPagerAdapterDialog(getContext(), imageList);
        vpFsStoreProductDialog.setAdapter(fsProductDetailViewPagerAdapterDialog);
        showViewPagerIndicator(count);
        ivCross.setOnClickListener(this);
        vpFsStoreProductDialog.setOnPageChangeListener(this);
        return v;
    }

    private void showViewPagerIndicator(int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setTag(R.id.tag_position, i);
            imageView.setImageResource(R.drawable.x_sd_page_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            params.rightMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            imageView.setLayoutParams(params);
            //imageView.setOnClickListener(this);
            llPagerIndicator.addView(imageView);
            mImageViewsList.add(imageView);
        }
        mImageViewsList.get(0).setSelected(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (ImageView imageView : mImageViewsList) {
            imageView.setSelected(false);
        }
        mImageViewsList.get(position).setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.iv_cross:
                if (this != null && this.isVisible()) {

                    getActivity().onBackPressed();
                }
                break;
        }
    }
}
