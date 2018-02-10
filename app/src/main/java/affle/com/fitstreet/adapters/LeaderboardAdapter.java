package affle.com.fitstreet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.response.ResGetContestDetails;
import affle.com.fitstreet.models.response.ResLeaveContest;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;

/**
 * Created by guruchetan on 25/7/16.
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private Context context;
    private List<ResLeaveContest.LeaderBoard> leaderBoardList;
    List<ResGetContestDetails.LeaderBoard> leaderBoards;

    public LeaderboardAdapter(Context context, List<ResLeaveContest.LeaderBoard> leaderBoardList, List<ResGetContestDetails.LeaderBoard> leaderBoards) {
        this.context = context;
        this.leaderBoardList = leaderBoardList;
        this.leaderBoards = leaderBoards;

    }


    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leaderboard_details, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardAdapter.ViewHolder holder, int position) {
        if (leaderBoardList != null) {
            ResLeaveContest.LeaderBoard leaderBoard = leaderBoardList.get(position);
            holder.tvRank.setText(leaderBoard.getRank() + "");
            holder.tvAthlete.setText(leaderBoard.getName());
            holder.tvDistance.setText(leaderBoard.getDistance() + " " + AppSharedPreference.getInstance(context).getString(PreferenceKeys.KEY_DISTANCE_UNIT, " km"));
        } else {
            ResGetContestDetails.LeaderBoard leaderBoard = leaderBoards.get(position);
            holder.tvRank.setText(leaderBoard.getRank() + "");
            holder.tvAthlete.setText(leaderBoard.getName());
            holder.tvDistance.setText(leaderBoard.getDistance() + " " + AppSharedPreference.getInstance(context).getString(PreferenceKeys.KEY_DISTANCE_UNIT, " km"));
        }
    }

    @Override
    public int getItemCount() {
        if (leaderBoards != null)
            return leaderBoards.size();
        else
            return leaderBoardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRank;
        public TextView tvAthlete;
        public TextView tvDistance;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRank = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_rank);
            tvAthlete = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_athlete);
            tvDistance = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_distance);
        }
    }
}
