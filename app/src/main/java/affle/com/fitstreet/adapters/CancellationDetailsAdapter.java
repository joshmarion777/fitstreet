package affle.com.fitstreet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.response.ResCancellationDetails;
import affle.com.fitstreet.network.ServiceConstants;
import butterknife.BindView;

/**
 * Created by akash on 12/10/16.
 */
public class CancellationDetailsAdapter extends RecyclerView.Adapter<CancellationDetailsAdapter.ViewHolder> {
    ArrayList<ResCancellationDetails.CancellationDatum> mCancellationDetailList = new ArrayList<>();


    public CancellationDetailsAdapter(ArrayList<ResCancellationDetails.CancellationDatum> list) {
        this.mCancellationDetailList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cancellation_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderedProductName.setText(mCancellationDetailList.get(position).getName());
        holder.tvOrderNumber.setText(mCancellationDetailList.get(position).getOrderID());
        if (mCancellationDetailList.get(position).getStatus().equals(ServiceConstants.ORDER_CANCELLED_BY_SELLER)) {
            holder.tvOrderCancelledBy.setText("Seller");
        } else {
            holder.tvOrderCancelledBy.setText("You");
        }
        holder.tvRefundedMoney.setText(mCancellationDetailList.get(position).getAmount());
        if (mCancellationDetailList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FS_STORE)) {
            holder.ivOrderedProductImage.setImageResource(R.drawable.fs_logo);
        } else if (mCancellationDetailList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FLIPKART)) {
            holder.ivOrderedProductImage.setImageResource(R.drawable.ic_fc_);
        } else if (mCancellationDetailList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_SNAPDEAL)) {
            holder.ivOrderedProductImage.setImageResource(R.drawable.ic_sd);
        }
        try {
            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mCancellationDetailList.get(position).getCreatedTime());
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
            String date = dtFormat.format(simpleDateFormat);
            holder.tvDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mCancellationDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOrderedProductImage;
        CustomTypefaceTextView orderedProductName;
        CustomTypefaceTextView tvOrderNumber;
        CustomTypefaceTextView tvOrderCancelledBy;
        CustomTypefaceTextView tvRefundedMoney;
        LinearLayout llOrderLayout;
        CustomTypefaceTextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ivOrderedProductImage = (ImageView) itemView.findViewById(R.id.iv_contest_image);
            tvOrderNumber = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_number);
            orderedProductName = (CustomTypefaceTextView) itemView.findViewById(R.id.ordered_product_name);
            tvOrderCancelledBy = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_cancelled_by);
            tvRefundedMoney = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_refunded_money);
            tvDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_date);

        }
    }
}
