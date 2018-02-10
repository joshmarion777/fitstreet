package affle.com.fitstreet.imagechooser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.utils.Logger;

/**
 * This class is an Adapter for image choose option list.
 *
 * @author Affle AppStudioz
 */
public class ImageChooseOptionAdapter extends BaseAdapter {
    private List<ImageChooseOption> imageChooseOptionList;
    private LayoutInflater mInflater;

    public ImageChooseOptionAdapter(Activity activity, List<ImageChooseOption> imageChooseOptionList) {
        this.imageChooseOptionList = imageChooseOptionList;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageChooseOptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageChooseOptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        ImageChooseOption imageChooseOption = imageChooseOptionList.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_select_image_option, null);
        }
        TextView mediaTypeNameTextView = (TextView) convertView.findViewById(R.id.media_type_name_textview);
        mediaTypeNameTextView.setText(imageChooseOption.getTitle());
        mediaTypeNameTextView.setCompoundDrawablesWithIntrinsicBounds(imageChooseOption.getIcon(), null, null, null);
        Logger.e("WIDTH -----> " + imageChooseOption.getIcon().getIntrinsicWidth() + "          HEIGHT -----> " + imageChooseOption.getIcon().getIntrinsicHeight());

        return convertView;
    }
}