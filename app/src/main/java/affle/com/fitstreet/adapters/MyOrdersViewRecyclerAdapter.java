package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.MyOrdersActivity;
import affle.com.fitstreet.ui.activities.MyOrdersViewActivity;
import butterknife.BindView;

/**
 * Created by akash on 19/9/16.
 */
public class MyOrdersViewRecyclerAdapter extends RecyclerView.Adapter<MyOrdersViewRecyclerAdapter.ViewHolder> {
    private MyOrdersViewActivity context;
    List<ResMyOrders.Product> mOrdersList;
    private static IOnItemClickListener iOnItemClickListener;
    private ArrayList<String> selectedProductList = new ArrayList<>();
    private ArrayList<String> selectedPositionList = new ArrayList<>();

    public static void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        MyOrdersViewRecyclerAdapter.iOnItemClickListener = iOnItemClickListener;
    }

    public MyOrdersViewRecyclerAdapter(Activity context, List<ResMyOrders.Product> list) {
        this.context = (MyOrdersViewActivity) context;
        mOrdersList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_order_view_listing, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProductName.setText(mOrdersList.get(position).getName());
        holder.tvProductSize.setText("Color : " + mOrdersList.get(position).getSize());
        holder.tvProductColor.setText("Size : " + mOrdersList.get(position).getColor());
        holder.viewBlackOverlay.setTag(position);
        Glide.with(context)
                .load(mOrdersList.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .into(holder.ivProduct);
        if (Integer.parseInt(mOrdersList.get(position).getStatus()) == ServiceConstants.ORDER_INACTIVE) {
            holder.viewBlackOverlay.setVisibility(View.VISIBLE);
            holder.tvOrderCancelled.setVisibility(View.VISIBLE);
            holder.llProduct.setOnClickListener(null);
            holder.itemView.setOnClickListener(null);
        }
        if (Integer.parseInt(mOrdersList.get(position).getStatus()) == ServiceConstants.ORDER_ACTIVE) {
            holder.viewBlackOverlay.setVisibility(View.GONE);
            holder.tvOrderCancelled.setVisibility(View.GONE);
        }
        if (mOrdersList.get(position).isSelected()) {
            holder.itemView.performClick();
        }
    }

    @Override
    public int getItemCount() {
        return mOrdersList.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_product_name)
        CustomTypefaceTextView tvProductName;
        @BindView(R.id.tv_product_color)
        CustomTypefaceTextView tvProductColor;
        @BindView(R.id.tv_product_size)
        CustomTypefaceTextView tvProductSize;
        View viewBlackOverlay;
        LinearLayout llProduct;
        CustomTypefaceTextView tvOrderCancelled;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_name);
            tvProductColor = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_color);
            tvProductSize = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_size);
            viewBlackOverlay = itemView.findViewById(R.id.view_selected_overlay);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            llProduct = (LinearLayout) itemView.findViewById(R.id.ll_product);
            itemView.setOnClickListener(this);
            tvOrderCancelled = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_cancelled);

        }

        @Override
        public void onClick(View v) {
            iOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
            if (viewBlackOverlay.getVisibility() == View.GONE) {
                viewBlackOverlay.setVisibility(View.VISIBLE);
                if (!selectedProductList.contains(mOrdersList.get((int) viewBlackOverlay.getTag()).getOrdProMapID()))
                    selectedProductList.add(mOrdersList.get((int) viewBlackOverlay.getTag()).getOrdProMapID());
                if (!selectedPositionList.contains("" + getAdapterPosition()))
                    selectedPositionList.add("" + getAdapterPosition());
                context.setProductIds(selectedProductList, selectedPositionList);
            } else {
                viewBlackOverlay.setVisibility(View.GONE);
                selectedProductList.remove(mOrdersList.get((int) viewBlackOverlay.getTag()).getOrdProMapID());
                selectedPositionList.remove("" + getAdapterPosition());
                context.setProductIds(selectedProductList, selectedPositionList);

            }
        }
    }
}
