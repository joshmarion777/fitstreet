package affle.com.fitstreet.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.interfaces.IViewType;
import affle.com.fitstreet.models.ChallengeHeaderModel;
import affle.com.fitstreet.models.ChallengeNoDataModel;
import affle.com.fitstreet.models.response.ResGetContests;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.fragments.ChallengesFragment;
import affle.com.fitstreet.utils.AppUtilMethods;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by root on 7/22/16.
 */
public class ContestsRecyclerAdapter extends RecyclerView.Adapter<ContestsRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private static IOnItemClickListener onItemClickListener;
    private List<IViewType> mChallengesList;
    private ChallengesFragment mContext;

    public ContestsRecyclerAdapter(List<IViewType> challengesList, ChallengesFragment context) {
        this.mChallengesList = challengesList;
        this.mContext = context;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        ContestsRecyclerAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mChallengesList.get(position).getViewType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_challenges, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (mChallengesList.get(position).getViewType()) {
            case IViewType.VIEW_TYPE_TITLE_CONTEST:
                ChallengeHeaderModel headerModel = (ChallengeHeaderModel) mChallengesList.get(position);
                holder.tvChallengeType.setText(headerModel.getChallengeTitle());
                holder.tvChallengeType.setVisibility(View.VISIBLE);
                holder.rlMyChallenges.setVisibility(View.GONE);
                holder.rlUpcomingChallenges.setVisibility(View.GONE);
                holder.tvNoChallanges.setVisibility(View.GONE);
                break;
            case IViewType.VIEW_TYPE_MY_CONTEST:
                ResGetContests.MyContestData myContestData = (ResGetContests.MyContestData) mChallengesList.get(position);
                holder.tvDistance.setText(String.valueOf(myContestData.getDistance()) + " " + AppSharedPreference.getInstance(mContext.getContext()).getString(PreferenceKeys.KEY_DISTANCE_UNIT, " km"));
                holder.tvRankParticipants.setText(String.valueOf(myContestData.getRank()) + "/" + String.valueOf(myContestData.getParticpants()));
                if (AppUtilMethods.calculateDays(myContestData.getEndDate()) >= 1)
                    holder.tvDaysLeft.setText(String.valueOf(AppUtilMethods.calculateDays(myContestData.getEndDate())) + mContext.getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(myContestData.getEndDate())));
//                holder.tvDaysLeft.setText((AppUtilMethods.calculateDays(myContestData.getEndDate()))==1?String.valueOf((AppUtilMethods.calculateDays(myContestData.getEndDate())))+" Day Left":String.valueOf((AppUtilMethods.calculateDays(myContestData.getEndDate())))+" Days Left" );
                else if (AppUtilMethods.calculateDays(myContestData.getEndDate()) == 0) {
                    holder.tvDaysLeft.setText(mContext.getString(R.string.txt_last_day));
                } else if (AppUtilMethods.calculateDays(myContestData.getEndDate()) < 1)
                    holder.tvDaysLeft.setText(mContext.getString(R.string.txt_ended));
                holder.tvChallengeName.setText(myContestData.getName());
                Glide.with(mContext)
                        .load(myContestData.getImage())
                        .placeholder(R.drawable.no_image_available)
                        .bitmapTransform(new CropCircleTransformation(mContext.getActivity()))
                        .into(holder.ivChallenges);
                holder.tvChallengeType.setVisibility(View.GONE);
                holder.rlMyChallenges.setVisibility(View.VISIBLE);
                holder.rlUpcomingChallenges.setVisibility(View.GONE);
                holder.tvNoChallanges.setVisibility(View.GONE);
                break;
            case IViewType.VIEW_TYPE_UPCOMING_CONTEST:
                ResGetContests.UpContestData upContestData = (ResGetContests.UpContestData) mChallengesList.get(position);
                holder.tvUpcomingParticipants.setText(upContestData.getParticpants());
                holder.tvUpcomingChallengeName.setText(upContestData.getName());
                Glide.with(mContext)
                        .load(upContestData.getImage())
                        .placeholder(R.drawable.no_image_available)
                        .bitmapTransform(new CropCircleTransformation(mContext.getActivity()))
                        .into(holder.ivUpcomingChallenges);
                holder.tvUpcomingTagText.setText(String.valueOf(upContestData.getTagText()));
                holder.tvUpcomingJoinChallenge.setTag(R.id.tag_position_challenge, position);
                holder.tvUpcomingJoinChallenge.setOnClickListener(this);
                if (AppUtilMethods.calculateDays(upContestData.getEndDate()) >= 1)
                    holder.tvUpcomingDaysLeft.setText(String.valueOf(AppUtilMethods.calculateDays(upContestData.getEndDate())) + mContext.getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(upContestData.getEndDate())));
