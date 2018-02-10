package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResProductsListData;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.ProductsListActivity;
import affle.com.fitstreet.utils.AppUtilMethods;

/**
 * Created by akash on 14/7/16.
 */
public class ProductsListRecycleAdapter extends RecyclerView.Adapter<ProductsListRecycleAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private static IOnItemClickListener onItemClickListener;
    private List<ResProductsListData> productsListData = new ArrayList<>();
    private List<ResProductsListData> completeProductsListData = new ArrayList<>();
    private ProductsListActivity context;
    private Filter filter;

    public ProductsListRecycleAdapter(Context context, List<ResProductsListData> productsListData) {
        this.context = (ProductsListActivity) context;
        this.productsListData = productsListData;
        completeProductsListData = productsListData;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        ProductsListRecycleAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorites, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ResProductsListData product = productsListData.get(position);
        Picasso.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.no_image_icon)
                .into(holder.ivProductImage);
        //holder.tvProductDesc.setText(product.getDescription());
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText("Rs. " + product.getPrice());
        holder.ivSetFavourite.setVisibility(View.VISIBLE);
        holder.ivShareProduct.setVisibility(View.VISIBLE);

        if (product.getFavourite() == ServiceConstants.FAVOURITE) {
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
        return productsListData.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_set_favourite_product:
                int position = (int) v.getTag(R.id.tag_position);
                ResProductsListData product = productsListData.get(position);
                if (product.getFavourite() == ServiceConstants.FAVOURITE)
                    context.setFavouriteUnFavourite(position, MethodFactory.UNFAVOURITE_PRODUCT.getMethod());
                else
                    context.setFavouriteUnFavourite(position, MethodFactory.FAVOURITE_PRODUCT.getMethod());
                break;
            case R.id.iv_share_product:
                ViewHolder holder = (ViewHolder) v.getTag();
                context.shareProduct(holder.ivProductImage.getDrawingCache(), holder.tvProductName.getText().toString());
                break;


        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    productsListData = (ArrayList<ResProductsListData>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResProductsListData> nItems = new ArrayList<ResProductsListData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeProductsListData;
                    } else {
                        for (ResProductsListData product : completeProductsListData) {
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
