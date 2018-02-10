package affle.com.fitstreet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResPushNotification;
import affle.com.fitstreet.utils.AppConstants;

/**
 * Created by akash on 21/10/16.
 */
public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<ResPushNotification.NotificationDatum> mNotificationList = new ArrayList<>();
    private Context context;
    private static IOnItemClickListener onItemClickListener;

    public NotificationRecyclerAdapter(Context context, ArrayList<ResPushNotification.NotificationDatum> arrayList) {
        this.mNotificationList = arrayList;
        this.context = context;
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        NotificationRecyclerAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_notification, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMessage.setText(mNotificationList.get(position).getNotifyMessage());
        holder.tvTime.setText(mNotificationList.get(position).getCreatedTime());
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag(R.id.tag_position_challenge);
        switch (view.getId()) {
            case R.id.tv_join_challenge:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CustomTypefaceTextView tvMessage;
        CustomTypefaceTextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_msg);
            tvTime = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            view.setTag(mNotificationList.get(getAdapterPosition()));
            onItemClickListener.onItemClick(view, getAdapterPosition(), 0);
        }
    }
}