//                    holder.tvUpcomingDaysLeft.setText((AppUtilMethods.calculateDays(upContestData.getEndDate()))==1?String.valueOf((AppUtilMethods.calculateDays(upContestData.getEndDate())))+" Day Left":String.valueOf((AppUtilMethods.calculateDays(upContestData.getEndDate())))+" Days Left" );
                else if (AppUtilMethods.calculateDays(upContestData.getEndDate()) == 0) {
                    holder.tvUpcomingDaysLeft.setText(mContext.getString(R.string.txt_last_day));
                } else if (AppUtilMethods.calculateDays(upContestData.getEndDate()) < 1)
                    holder.tvUpcomingDaysLeft.setText(mContext.getString(R.string.txt_ended));

                holder.tvChallengeType.setVisibility(View.GONE);
                holder.rlMyChallenges.setVisibility(View.GONE);
                holder.rlUpcomingChallenges.setVisibility(View.VISIBLE);
                holder.tvNoChallanges.setVisibility(View.GONE);
                break;
            case IViewType.VIEW_TYPE_NO_DATA:
                ChallengeNoDataModel noDataModel = (ChallengeNoDataModel) mChallengesList.get(position);
                holder.tvNoChallanges.setText(noDataModel.getChallengeTitle());
                holder.tvChallengeType.setVisibility(View.GONE);
                holder.rlMyChallenges.setVisibility(View.GONE);
                holder.rlUpcomingChallenges.setVisibility(View.GONE);
                holder.tvNoChallanges.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mChallengesList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.tag_position_challenge);
        switch (v.getId()) {
            case R.id.tv_join_challenge:
                if (NetworkConnection.isNetworkConnected(mContext.getContext())) {
                    ResGetContests.UpContestData upContestData = (ResGetContests.UpContestData) mChallengesList.get(position);
                    if (AppUtilMethods.calculateDays(upContestData.getEndDate()) < 0) {
                        new AlertDialog.Builder(mContext.getContext())
                                .setTitle(mContext.getString(R.string.txt_alert_title))
                                .setMessage(mContext.getString(R.string.txt_contest_ended))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    } else
                        mContext.joinChallenge(upContestData.getContestID());
                }
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNoChallanges;
        public TextView tvChallengeType;
        public RelativeLayout rlMyChallenges;
        public ImageView ivChallenges;
        public TextView tvChallengeName;
        public TextView tvDaysLeft;
        public TextView tvRankParticipants;
        public TextView tvDistance;
        public RelativeLayout rlUpcomingChallenges;
        public ImageView ivUpcomingChallenges;
        public TextView tvUpcomingChallengeName;
        public TextView tvUpcomingDaysLeft;
        public TextView tvUpcomingParticipants;
        public TextView tvUpcomingTagText;
        public TextView tvUpcomingJoinChallenge;

        public ViewHolder(View itemView) {
            super(itemView);
            tvChallengeType = (TextView) itemView.findViewById(R.id.tv_challenge_type);

            rlMyChallenges = (RelativeLayout) itemView.findViewById(R.id.rl_my_challenges);
            ivChallenges = (ImageView) itemView.findViewById(R.id.iv_challenge);
            tvChallengeName = (TextView) itemView.findViewById(R.id.tv_challenge_name);
            tvDaysLeft = (TextView) itemView.findViewById(R.id.tv_time_left);
            tvRankParticipants = (TextView) itemView.findViewById(R.id.tv_rank_participant);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);

            rlUpcomingChallenges = (RelativeLayout) itemView.findViewById(R.id.rl_ongoing_challenges);
            ivUpcomingChallenges = (ImageView) itemView.findViewById(R.id.iv_ongoing_challenge);
            tvUpcomingChallengeName = (TextView) itemView.findViewById(R.id.tv_ongoing_challenge_name);
            tvUpcomingDaysLeft = (TextView) itemView.findViewById(R.id.tv_ongoing_time_left);
            tvUpcomingParticipants = (TextView) itemView.findViewById(R.id.tv_ongoing_participants);
            tvUpcomingTagText = (TextView) itemView.findViewById(R.id.tv_challenge_tagtext);
            tvUpcomingJoinChallenge = (TextView) itemView.findViewById(R.id.tv_join_challenge);
            tvNoChallanges = (TextView) itemView.findViewById(R.id.tv_no_challanges);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}
