package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResProductsCategoryData;
import affle.com.fitstreet.ui.activities.ProductsListActivity;
import affle.com.fitstreet.utils.Logger;

/**
 * Created by akash on 30/6/16.
 */
public class RecommendedRecyclerAdapter extends RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder> implements Filterable {
    List<ResProductsCategoryData> productsCategoryData;
    List<ResProductsCategoryData> completeProductsCategoryData;
    private Context context;
    private Filter filter;

    public RecommendedRecyclerAdapter(Context context, List<ResProductsCategoryData> resProductsCategoryData) {
        this.context = context;
        this.productsCategoryData = resProductsCategoryData;
        completeProductsCategoryData = resProductsCategoryData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_recycler_view_items, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(productsCategoryData.get(position).getProCatImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.ivRecommended);
        if (productsCategoryData.get(position).getProCatImage().equals("")) {
            holder.overLayView.setVisibility(View.VISIBLE);
        } else {
            holder.overLayView.setVisibility(View.GONE);
        }
        holder.rlRecommended.setOnClickListener(holder);
        holder.tvRecommended.setText(productsCategoryData.get(position).getProCatName().toString());

    }

    @Override
    public int getItemCount() {
        return productsCategoryData.size();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    productsCategoryData = (ArrayList<ResProductsCategoryData>) results.values;
                    Logger.e("LIST SIZE -----> " + productsCategoryData.size());
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResProductsCategoryData> nItems = new ArrayList<ResProductsCategoryData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = completeProductsCategoryData;
                    } else {
                        for (ResProductsCategoryData productCategory : completeProductsCategoryData) {
                            if (productCategory.getProCatName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                                nItems.add(productCategory);
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
        public TextView tvRecommended;
        public ImageView ivRecommended;
        public RelativeLayout rlRecommended;
        public View overLayView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRecommended = (TextView) itemView.findViewById(R.id.tv_recommended);
            ivRecommended = (ImageView) itemView.findViewById(R.id.iv_recommended);
            rlRecommended = (RelativeLayout) itemView.findViewById(R.id.rl_recommended);
            overLayView = itemView.findViewById(R.id.iv_recommended_overlay);
        }

        @Override
        public void onClick(View v) {
            String catId = productsCategoryData.get(this.getLayoutPosition()).getProCatID();
            String catName = productsCategoryData.get(this.getLayoutPosition()).getProCatName();
            Intent intent = new Intent(context, ProductsListActivity.class);
            intent.putExtra("productCatId", catId);
            intent.putExtra("productCatName", catName);
            context.startActivity(intent);
        }
    }
}
