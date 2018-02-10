package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.response.ResWalletHistoryReceived;
import affle.com.fitstreet.network.ServiceConstants;

/**
 * Created by apps on 10/10/16.
 */

public class WalletHistoryReceivedAdapter extends RecyclerView.Adapter<WalletHistoryReceivedAdapter.ViewHolder> {
    private Activity mActivity;
    private ArrayList<ResWalletHistoryReceived.WalletDatum> walletReceivedHistoryList;

    public WalletHistoryReceivedAdapter(Activity activity, ArrayList<ResWalletHistoryReceived.WalletDatum> list) {
        this.mActivity = activity;
        this.walletReceivedHistoryList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.row_wallet_history_received, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.llAmount.setVisibility(View.GONE);
        if (walletReceivedHistoryList.get(position).getType().equals(ServiceConstants.WALLET_HISTORY_TYPE_RECEIVE_BY_CASHBACK)) {
            holder.llRedeemFsPoints.setVisibility(View.VISIBLE);
            holder.llOrderNumber.setVisibility(View.VISIBLE);
            holder.llCashbakcYouGot.setVisibility(View.VISIBLE);
            holder.llAmount.setVisibility(View.GONE);
            holder.llOrderCancelledBy.setVisibility(View.GONE);
            holder.llPrizeMoney.setVisibility(View.GONE);
            holder.llRefundedMoney.setVisibility(View.GONE);
            holder.llRank.setVisibility(View.GONE);
            holder.llDistance.setVisibility(View.GONE);
            holder.tvProductName.setText(walletReceivedHistoryList.get(position).getName().toString());
            holder.tvRedeemedFsPointsValue.setText(walletReceivedHistoryList.get(position).getRedeemPoint());
            holder.tvCashbackYouGotValue.setText(walletReceivedHistoryList.get(position).getAmount());
            holder.tvOrderNumberValue.setText(walletReceivedHistoryList.get(position).getOrderID());
            holder.tvDate.setText(getDate(walletReceivedHistoryList.get(position).getCreatedTime()));
            if (walletReceivedHistoryList.get(position).getAffilateType() != null) {
                if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FS_STORE)) {
                    holder.ivProductType.setImageResource(R.drawable.fs_logo);
                } else if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FLIPKART)) {
                    holder.ivProductType.setImageResource(R.drawable.ic_fc_);
                } else if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_SNAPDEAL)) {
                    holder.ivProductType.setImageResource(R.drawable.ic_sd);
                }
            }
        } else if (walletReceivedHistoryList.get(position).getType().equals(ServiceConstants.WALLET_HISTORY_TYPE_RECEIVE_BY_PRIZE)) {
            holder.llRedeemFsPoints.setVisibility(View.GONE);
            holder.llOrderNumber.setVisibility(View.GONE);
            holder.llCashbakcYouGot.setVisibility(View.GONE);
            holder.llAmount.setVisibility(View.GONE);
            holder.llOrderCancelledBy.setVisibility(View.GONE);
            holder.llPrizeMoney.setVisibility(View.VISIBLE);
            holder.llRefundedMoney.setVisibility(View.GONE);
            holder.llRank.setVisibility(View.VISIBLE);
            holder.llDistance.setVisibility(View.VISIBLE);
            holder.tvOrderNumberValue.setText(walletReceivedHistoryList.get(position).getOrderID());
            holder.tvProductName.setText(walletReceivedHistoryList.get(position).getContestName());
            holder.tvPrizeMoneyValue.setText(walletReceivedHistoryList.get(position).getAmount());
            holder.tvRankValue.setText(walletReceivedHistoryList.get(position).getRank());
            holder.tvDistanceValue.setText(walletReceivedHistoryList.get(position).getDistance());
            holder.tvDate.setText(getDate(walletReceivedHistoryList.get(position).getCreatedTime()));
            Glide.with(mActivity)
                    .load(walletReceivedHistoryList.get(position).getImage())
                    .error(R.drawable.no_image_available)
                    .placeholder(R.drawable.no_image_available)
                    .into(holder.ivProductType);
        } else if (walletReceivedHistoryList.get(position).getType().equals(ServiceConstants.WALLET_HISTORY_TYPE_RECEIVE_BY_REFUND)) {
            holder.llRedeemFsPoints.setVisibility(View.GONE);
            holder.llOrderNumber.setVisibility(View.VISIBLE);
            holder.llCashbakcYouGot.setVisibility(View.GONE);
            holder.llAmount.setVisibility(View.GONE);
            holder.llOrderCancelledBy.setVisibility(View.VISIBLE);
            holder.llPrizeMoney.setVisibility(View.GONE);
            holder.llRefundedMoney.setVisibility(View.VISIBLE);
            holder.llRank.setVisibility(View.GONE);
            holder.llDistance.setVisibility(View.GONE);
            holder.tvOrderNumberValue.setText(walletReceivedHistoryList.get(position).getOrderID());
            holder.tvProductName.setText(walletReceivedHistoryList.get(position).getName().toString());
            holder.tvDate.setText(getDate(walletReceivedHistoryList.get(position).getCreatedTime()));
            holder.tvRefundedMoney.setText(walletReceivedHistoryList.get(position).getAmount());
            if (walletReceivedHistoryList.get(position).getAffilateType() != null) {
                if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FS_STORE)) {
                    holder.ivProductType.setImageResource(R.drawable.fs_logo);
                } else if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_FLIPKART)) {
                    holder.ivProductType.setImageResource(R.drawable.ic_fc_);
                } else if (walletReceivedHistoryList.get(position).getAffilateType().equals(ServiceConstants.AFFILIATE_TYPE_SNAPDEAL)) {
                    holder.ivProductType.setImageResource(R.drawable.ic_sd);
                }
            }
            if (walletReceivedHistoryList.get(position).getStatus().equals(ServiceConstants.ORDER_CANCELLED_BY_SELLER)) {
                holder.tvOrderCancelledBy.setText("Seller");
            } else {
                holder.tvOrderCancelledBy.setText("You");
            }

        }
    }

    @Override
    public int getItemCount() {
        return walletReceivedHistoryList.size();
    }

    public String getDate(String dateTime) {
        String date = "";
        try {

            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(dateTime);
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
            date = dtFormat.format(simpleDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProductType;
        LinearLayout llAmount;
        LinearLayout llOrderNumber;
        LinearLayout llRedeemFsPoints;
        LinearLayout llCashbakcYouGot;
        LinearLayout llRank;
        LinearLayout llDistance;
        LinearLayout llPrizeMoney;
        LinearLayout llRefundedMoney;
        LinearLayout llOrderCancelledBy;
        CustomTypefaceTextView tvRedeemedFsPoints;
        CustomTypefaceTextView tvProductName;
        CustomTypefaceTextView tvOrderNumberValue;
        CustomTypefaceTextView tvRefundedMoney;
        CustomTypefaceTextView tvOrderCancelledBy;
        CustomTypefaceTextView tvCashbackYouGot;
        CustomTypefaceTextView tvCashbackYouGotValue;
        CustomTypefaceTextView tvRedeemedFsPointsValue;
        ImageView ivContestImage;
        CustomTypefaceTextView tvContestName;
        CustomTypefaceTextView tvDistanceValue;
        CustomTypefaceTextView tvRankValue;
        CustomTypefaceTextView tvPrizeMoneyValue;
        CustomTypefaceTextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            llOrderNumber = (LinearLayout) itemView.findViewById(R.id.ll_order_number);
            llCashbakcYouGot = (LinearLayout) itemView.findViewById(R.id.ll_cashback_you_got);
            llAmount = (LinearLayout) itemView.findViewById(R.id.ll_amount_wallet_history_paid);
            tvRefundedMoney = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_refunded_money_value);
            tvOrderCancelledBy = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_cancelled_by_value);
            llRedeemFsPoints = (LinearLayout) itemView.findViewById(R.id.ll_redeem_fs_points);
            tvRedeemedFsPoints = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_redeemed_fs_points);
            tvProductName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_name_cahback_history);
            tvCashbackYouGot = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_cashback_you_got);
            tvOrderNumberValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_number_value);
            ivProductType = (ImageView) itemView.findViewById(R.id.iv_product_type);
            tvCashbackYouGotValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_cashback_you_got_value);
            tvRedeemedFsPointsValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_redeemed_fs_points_value);
            ivContestImage = (ImageView) itemView.findViewById(R.id.iv_contest_image);
            tvContestName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_contest_name);
            tvDistanceValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_distance_value);
            tvRankValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_rank_value);
            tvPrizeMoneyValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_prize_money_value);
            tvDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_date);
            llRank = (LinearLayout) itemView.findViewById(R.id.ll_rank);
            llPrizeMoney = (LinearLayout) itemView.findViewById(R.id.ll_prize_money);
            llDistance = (LinearLayout) itemView.findViewById(R.id.ll_distance);
            llRefundedMoney = (LinearLayout) itemView.findViewById(R.id.ll_refunded_money);
            llOrderCancelledBy = (LinearLayout) itemView.findViewById(R.id.ll_order_cancelled_by);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
