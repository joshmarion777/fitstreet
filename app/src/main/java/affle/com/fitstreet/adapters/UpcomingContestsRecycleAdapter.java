package affle.com.fitstreet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResGetContests;
import affle.com.fitstreet.ui.fragments.ChallengesFragment;
import affle.com.fitstreet.utils.Logger;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by akash on 20/7/16.
 */
public class UpcomingContestsRecycleAdapter extends RecyclerView.Adapter<UpcomingContestsRecycleAdapter.ViewHolder> implements View.OnClickListener {

    private static IOnItemClickListener onItemClickListener;
    private List<ResGetContests.UpContestData> upContestData = new ArrayList<>();
    private ChallengesFragment mContext;

    public UpcomingContestsRecycleAdapter(List<ResGetContests.UpContestData> upContestData, ChallengesFragment context) {
        this.upContestData = upContestData;
        this.mContext = context;
    }

    public static IOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        UpcomingContestsRecycleAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ongoing_challenges, null);
        return new ViewHolder(view);
    }

    public long calculateDays(int pos) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = upContestData.get(pos).getEndDate();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String inputString2 = df.format(c.getTime());
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date1.getTime() - date2.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Logger.e("bind view holder");
        holder.tvParticipants.setText(upContestData.get(position).getParticpants());
        //holder.tvDaysLeft.setText(upContestData.get(position).getEndDate());
        holder.tvChallengeName.setText(upContestData.get(position).getName());
        Glide.with(mContext)
                .load(upContestData.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .bitmapTransform(new CropCircleTransformation(mContext.getActivity()))
                .into(holder.ivChallenges);
        holder.tvTagText.setText(String.valueOf(upContestData.get(position).getTagText()));
        holder.btnJoinChallenge.setTag(R.id.tag_position_challenge, position);
        holder.btnJoinChallenge.setOnClickListener(this);
        if (calculateDays(position) >= 1)
            holder.tvDaysLeft.setText(String.valueOf(calculateDays(position)) + " Days Left");
        else {
            holder.tvDaysLeft.setText("Last Day");
        }
    }

    @Override
    public int getItemCount() {
        return upContestData.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.tag_position_challenge);
        switch (v.getId()) {
            case R.id.btn_join_challenge:
                mContext.joinChallenge(upContestData.get(position).getContestID());
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivChallenges;
        public TextView tvChallengeName;
        public TextView tvDaysLeft;
        public TextView tvParticipants;
        public TextView tvTagText;
        public Button btnJoinChallenge;

        public ViewHolder(View itemView) {
            super(itemView);
            ivChallenges = (ImageView) itemView.findViewById(R.id.iv_challenge);
            tvChallengeName = (TextView) itemView.findViewById(R.id.tv_challenge_name);
            tvDaysLeft = (TextView) itemView.findViewById(R.id.tv_time_left);
            tvParticipants = (TextView) itemView.findViewById(R.id.tv_participants);
            tvTagText = (TextView) itemView.findViewById(R.id.tv_challenge_tagtext);
            btnJoinChallenge = (Button) itemView.findViewById(R.id.btn_join_challenge);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}
