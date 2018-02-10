package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResGetFilters;

/**
 * Created by Affle AppStudioz on 11/7/16.
 */
public class PartnersRecyclerAdapter extends RecyclerView.Adapter<PartnersRecyclerAdapter.ViewHolder> {
    private List<ResGetFilters.ResPartnerData> mPartnerDataList;
    private List<String> mSelectedPartnersList;
    private Activity mContext;
    private static IOnItemClickListener onItemClickListener;

    public PartnersRecyclerAdapter(Activity context, List<ResGetFilters.ResPartnerData> partnerDataList, List<String> selectedPartnersList) {
        mPartnerDataList = partnerDataList;
        mSelectedPartnersList = selectedPartnersList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_partners, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResGetFilters.ResPartnerData partner = mPartnerDataList.get(position);
        holder.tvPartners.setText(partner.getPartnerName());
        holder.tvPartners.setSelected(mSelectedPartnersList.contains(partner.getPartnerID()));
    }

    @Override
    public int getItemCount() {
        return mPartnerDataList.size();
    }

    public static void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        PartnersRecyclerAdapter.onItemClickListener = onItemClickListener;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvPartners;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPartners = (TextView) itemView.findViewById(R.id.tv_partner);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition(), 0);
        }
    }
}
