package affle.com.fitstreet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by akash on 8/8/16.
 */
public class AutoCompleteSearchViewAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<HashMap<String, String>> fullList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();

    public AutoCompleteSearchViewAdapter(Context context, int resource, ArrayList<HashMap<String, String>> fullList) {
        super(context, resource);
        this.fullList = fullList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Filter getFilter() {
        android.widget.Filter filter = new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    ArrayList<HashMap<String, String>> suggestion = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < fullList.size(); i++) {
                        if (fullList.get(i).get("productName").toUpperCase().contains(((String) constraint).toUpperCase())) {
                            suggestion.add(fullList.get(i));
                        }
                    }
                    filterResults.values = suggestion;
                    filterResults.count = suggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                if (results.values != null) {
                    ArrayList<HashMap<String, String>> newValues = (ArrayList<HashMap<String, String>>) results.values;

                    for (int i = 0; i < newValues.size(); i++) {
                        filteredList.add(newValues.get(i));
                    }
                }
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        };
        return filter;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public String getItem(int position) {
        return filteredList.get(position).get("productName");
    }

    public HashMap<String, String> getSelectedList(int position) {
        HashMap<String, String> selectedItem = new HashMap<>();
        selectedItem = filteredList.get(position);
        return selectedItem;
    }

}

