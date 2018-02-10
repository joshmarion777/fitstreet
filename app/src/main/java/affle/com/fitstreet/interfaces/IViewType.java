package affle.com.fitstreet.interfaces;

public interface IViewType {

    int VIEW_TYPE_MY_CONTEST = 0;
    int VIEW_TYPE_UPCOMING_CONTEST = 1;
    int VIEW_TYPE_TITLE_CONTEST = 2;
    int VIEW_TYPE_NO_DATA = 3;

    public int getViewType();
}
