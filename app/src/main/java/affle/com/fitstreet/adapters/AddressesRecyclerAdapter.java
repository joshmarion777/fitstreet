package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.response.ResUserAddress;
import affle.com.fitstreet.ui.activities.AddAddressActivity;
import affle.com.fitstreet.ui.activities.MyAddressesActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;

/**
 * Created by Affle AppStudioz on 11/7/16.
 */
public class AddressesRecyclerAdapter extends RecyclerView.Adapter<AddressesRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private List<ResUserAddress> mAddressList;
    private Activity mContext;

    public AddressesRecyclerAdapter(Activity context, List<ResUserAddress> addressList) {
        mAddressList = addressList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResUserAddress address = mAddressList.get(position);
        holder.tvAddressName.setText(address.getName());
        holder.tvAddressLocation.setText(address.getAddress() + ", " + (address.getLandmark() == null || address.getLandmark().equals("") ? "" : address.getLandmark() + ", ") + address.getCity() + " - " + address.getPincode() + ", " + address.getState());
        holder.tvAddressPhone.setText(address.getPhone());
        holder.tvRemove.setTag(R.id.tag_position, position);
        holder.tvEdit.setTag(R.id.tag_position, position);
        holder.rlAddressDetail.setTag(R.id.tag_position, position);
        holder.tvRemove.setOnClickListener(this);
        holder.tvEdit.setOnClickListener(this);
        holder.itemView.setOnClickListener(this);
        holder.rlAddressDetail.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAddressName;
        public TextView tvAddressLocation;
        public TextView tvAddressPhone;
        public TextView tvRemove;
        public TextView tvEdit;
        RelativeLayout rlAddressDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAddressName = (TextView) itemView.findViewById(R.id.tv_address_name);
            tvAddressLocation = (TextView) itemView.findViewById(R.id.tv_address_location);
            tvAddressPhone = (TextView) itemView.findViewById(R.id.tv_address_phone);
            tvRemove = (TextView) itemView.findViewById(R.id.tv_address_remove);
            tvEdit = (TextView) itemView.findViewById(R.id.tv_address_edit);
            rlAddressDetail = (RelativeLayout) itemView.findViewById(R.id.rl_address_detail);


        }
    }

    @Override
    public void onClick(View view) {
        if (view.getTag(R.id.tag_position) != null) {
            int position = (int) view.getTag(R.id.tag_position);
            ResUserAddress address = mAddressList.get(position);
            switch (view.getId()) {
                case R.id.tv_address_edit:
                    Intent intent = new Intent(mContext, AddAddressActivity.class);
                    intent.putExtra(AppConstants.TAG_ADDRESS_MODEL, address);
                    intent.putExtra(AppConstants.TAG_POSITION, position);
                    mContext.startActivityForResult(intent, AppConstants.RC_ADD_ADDRESS);
                    break;

                case R.id.tv_address_remove:
                    AppDialog.showRemoveAddressAlertDialog(mContext, position);
                    break;

                case R.id.rl_address_detail:
                    if (((MyAddressesActivity) mContext).getBoolean()) {
                        Intent intent1 = new Intent();
                        if (mAddressList != null) {
                            intent1.putExtra("name", mAddressList.get(position).getName());
                            intent1.putExtra("address", mAddressList.get(position).getAddress());
                            intent1.putExtra("city", mAddressList.get(position).getCity());
                            intent1.putExtra("state", mAddressList.get(position).getState());
                            intent1.putExtra("pincode", mAddressList.get(position).getPincode());
                            intent1.putExtra("mobile", mAddressList.get(position).getPhone());
                            if (mAddressList.get(position).getLandmark() != null)
                                intent1.putExtra("landmark", mAddressList.get(position).getLandmark());
                        }
                        mContext.setResult(Activity.RESULT_OK, intent1);
                        mContext.finish();


                    }


            }
        }
    }
}
