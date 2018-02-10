package affle.com.fitstreet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResCouponList;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.CouponsDetailActivity;
import affle.com.fitstreet.ui.activities.CouponsListActivity;
import affle.com.fitstreet.utils.AppConstants;


/**
 * Created by akash on 4/7/16.
 */
public class CouponsListRecyclerAdapter extends RecyclerView.Adapter<CouponsListRecyclerAdapter.ViewHolder> {
    List<ResCouponList> couponLists = new ArrayList<>();
    private CouponsListActivity context;

    public CouponsListRecyclerAdapter(Context context, List<ResCouponList> couponLists) {
        this.context = (CouponsListActivity) context;
        this.couponLists = couponLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons_list_recycler_view_items, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.couponsName.setText(couponLists.get(position).getName());
        //setting coupon background image
        Glide.with(context)
                .load(couponLists.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.couponsImage);
        holder.couponsDesc.setText(couponLists.get(position).getTagText());
        holder.couponsLocation.setText(couponLists.get(position).getLocationName());//2016-07-11
        try {
            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(couponLists.get(position).getValidUpTo());
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMMM dd, yyyy");
            String date = dtFormat.format(simpleDateFormat);
            holder.couponsValidity.setText(context.getString(R.string.txt_coupons_list_validity) + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.ivSetFavouriteCoupon.setVisibility(View.VISIBLE);
        holder.ivSetFavouriteCoupon.setOnClickListener(holder);
        holder.ivShare.setOnClickListener(holder);
        holder.rlCouponList.setOnClickListener(holder);

        if (couponLists.get(position).getFavourite() == ServiceConstants.FAVOURITE) {
            holder.ivSetFavouriteCoupon.setImageResource(R.drawable.heart);
        } else {
            holder.ivSetFavouriteCoupon.setImageResource(R.drawable.empty_heart);
        }

    }

    @Override
    public int getItemCount() {
        return couponLists.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView couponsName;
        public TextView couponsDesc;
        public ImageView couponsImage;
        public TextView couponsLocation;
        public TextView couponsValidity;
        public ImageView ivSetFavouriteCoupon;
        public ImageView ivShare;
        public RelativeLayout rlCouponList;

        public ViewHolder(View itemView) {
            super(itemView);
            couponsName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            couponsDesc = (TextView) itemView.findViewById(R.id.tv_coupon_desc);
            couponsImage = (ImageView) itemView.findViewById(R.id.iv_coupons_image);
            couponsLocation = (TextView) itemView.findViewById(R.id.tv_coupon_location);
            couponsValidity = (TextView) itemView.findViewById(R.id.tv_coupons_validity);
            ivSetFavouriteCoupon = (ImageView) itemView.findViewById(R.id.iv_set_favourite_coupon);
            ivShare = (ImageView) itemView.findViewById(R.id.btn_share);
            rlCouponList = (RelativeLayout) itemView.findViewById(R.id.rl_coupon_list);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_set_favourite_coupon:
                    ResCouponList coupon = couponLists.get(getLayoutPosition());
                    if (coupon.getFavourite() == ServiceConstants.FAVOURITE)
                        context.setFavouriteUnFavourite(getLayoutPosition(), MethodFactory.UNFAVOURITE_COUPON.getMethod());
                    else
                        context.setFavouriteUnFavourite(getLayoutPosition(), MethodFactory.FAVOURITE_COUPON.getMethod());

                    break;
                case R.id.btn_share:

//                    String shareBody = "This is the text that will be shared.";
//                    String shareSubject = context.getString(R.string.app_name);
//                    AppUtilMethods.openTextShareDialog(context, shareSubject, shareBody);
//                    couponsImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = couponsImage.getDrawingCache();
//                    if (bitmap != null) {
//                        String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_COUPON_IMAGE_PATH;
//                        CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
//                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                        sharingIntent.setType("image/*");
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share subject");
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "share text");
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse(imagePath));
//
//                        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
//
//                    }
                    //custom akash

                    couponsImage.setDrawingCacheEnabled(true);
                    Bitmap bitmap = couponsImage.getDrawingCache();
                    context.shareCoupon(bitmap, couponsName.getText().toString());
                    break;
                case R.id.rl_coupon_list:
                    Intent intent = new Intent(context, CouponsDetailActivity.class);
                    intent.putExtra(AppConstants.COUPON_ID, couponLists.get(getAdapterPosition()).getCoupanID());
                    intent.putExtra(AppConstants.FAVOURITE, couponLists.get(getAdapterPosition()).getFavourite() + "");
                    context.startActivityForResult(intent, AppConstants.RC_COUPON_LIST);
            }
        }


    }
}
