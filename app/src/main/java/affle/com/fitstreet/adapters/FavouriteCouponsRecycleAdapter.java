package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
import affle.com.fitstreet.models.request.ReqUnFavouriteCoupon;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResFavouriteCouponsData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.CouponsDetailActivity;
import affle.com.fitstreet.ui.fragments.FavouriteFragment;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 12/7/16.
 */
public class FavouriteCouponsRecycleAdapter extends RecyclerView.Adapter<FavouriteCouponsRecycleAdapter.ViewHolder> implements Filterable {
    private List<ResFavouriteCouponsData> favouriteCouponsData = new ArrayList<>();
    private List<ResFavouriteCouponsData> completeFavouriteCouponsData = new ArrayList<>();
    private Context mContext;
    private FavouriteFragment fragment;
    private Filter filter;

    public FavouriteCouponsRecycleAdapter(Context context, List<ResFavouriteCouponsData> favouriteCouponsData, Fragment fragment) {
        this.favouriteCouponsData = favouriteCouponsData;
        completeFavouriteCouponsData = favouriteCouponsData;
        this.mContext = context;
        this.fragment = (FavouriteFragment) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons_list_recycler_view_items, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.couponsName.setText(favouriteCouponsData.get(position).getName());
        //setting coupon background image
        Glide.with(mContext)
                .load(favouriteCouponsData.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.couponsImage);
        holder.unFavouriteCoupon.setVisibility(View.VISIBLE);
        holder.couponsDesc.setText(favouriteCouponsData.get(position).getTagText());
        holder.couponsLocation.setText(favouriteCouponsData.get(position).getLocationName());
        holder.unFavouriteCoupon.setOnClickListener(holder);
        holder.ivShare.setVisibility(View.GONE);
        holder.ivShare.setOnClickListener(holder);
        holder.couponsValidity.setText(mContext.getString(R.string.txt_coupons_list_validity) + favouriteCouponsData.get(position).getValidUpTo());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return favouriteCouponsData.size();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    favouriteCouponsData = (ArrayList<ResFavouriteCouponsData>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResFavouriteCouponsData> nItems = new ArrayList<ResFavouriteCouponsData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeFavouriteCouponsData;
                    } else {
                        for (ResFavouriteCouponsData coupon : completeFavouriteCouponsData) {
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
        public ImageView unFavouriteCoupon;
        public ImageView ivShare;

        public ViewHolder(View itemView) {
            super(itemView);
            couponsName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            couponsDesc = (TextView) itemView.findViewById(R.id.tv_coupon_desc);
            couponsImage = (ImageView) itemView.findViewById(R.id.iv_coupons_image);
            couponsLocation = (TextView) itemView.findViewById(R.id.tv_coupon_location);
            couponsValidity = (TextView) itemView.findViewById(R.id.tv_coupons_validity);
            unFavouriteCoupon = (ImageView) itemView.findViewById(R.id.iv_unfavourite_coupon);
            ivShare = (ImageView) itemView.findViewById(R.id.btn_share);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_unfavourite_coupon:
                    fragment.showProgressDialog();
                    IApiClient client = ApiClient.getApiClient();
                    ReqUnFavouriteCoupon reqUnFavouriteCoupon = new ReqUnFavouriteCoupon();
                    reqUnFavouriteCoupon.setCoupanID(favouriteCouponsData.get(getLayoutPosition()).getCoupanID());
                    reqUnFavouriteCoupon.setMethod(MethodFactory.UNFAVOURITE_COUPON.getMethod());
                    reqUnFavouriteCoupon.setUserID(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_USER_ID, ""));
                    reqUnFavouriteCoupon.setServiceKey(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
                    Call<ResBase> resBaseCall = client.unFavouriteCoupon(reqUnFavouriteCoupon);
                    resBaseCall.enqueue(new Callback<ResBase>() {
                        @Override
                        public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                            ResBase resBase = response.body();
                            if (resBase != null) {
                                if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                                    fragment.dismissProgressDialog();
                                    ToastUtils.showShortToast(mContext, mContext.getString(R.string.txt_success_unfavourite_coupon));
                                    favouriteCouponsData.remove(getLayoutPosition());
                                    fragment.notifyData();

                                } else {
                                    ToastUtils.showShortToast(mContext, resBase.getErrstr());
                                    fragment.dismissProgressDialog();
                                }
                            } else {
                                ToastUtils.showShortToast(mContext, R.string.err_network_connection);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResBase> call, Throwable t) {
                            ToastUtils.showShortToast(mContext, R.string.err_network_connection);
                            fragment.dismissProgressDialog();
                        }
                    });
                    break;

                default:
//                    String z=(String) v.getTag();
                    openDetailScreen((Integer) v.getTag());
//                case R.id.btn_share:
//                    String shareBody = "This is the text that will be shared.";
//                    String shareSubject = mContext.getString(R.string.app_name);
//                    AppUtilMethods.openTextShareDialog((Activity) mContext, shareSubject, shareBody);
//                    break;
            }
        }
    }

    private void openDetailScreen(int pos) {

        Intent intent = new Intent(fragment.getActivity(), CouponsDetailActivity.class);
        intent.putExtra(AppConstants.COUPON_ID, favouriteCouponsData.get(pos).getCoupanID());
// intent.putExtra(AppConstants.FAVOURITE, favouriteCouponsData.get(pos).getFavourite() + "");
        intent.putExtra(AppConstants.FAVOURITE, "1");
        fragment.startActivityForResult(intent, AppConstants.RC_COUPON_LIST);
    }

}
