package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResFsStoreProductsList;
import affle.com.fitstreet.ui.activities.FsStoreMenWomenActivity;
import affle.com.fitstreet.utils.RangeSeekBar;

/**
 * Created by root on 7/20/16.
 */
public class FsProductFiltersListAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

    private Activity mContext;
    private List<String> mFilterTypeList;
    private List<ResFsStoreProductsList.FsCatDatum> mPartnerDataList;
    private List<String> mSelectedPartnersList;
    private LayoutInflater mInflater;
    private List<Integer> mRangeList;
    private int mMinRange;
    private int mMaxRange;
    private int mLastOpenedPosition;

    public FsProductFiltersListAdapter(Activity context, List<String> filterTypeList,
                                       List<ResFsStoreProductsList.FsCatDatum> partnerDataList, List<String> selectedPartnersList,
                                       List<Integer> rangeList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mFilterTypeList = filterTypeList;
        mPartnerDataList = partnerDataList;
        mSelectedPartnersList = selectedPartnersList;
        //mBrandDataList = brandDataList;
        //mSelectedBrandsList = selectedBrandsList;
        mRangeList = rangeList;
        //mSelectedGenderList = selectedGenderList;
        mMinRange = mRangeList.get(0);
        mMaxRange = mRangeList.get(1);
        //isMaleSelected = mSelectedGenderList.get(0);
        //isFemaleSelected = mSelectedGenderList.get(1);
        mLastOpenedPosition = -1;
    }

    public int getMinRange() {
        return mMinRange;
    }

    public void setMinRange(int minRange) {
        this.mMinRange = minRange;
    }

    public int getMaxRange() {
        return mMaxRange;
    }

    public void setMaxRange(int maxRange) {
        this.mMaxRange = maxRange;
    }

    public int getLastOpenedPosition() {
        return mLastOpenedPosition;
    }

    public void setLastOpenedPosition(int mLastOpenedPosition) {
        this.mLastOpenedPosition = mLastOpenedPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        if (groupPosition == 0) {
            return mPartnerDataList.get(childPosititon);
        } else if (groupPosition == 1) {
            return "";
        } else {
            return null;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, final boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_filter_child, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        final ChildViewHolder childViewHolder = holder;
//        if (groupPosition == 0) {
//            holder.llGender.setVisibility(View.GONE);
//            holder.tvPartnerBrand.setVisibility(View.GONE);
//            holder.llPriceRange.setVisibility(View.GONE);
//            holder.ivMale.setSelected(isMaleSelected);
//            holder.ivFemale.setSelected(isFemaleSelected);
//            holder.ivMale.setOnClickListener(this);
//            holder.ivFemale.setOnClickListener(this);
////        }
//    if (groupPosition == 0) {
//            holder.llGender.setVisibility(View.GONE);
//            holder.tvPartnerBrand.setVisibility(View.VISIBLE);
//            holder.llPriceRange.setVisibility(View.GONE);
//            holder.tvPartnerBrand.setText(mBrandDataList.get(childPosition).getBrandName());
//            holder.tvPartnerBrand.setSelected(mSelectedBrandsList.contains(mBrandDataList.get(childPosition).getBrandID()));
//        }
        if (groupPosition == 0) {
            holder.llGender.setVisibility(View.GONE);
            holder.tvPartnerBrand.setVisibility(View.VISIBLE);
            holder.llPriceRange.setVisibility(View.GONE);
            holder.tvPartnerBrand.setText(mPartnerDataList.get(childPosition).getProCatName());
            holder.tvPartnerBrand.setSelected(mSelectedPartnersList.contains(mPartnerDataList.get(childPosition).getProCatID()));
        } else if (groupPosition == 1) {
            holder.llGender.setVisibility(View.GONE);
            holder.tvPartnerBrand.setVisibility(View.GONE);
            holder.llPriceRange.setVisibility(View.VISIBLE);
            holder.tvPriceRange.setText(String.format(mContext.getResources().getString(R.string.format_price_range), mRangeList.get(0), mRangeList.get(1)));
            holder.rangeSeekBar = new RangeSeekBar<Integer>(mRangeList.get(0), mRangeList.get(1), mContext);
            if (((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.size() > 0) {
                holder.rangeSeekBar.setSelectedMaxValue(Integer.parseInt(((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.get(1).toString()));
                holder.rangeSeekBar.setSelectedMinValue(Integer.parseInt(((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.get(0).toString()));
                childViewHolder.tvPriceRange.setText(String.format(mContext.getResources().getString(R.string.format_price_range), Integer.parseInt(((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.get(0).toString()), Integer.parseInt(((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.get(1).toString())));
            }
            holder.llrbPrice.removeAllViews();
            holder.llrbPrice.addView(holder.rangeSeekBar);
            holder.rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    childViewHolder.tvPriceRange.setText(String.format(mContext.getResources().getString(R.string.format_price_range), mMinRange = (int) minValue, mMaxRange = (int) maxValue));
                    if (((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.size() != 0)
                        ((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.clear();
                    ((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.add(0, mMinRange);
                    ((FsStoreMenWomenActivity) mContext).mSelectedRangeForFSProducts.add(1, mMaxRange);
                }
            });
        } else {
            return null;
        }
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return mPartnerDataList.size();
        } else if (groupPosition == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mFilterTypeList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mFilterTypeList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_filter_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        if (groupPosition == 0) {
            holder.viewDivFilter.setVisibility(View.GONE);
        } else {
            holder.viewDivFilter.setVisibility(View.VISIBLE);
        }
        holder.tvFilterType.setText(mFilterTypeList.get(groupPosition));
        holder.tvFilterType.setSelected(isExpanded);

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class ChildViewHolder {
        private TextView tvPartnerBrand;
        private LinearLayout llrbPrice;
        private TextView tvPriceRange;
        private LinearLayout llGender;
        private LinearLayout llPriceRange;
        private RangeSeekBar<Integer> rangeSeekBar;

        public ChildViewHolder(View itemView) {
            tvPartnerBrand = (TextView) itemView.findViewById(R.id.tv_partner);
            llrbPrice = (LinearLayout) itemView.findViewById(R.id.ll_rb_price);
            tvPriceRange = (TextView) itemView.findViewById(R.id.tv_price_range);
            llGender = (LinearLayout) itemView.findViewById(R.id.ll_gender);
            llPriceRange = (LinearLayout) itemView.findViewById(R.id.ll_price_range);
//           rangeSeekBar=new RangeSeekBar<Integer>(0,10000,itemView.getContext());
//            llrbPrice.addView(rangeSeekBar);
        }
    }

    public static class GroupViewHolder {
        private TextView tvFilterType;
        private View viewDivFilter;

        public GroupViewHolder(View itemView) {
            tvFilterType = (TextView) itemView.findViewById(R.id.tv_title_filter);
            viewDivFilter = itemView.findViewById(R.id.view_div_filter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_male_filter:
//                isMaleSelected = !isMaleSelected;
//                notifyDataSetChanged();
//                break;
//            case R.id.iv_female_filter:
//                isFemaleSelected = !isFemaleSelected;
//                notifyDataSetChanged();
//                break;
        }
    }
}