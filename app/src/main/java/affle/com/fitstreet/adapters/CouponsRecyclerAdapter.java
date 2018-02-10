package affle.com.fitstreet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResCouponData;
import affle.com.fitstreet.ui.activities.CouponsListActivity;

/**
 * Created by akash on 23/6/16.
 */
public class CouponsRecyclerAdapter extends RecyclerView.Adapter<CouponsRecyclerAdapter.ViewHolder> implements Filterable {
    List<ResCouponData> couponsData = new ArrayList<>();
    private List<ResCouponData> compleCouponData = new ArrayList<>();
    private Context context;
    private Filter filter;

    public CouponsRecyclerAdapter(Context context, List<ResCouponData> couponsData) {
        this.context = context;
        this.couponsData = couponsData;
        compleCouponData = couponsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons_recycler_view_items, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.couponsName.setText(couponsData.get(position).getCatName());
        //setting coupon background image
        Glide.with(context)
                .load(couponsData.get(position).getCatImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.couponsImage);
        //setting coupon category image on textview
        Glide.with(holder.couponsName.getContext())
                .load(couponsData.get(position).getCatIcon())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(50, 50) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        holder.couponsName.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(holder.couponsName.getResources(), resource), null, null);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return couponsData.size();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    couponsData = (ArrayList<ResCouponData>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<ResCouponData> nItems = new ArrayList<ResCouponData>();
                    if (constraint == null || constraint.length() == 0) {
                        nItems = compleCouponData;
                    } else {
                        for (ResCouponData coupon : compleCouponData) {
                            if (coupon.getCatName().toUpperCase().contains(constraint.toString().toUpperCase())) {
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
        public ImageView couponsImage;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            couponsName = (TextView) itemView.findViewById(R.id.tv_coupons_text);
            couponsImage = (ImageView) itemView.findViewById(R.id.iv_coupons_background);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String catId = couponsData.get(this.getLayoutPosition()).getCatID();
            String catName = couponsData.get(this.getLayoutPosition()).getCatName();
            Intent intent = new Intent(context, CouponsListActivity.class);
            intent.putExtra("catId", catId);
            intent.putExtra("catName", catName);
            context.startActivity(intent);
        }
    }
}
