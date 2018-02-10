package affle.com.fitstreet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.PlaceModel;
import affle.com.fitstreet.utils.PlaceAPI;

/**
 * Created by root on 6/27/16.
 */
public class PlacesAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private final Context mContext;
    private List<PlaceModel> mAddressList;
    private static IOnItemClickListener onItemClickListener;
    private LayoutInflater mInflater;
    private Filter mFilter;
    private PlaceAPI mPlaceAPI;
    private String mType;

    public PlacesAutoCompleteAdapter(Context context, String type) {
        mInflater = LayoutInflater.from(context);
        mAddressList = new ArrayList<PlaceModel>();
        mContext = context;
        mType = type;
        mPlaceAPI = new PlaceAPI();
    }

    @Override
    public int getCount() {
        return mAddressList.size();
    }

    @Override
    public PlaceModel getItem(int position) {
        return mAddressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_place, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvAddress.setText(mAddressList.get(position).getDescription());
        return view;

    }

    public static class ViewHolder {
        private TextView tvAddress;

        public ViewHolder(View itemView) {
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }

    @Override
    public Filter getFilter() {
//        notifyDataSetChanged();
        if (mFilter == null) {
            mFilter = new Filter() {
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    List<PlaceModel> addressList;
                    if (constraint != null && constraint.length() > 0) {
                        mAddressList = mPlaceAPI.autocomplete(constraint.toString(), mType);
                    } else {
                        mAddressList = new ArrayList<PlaceModel>();
                    }
                    filterResults.values = mAddressList;
                    filterResults.count = mAddressList.size();
                    return filterResults;
                }
            };
        }
        return mFilter;
    }
}