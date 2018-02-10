package affle.com.fitstreet.adapters;

import android.content.Context;
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
import affle.com.fitstreet.models.response.ResPrizeMoneyDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by akash on 13/10/16.
 */
public class PrizeMoneyDetailsAdapter extends RecyclerView.Adapter<PrizeMoneyDetailsAdapter.ViewHolder> {
    private ArrayList<ResPrizeMoneyDetails.PrizeDatum> mPrizeMoneyDetailsList = new ArrayList<>();
    private Context context;

    public PrizeMoneyDetailsAdapter(Context context, ArrayList<ResPrizeMoneyDetails.PrizeDatum> prizeDetailsList) {
        this.mPrizeMoneyDetailsList = prizeDetailsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prize_money_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvContestName.setText(mPrizeMoneyDetailsList.get(position).getName());
        holder.tvDistanceValue.setText(mPrizeMoneyDetailsList.get(position).getDistance());
        holder.tvPrizeMoneyValue.setText("Rs. " + mPrizeMoneyDetailsList.get(position).getAmount());
        holder.tvRankValue.setText(mPrizeMoneyDetailsList.get(position).getRank());
        Glide.with(context)
                .load(mPrizeMoneyDetailsList.get(position).getImage())
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.no_image_available)
                .into(holder.ivContestImage);
        try {
            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mPrizeMoneyDetailsList.get(position).getCreatedTime());
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
            String date = dtFormat.format(simpleDateFormat);
            holder.tvDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPrizeMoneyDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivContestImage;
        CustomTypefaceTextView tvContestName;
        CustomTypefaceTextView tvDistanceValue;
        CustomTypefaceTextView tvRankValue;
        CustomTypefaceTextView tvPrizeMoneyValue;
        CustomTypefaceTextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ivContestImage = (ImageView) itemView.findViewById(R.id.iv_contest_image);
            tvContestName = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_contest_name);
            tvDistanceValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_distance_value);
            tvRankValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_rank_value);
            tvPrizeMoneyValue = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_prize_money_value);
            tvDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_contest_date);

        }
    }
}
