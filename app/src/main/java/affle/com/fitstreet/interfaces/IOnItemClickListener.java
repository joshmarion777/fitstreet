package affle.com.fitstreet.interfaces;

import android.view.View;

/**
 * Interface to give callback when an item of the recycler view is clicked
 */
public interface IOnItemClickListener {
    //method called when an item of the recycler view is clicked
    public void onItemClick(View view, int position, int tag);
}