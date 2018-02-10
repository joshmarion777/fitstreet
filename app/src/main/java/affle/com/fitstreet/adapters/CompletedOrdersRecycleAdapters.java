package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.network.ServiceConstants;

/**
 * Created by akash on 2/8/16.
 */
public class CompletedOrdersRecycleAdapters extends RecyclerView.Adapter<CompletedOrdersRecycleAdapters.ViewHolder> implements View.OnClickListener {
    Activity activity;
    LayoutInflater layoutInflater;
    List<ResMyOrders.OrderDatum> mCompletedOrderlist;
    private static IOnItemClickListener iOnItemClickListener;

    public static void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        CompletedOrdersRecycleAdapters.iOnItemClickListener = iOnItemClickListener;
    }


    public CompletedOrdersRecycleAdapters(Activity activity, List<ResMyOrders.OrderDatum> list) {
        this.activity = activity;
        mCompletedOrderlist = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_orders, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvOrderNumber.setText("ORDER NUMBER : " + mCompletedOrderlist.get(position).getOrderID());
        holder.llBottomButton.setVisibility(View.GONE);
        holder.llProductsImages.removeAllViews();
        holder.llProductDetailContainer.removeAllViews();
        for (int i = 0; i < mCompletedOrderlist.get(position).getProducts().size(); i++) {
            ImageView imageView = new ImageView(activity);
            RelativeLayout relativeLayout = new RelativeLayout(activity);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            relativeLayout.setLayoutParams(param);
            relativeLayout.addView(imageView);
            View view = new View(activity);
            view.setLayoutParams(params);
            view.setBackgroundColor(activity.getResources().getColor(R.color.c_black_overlay));
            CustomTypefaceTextView textView = new CustomTypefaceTextView(activity);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(activity.getResources().getText(R.string.order_cancelled));
            textView.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            textView.setTextColor(activity.getResources().getColor(R.color.c_white));
            relativeLayout.addView(view);
            relativeLayout.addView(textView);
            Glide.with(activity)
                    .load(mCompletedOrderlist.get(position).getProducts().get(i).getImage())
                    .placeholder(R.drawable.no_image_available)
                    .into(imageView);
            holder.llProductsImages.addView(relativeLayout);
            holder.llProductDetailContainer.setVisibility(View.VISIBLE);
            layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dynamicAddView = layoutInflater.inflate(R.layout.my_order_details_view, null);
            CustomTypefaceTextView tvProductName = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_product_name);
            CustomTypefaceTextView tvSize = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_size);
            CustomTypefaceTextView tvColor = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_color);
            CustomTypefaceTextView tvserial = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_product_serial);
            holder.llProductDetailContainer.addView(dynamicAddView);
            tvProductName.setText(mCompletedOrderlist.get(position).getProducts().get(i).getName());
            tvColor.setText(mCompletedOrderlist.get(position).getProducts().get(i).getColor());
            tvSize.setText(mCompletedOrderlist.get(position).getProducts().get(i).getSize());
            tvserial.setText(String.valueOf((i + 1)) + ". ");
            if (Integer.parseInt(mCompletedOrderlist.get(position).getProducts().get(i).getStatus()) == ServiceConstants.ORDER_INACTIVE) {
                view.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

            }

        }


        if (mCompletedOrderlist.get(position).getProducts() != null) {
            holder.tvNumberOfProductsText.setText(R.string.txt_number_of_product);
            holder.tvNumberOfProducts.setText(String.valueOf(mCompletedOrderlist.get(position).getProducts().size()));
        }


        try {
            if (Integer.parseInt(mCompletedOrderlist.get(position).getOrderStatus()) == ServiceConstants.ORDER_DELIVERED) {
                holder.tvProductDateText.setText(R.string.txt_delivered_on);
                Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mCompletedOrderlist.get(position).getDeliveredTime());
                SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");

                String date = dtFormat.format(simpleDateFormat);
                holder.tvProductDate.setText(date);
            } else {
                holder.tvProductDateText.setText(R.string.txt_cancelled_on);
                Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mCompletedOrderlist.get(position).getCancelledOn());
                SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
                String date = dtFormat.format(simpleDateFormat);
                holder.tvProductDate.setText(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mCompletedOrderlist.size();
    }

    @Override
    public void onClick(View v) {
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrderNumber;
        LinearLayout llBottomButton;
        LinearLayout llProductDetailContainer;
        LinearLayout llProductsImages;
        CustomTypefaceTextView tvNumberOfProducts;
        CustomTypefaceTextView tvProductDate;
        CustomTypefaceTextView tvProductDateText;
        CustomTypefaceTextView tvNumberOfProductsText;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            llBottomButton = (LinearLayout) itemView.findViewById(R.id.ll_bottom_buttons);
            llProductsImages = (LinearLayout) itemView.findViewById(R.id.ll_product_images);
            tvNumberOfProducts = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_products_number);
            tvProductDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_date);
            llProductDetailContainer = (LinearLayout) itemView.findViewById(R.id.ll_product_detail_container);
            tvProductDateText = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_date_text);
            tvNumberOfProductsText = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_products_number_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            iOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}