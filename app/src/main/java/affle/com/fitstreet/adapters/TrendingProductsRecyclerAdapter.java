package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResTrendingProductsData;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.TrendingActivity;

/**
 * Created by akash on 11/7/16.
 */
public class TrendingProductsRecyclerAdapter extends RecyclerView.Adapter<TrendingProductsRecyclerAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private static IOnItemClickListener onItemClickListener;
    private List<ResTrendingProductsData> resTrendingProductsData = new ArrayList<>();
    private List<ResTrendingProductsData> completeResTrendingProductsData = new ArrayList<>();
    private TrendingActivity mContext;
    private Filter mFilter;

    public TrendingProductsRecyclerAdapter(Context context, List<ResTrendingProductsData> resTrendingProductsData) {
        this.resTrendingProductsData = resTrendingProductsData;
        completeResTrendingProductsData = resTrendingProductsData;
        this.mContext = (TrendingActivity) context;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        TrendingProductsRecyclerAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorites, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvProductPrice.setText("Rs. " + resTrendingProductsData.get(position).getPrice());
        holder.tvProductName.setText(resTrendingProductsData.get(position).getName());
        holder.ivShareProduct.setVisibility(View.VISIBLE);
        holder.ivSetFavourite.setVisibility(View.VISIBLE);
//        Glide.with(mContext)
//                .load(resTrendingProductsData.get(position).getImage())
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        holder.ivProductImage.setImageResource(R.drawable.no_image_available);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(holder.ivProductImage);
        Picasso.with(mContext)
                .load(resTrendingProductsData.get(position).getImage())
                .placeholder(R.drawable.no_image_icon)
                .into(holder.ivProductImage);
        holder.ivCancelFavouriteProduct.setOnClickListener(holder);
        if (resTrendingProductsData.get(position).getFavourite() == ServiceConstants.FAVOURITE) {
            holder.ivSetFavourite.setImageResource(R.drawable.heart);
        } else {
            holder.ivSetFavourite.setImageResource(R.drawable.empty_heart);
        }
        holder.ivSetFavourite.setTag(R.id.tag_position, position);
        holder.ivSetFavourite.setOnClickListener(this);
        holder.ivShareProduct.setTag(holder);
        holder.ivShareProduct.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return resTrendingProductsData.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_set_favourite_product:
                int position = (int) v.getTag(R.id.tag_position);
                ResTrendingProductsData product = resTrendingProductsData.get(position);
                if (product.getFavourite() == ServiceConstants.FAVOURITE)
                    mContext.setFavouriteUnFavourite(position, MethodFactory.UNFAVOURITE_PRODUCT.getMethod());
                else
                    mContext.setFavouriteUnFavourite(position, MethodFactory.FAVOURITE_PRODUCT.getMethod());
                break;
            case R.id.iv_share_product:
                ViewHolder holder = (ViewHolder) v.getTag();
                mContext.shareProduct(holder.ivProductImage.getDrawingCache(), holder.tvProductName.getText().toString());
                break;

        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    resTrendingProductsData = (ArrayList<ResTrendingProductsData>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResTrendingProductsData> nItems = new ArrayList<ResTrendingProductsData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeResTrendingProductsData;
                    } else {
                        for (ResTrendingProductsData product : completeResTrendingProductsData) {
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
        return mFilter;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProductImage;
        public TextView tvProductName;
        public TextView tvProductDesc;
        public ImageView ivCancelFavouriteProduct;
        public TextView tvProductPrice;
        public ImageView ivSetFavourite;
        public ImageView ivShareProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = (ImageView) itemView.findViewById(R.id.iv_favorite_product);
            tvProductDesc = (TextView) itemView.findViewById(R.id.tv_product_desc);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            ivCancelFavouriteProduct = (ImageView) itemView.findViewById(R.id.iv_cancel_favourite_product);
            ivSetFavourite = (ImageView) itemView.findViewById(R.id.iv_set_favourite_product);
            ivShareProduct = (ImageView) itemView.findViewById(R.id.iv_share_product);
            ivProductImage.setDrawingCacheEnabled(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}
