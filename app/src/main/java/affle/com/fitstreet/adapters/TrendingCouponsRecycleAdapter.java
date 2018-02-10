package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResTrendingCouponsData;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.CouponsDetailActivity;
import affle.com.fitstreet.ui.activities.TrendingActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.Logger;

/**
 * Created by akash on 12/7/16.
 */
public class TrendingCouponsRecycleAdapter extends RecyclerView.Adapter<TrendingCouponsRecycleAdapter.ViewHolder> implements Filterable {
    private List<ResTrendingCouponsData> resTrendingCouponsData = new ArrayList<>();
    private List<ResTrendingCouponsData> completeResTrendingCouponsData = new ArrayList<>();
    private TrendingActivity mContext;
    private Filter filter;

    public TrendingCouponsRecycleAdapter(Context context, List<ResTrendingCouponsData> resTrendingCouponsData) {
        this.resTrendingCouponsData = resTrendingCouponsData;
        completeResTrendingCouponsData = resTrendingCouponsData;
        this.mContext = (TrendingActivity) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons_list_recycler_view_items, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.couponsName.setText(resTrendingCouponsData.get(position).getName());
        //setting coupon background image
        Glide.with(mContext)
                .load(resTrendingCouponsData.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.couponsImage);

        holder.couponsDesc.setText(resTrendingCouponsData.get(position).getTagText());
        holder.couponsLocation.setText(resTrendingCouponsData.get(position).getLocationName());
        holder.ivFavouriteCoupon.setOnClickListener(holder);
        holder.ivShare.setOnClickListener(holder);
        holder.ivFavouriteCoupon.setVisibility(View.VISIBLE);
        holder.couponsValidity.setText(mContext.getString(R.string.txt_coupons_list_validity) + resTrendingCouponsData.get(position).getValidUpTo());
        if (resTrendingCouponsData.get(position).getFavourite() == ServiceConstants.FAVOURITE) {
            holder.ivFavouriteCoupon.setImageResource(R.drawable.heart);
        } else {
            holder.ivFavouriteCoupon.setImageResource(R.drawable.empty_heart);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return resTrendingCouponsData.size();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    resTrendingCouponsData = (ArrayList<ResTrendingCouponsData>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResTrendingCouponsData> nItems = new ArrayList<ResTrendingCouponsData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeResTrendingCouponsData;
                    } else {
                        for (ResTrendingCouponsData coupon : completeResTrendingCouponsData) {
                            if (coupon.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                                nItems.add(coupon);
                            }
                        }
                    }
                    results.values = nItems;
                    results.count = nItems.size();
                    return results;
                }
            };
        }
        return filter;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView couponsName;
        public TextView couponsDesc;
        public ImageView couponsImage;
        public TextView couponsLocation;
        public TextView couponsValidity;
        public ImageView ivFavouriteCoupon;
        public ImageView ivShare;

        public ViewHolder(View itemView) {
            super(itemView);
            couponsName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            couponsDesc = (TextView) itemView.findViewById(R.id.tv_coupon_desc);
            couponsImage = (ImageView) itemView.findViewById(R.id.iv_coupons_image);
            couponsLocation = (TextView) itemView.findViewById(R.id.tv_coupon_location);
            couponsValidity = (TextView) itemView.findViewById(R.id.tv_coupons_validity);
            ivFavouriteCoupon = (ImageView) itemView.findViewById(R.id.iv_set_favourite_coupon);
            ivShare = (ImageView) itemView.findViewById(R.id.btn_share);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_set_favourite_coupon:
                    ResTrendingCouponsData coupon = resTrendingCouponsData.get(getLayoutPosition());
                    Logger.e("on click fav iv product fav -----> " + coupon.getFavourite() + "     pos -----> " + getLayoutPosition());
                    if (coupon.getFavourite() == ServiceConstants.FAVOURITE)
                        mContext.setFavouriteUnFavourite(getLayoutPosition(), MethodFactory.UNFAVOURITE_COUPON.getMethod());
                    else
                        mContext.setFavouriteUnFavourite(getLayoutPosition(), MethodFactory.FAVOURITE_COUPON.getMethod());
                    break;
                case R.id.btn_share:
//                    String shareBody = "This is the text that will be shared.";
//                    String shareSubject = mContext.getString(R.string.app_name);
//                    AppUtilMethods.openTextShareDialog(mContext, shareSubject, shareBody);


                    couponsImage.setDrawingCacheEnabled(true);
                    Bitmap bitmap = couponsImage.getDrawingCache();
                    mContext.shareCoupon(bitmap, couponsName.getText().toString());
                    break;
                default:
                    openDetailScreen((int) v.getTag());

            }
        }
    }


    private void openDetailScreen(int pos) {

        Intent intent = new Intent(mContext, CouponsDetailActivity.class);
        intent.putExtra(AppConstants.COUPON_ID, resTrendingCouponsData.get(pos).getCoupanID());
        intent.putExtra(AppConstants.FAVOURITE, resTrendingCouponsData.get(pos).getFavourite() + "");
        mContext.startActivityForResult(intent, AppConstants.RC_COUPON_LIST);
    }
}
