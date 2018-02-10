package affle.com.fitstreet.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResFsStoreProductsList;
import affle.com.fitstreet.models.response.ResProductsListData;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.FsStoreMenWomenActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.utils.AppDialog;

/**
 * Created by akash on 31/8/16.
 */
public class FsStoreProductsListAdapter extends RecyclerView.Adapter<FsStoreProductsListAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private List<ResFsStoreProductsList.FsProductDatum> resFsStoreProductsList = new ArrayList<>();
    private FsStoreMenWomenActivity context;
    public static IOnItemClickListener onItemClickListener;
    private Filter filter;
    private List<ResFsStoreProductsList.FsProductDatum> completeProductsListData = new ArrayList<>();

    public FsStoreProductsListAdapter(Context context, List<ResFsStoreProductsList.FsProductDatum> resFsStoreProductsList) {
        this.context = (FsStoreMenWomenActivity) context;
        this.resFsStoreProductsList = resFsStoreProductsList;
        completeProductsListData = resFsStoreProductsList;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        FsStoreProductsListAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fs_store_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(resFsStoreProductsList.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.ivProductImage);
        holder.tvProductName.setText(resFsStoreProductsList.get(position).getName());
        holder.tvActualProductPrice.setText("Rs. " + resFsStoreProductsList.get(position).getPrice());
        holder.tvActualProductPrice.setPaintFlags(holder.tvActualProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ivSetFavourite.setVisibility(View.VISIBLE);
        holder.ivShareProduct.setVisibility(View.VISIBLE);
        float discountedPrice = calculateDiscountPrice(Float.parseFloat(resFsStoreProductsList.get(position).getPrice()), Float.parseFloat(resFsStoreProductsList.get(position).getDiscount()));

        holder.tvDiscountedPrice.setText("Rs. " + String.valueOf(discountedPrice));
        if (resFsStoreProductsList.get(position).getFavourite() == ServiceConstants.FAVOURITE) {
            holder.ivSetFavourite.setImageResource(R.drawable.heart);
        } else {
            holder.ivSetFavourite.setImageResource(R.drawable.empty_heart);
        }
        if (!(resFsStoreProductsList.get(position).getDiscount().equals("0") | resFsStoreProductsList.get(position).getDiscount().equals("0"))) {
            holder.tvProductDiscount.setText(resFsStoreProductsList.get(position).getDiscount() + "% off");
            holder.tvProductDiscount.setVisibility(View.VISIBLE);
            holder.tvActualProductPrice.setVisibility(View.VISIBLE);
        } else {
            holder.tvProductDiscount.setVisibility(View.GONE);
            holder.tvActualProductPrice.setVisibility(View.GONE);
        }

        holder.ivShareProduct.setTag(holder);
        holder.ivSetFavourite.setTag(position);
        holder.ivSetFavourite.setOnClickListener(this);
        holder.ivShareProduct.setOnClickListener(this);
        holder.itemView.setTag(resFsStoreProductsList.get(position));
    }

    public float calculateDiscountPrice(float actualPrice, float discount) {
        float discountValue = (actualPrice * discount) / 100;
        return actualPrice - discountValue;
    }

    @Override
    public int getItemCount() {
        return resFsStoreProductsList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_set_favourite_product:
                if (AppSharedPreference.getInstance(context).getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (AppSharedPreference.getInstance(context).getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        int position = (int) v.getTag();
                        ResFsStoreProductsList.FsProductDatum product = resFsStoreProductsList.get(position);
                        if (product.getFavourite() == ServiceConstants.FAVOURITE)
                            context.setFavouriteUnFavourite(position, MethodFactory.UNFAVOURITE_PRODUCT.getMethod());
                        else
                            context.setFavouriteUnFavourite(position, MethodFactory.FAVOURITE_PRODUCT.getMethod());
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(context);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(context);
                }
                break;
            case R.id.iv_share_product:
                if (AppSharedPreference.getInstance(context).getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (AppSharedPreference.getInstance(context).getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        ViewHolder holder = (ViewHolder) v.getTag();
                        context.shareProduct(holder.ivProductImage.getDrawingCache(), holder.tvProductName.getText().toString());
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(context);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(context);
                }
                break;
        }

    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    resFsStoreProductsList = (ArrayList<ResFsStoreProductsList.FsProductDatum>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResFsStoreProductsList.FsProductDatum> nItems = new ArrayList<ResFsStoreProductsList.FsProductDatum>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeProductsListData;
                    } else {
                        for (ResFsStoreProductsList.FsProductDatum product : completeProductsListData) {
                            if (product.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                                nItems.add(product);
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
        public ImageView ivProductImage;
        public TextView tvProductName;
        public TextView tvProductDesc;
        public ImageView ivCancelFavouriteProduct;
        public TextView tvActualProductPrice;
        public ImageView ivSetFavourite;
        public ImageView ivShareProduct;
        public TextView tvDiscountedPrice;
        public TextView tvProductDiscount;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = (ImageView) itemView.findViewById(R.id.iv_favorite_product);
            tvProductDesc = (TextView) itemView.findViewById(R.id.tv_product_desc);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductDiscount = (TextView) itemView.findViewById(R.id.tv_product_discount);
            tvActualProductPrice = (TextView) itemView.findViewById(R.id.tv_product_actual_price);
            ivCancelFavouriteProduct = (ImageView) itemView.findViewById(R.id.iv_cancel_favourite_product);
            ivSetFavourite = (ImageView) itemView.findViewById(R.id.iv_set_favourite_product);
            ivShareProduct = (ImageView) itemView.findViewById(R.id.iv_share_product);
            tvDiscountedPrice = (TextView) itemView.findViewById(R.id.tv_discounted_price);
            ivProductImage.setDrawingCacheEnabled(true);
            itemView.setOnClickListener(this);
            ivProductImage.setDrawingCacheEnabled(true);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}
