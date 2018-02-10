package affle.com.fitstreet.adapters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by akash on 4/10/16.
 */
public abstract class EndlessRecyclerOnScrollListenerGrid extends RecyclerView.OnScrollListener {
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;
    private GridLayoutManager mGridLayoutManager;

    public EndlessRecyclerOnScrollListenerGrid(GridLayoutManager gridLayoutManager) {
        previousTotal = 0;
        //   this.mGridLayoutManager = gridLayoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            visibleItemCount = recyclerView.getChildCount();
            mGridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            totalItemCount = mGridLayoutManager.getItemCount();
            firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();
            onScroll(firstVisibleItem);

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                } else return;
            }
//            if (!loading
//                    && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
                //loading = onLoadMore(currentPage + 1, totalItemCount);

                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }
        }
    }

    public abstract void onLoadMore(int current_page);

    public void onScroll(int firstVisibleItem) {

    }
}
