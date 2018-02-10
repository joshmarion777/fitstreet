package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
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
import affle.com.fitstreet.models.response.ResWalletHistoryPaid;
import affle.com.fitstreet.models.response.ResWalletHistoryReceived;
import affle.com.fitstreet.network.ServiceConstants;

/**
 * Created by apps on 10/10/16.
 */

public class WalletHistoryPaidAdapter extends RecyclerView.Adapter<WalletHistoryPaidAdapter.ViewHolder> {
    private Activity mActivity;
    private ArrayList<ResWalletHistoryPaid.WalletDatum> walletPaidHistoryList;

    public WalletHistoryPaidAdapter(Activity activity, ArrayList<ResWalletHistoryPaid.WalletDatum> list) {
        this.mActivity = activity;
        this.walletPaidHistoryList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.row_wallet_history, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(walletPaidHistoryList.get(position).getCreatedTime());
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
            String date = dtFormat.format(simpleDateFormat);
            holder.tvOrderDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (walletPaidHistoryList.get(position).getType().equals(ServiceConstants.WALLET_HISTORY_TYPE_SEND_TO_BANK_AMOUNT)) {
            holder.ivImage.setImageResource(R.drawable.send_to_bank);
            holder.tvProductNameCahbackHistory.setVisibility(View.GONE);
            holder.llCashbakcYouGot.setVisibility(View.GONE);
            holder.llRedeemFsPoints.setVisibility(View.GONE);
            holder.llOrderNumber.setVisibility(View.GONE);
            holder.llCashbackUsed.setVisibility(View.GONE);
            holder.llAccountNumber.setVisibility(View.VISIBLE);
            holder.llIfscCode.setVisibility(View.VISIBLE);
            holder.llBankName.setVisibility(View.VISIBLE);
            holder.llWithdraweeName.setVisibility(View.VISIBLE);
            holder.llAmount.setVisibility(View.VISIBLE);
            holder.tvWithdraweeName.setText(walletPaidHistoryList.get(position).getAccountHolderName());
            holder.tvIfscCode.setText(walletPaidHistoryList.get(position).getIfscCode());
            holder.tvBankName.setText(walletPaidHistoryList.get(position).getBankName());
            holder.tvAmountValue.setText(String.valueOf(walletPaidHistoryList.get(position).getAmount()));
            holder.tvAccountNumber.setText(walletPaidHistoryList.get(position).getAccountNumber());
        } else if (walletPaidHistoryList.get(position).getType().equals(ServiceConstants.WALLET_HISTORY_TYPE_USED_FOR_SHOPPING)) {
            holder.ivImage.setImageResource(R.drawable.product);
            holder.llAccountNumber.setVisibility(View.GONE);
            holder.llIfscCode.setVisibility(View.GONE);
            holder.llCashbakcYouGot.setVisibility(View.GONE);
            holder.llRedeemFsPoints.setVisibility(View.GONE);
            holder.tvProductNameCahbackHistory.setVisibility(View.GONE);
            holder.llBankName.setVisibility(View.GONE);
            holder.llOrderNumber.setVisibility(View.VISIBLE);
            holder.llCashbackUsed.setVisibility(View.VISIBLE);
            holder.llAmount.setVisibility(View.GONE);
            holder.llWithdraweeName.setVisibility(View.GONE);
            holder.tvOrderNumberValue.setText(walletPaidHistoryList.get(position).getOrderID());
            holder.tvOrderNumberValue.setPadding(0, 10, 0, 0);
            holder.tvOrderCashbackUsedValue.setText(String.valueOf(walletPaidHistoryList.get(position).getAmount()));

        }
    }

    @Override
    public int getItemCount() {
        return walletPaidHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivImage;
        LinearLayout llOrderNumber;
        LinearLayout llRedeemFsPoints;
        LinearLayout llCashbakcYouGot;
        LinearLayout llWithdraweeName;
        LinearLayout llBankName;
        LinearLayout llIfscCode;
        LinearLayout llAccountNumber;
        LinearLayout llCashbackUsed;
        LinearLayout llAmount;
        CustomTypefaceTextView tvOrderDate;
        CustomTypefaceTextView tvOrderNumberValue;
        CustomTypefaceTextView tvOrderCashbackUsedValue;
        CustomTypefaceTextView tvAmountValue;
        CustomTypefaceTextView tvWithdraweeName;
        CustomTypefaceTextView tvAccountNumber;
        CustomTypefaceTextView tvBankName;
        CustomTypefaceTextView tvIfscCode;
        CustomTypefaceTextView tvProductNameCahbackHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            llAmount = (LinearLayout) itemView.findViewById(R.id.ll_amount_wallet_history_paid);
            tvOrderNumberValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_number_value);
            tvOrderCashbackUsedValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_cashback_used_value);
            llAccountNumber = (LinearLayout) itemView.findViewById(R.id.ll_account_number);
            llIfscCode = (LinearLayout) itemView.findViewById(R.id.ll_ifsc_code);
            llWithdraweeName = (LinearLayout) itemView.findViewById(R.id.ll_withdrawee_name);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_product_type);
            llBankName = (LinearLayout) itemView.findViewById(R.id.ll_bank_name);
            tvAccountNumber = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_account_num_value);
            tvWithdraweeName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_withdrawer_name_value);
            tvAmountValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_amount_value);
            tvBankName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_bank_name_value);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_product_type);
            llOrderNumber = (LinearLayout) itemView.findViewById(R.id.ll_order_number);
            llCashbakcYouGot = (LinearLayout) itemView.findViewById(R.id.ll_cashback_you_got);
            llRedeemFsPoints = (LinearLayout) itemView.findViewById(R.id.ll_redeem_fs_points);
            tvIfscCode = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_ifsc_code_value);
            llCashbackUsed = (LinearLayout) itemView.findViewById(R.id.ll_cashback_used);
            tvOrderDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_date);
            tvProductNameCahbackHistory = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_name_cahback_history);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
