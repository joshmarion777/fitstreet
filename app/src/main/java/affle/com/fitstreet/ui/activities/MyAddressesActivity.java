package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.AddressesRecyclerAdapter;
import affle.com.fitstreet.models.request.ReqDeleteUserAddresses;
import affle.com.fitstreet.models.request.ReqGetUserDetails;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetUserAddresses;
import affle.com.fitstreet.models.response.ResUserAddress;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAddressesActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_address_count)
    TextView tvAddressCount;
    @BindView(R.id.rv_addresses)
    RecyclerView rvAddresses;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddresses;
    @BindView(R.id.btn_add_address)
    Button btnAddAddress;
    private ProgressDialog mProgressDialog;
    private AddressesRecyclerAdapter mAddressesAdapter;
    private List<ResUserAddress> mAddressList;
    private boolean mFromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                mFromWhere = (Boolean) intent.getExtras().get(AppConstants.TAG_FROM_WHERE);
            }
        }
        super.initData();
    }

    public Boolean getBoolean() {
        return mFromWhere;
    }

    @Override
    protected void initViews() {
        if (mFromWhere) {
            tvTitle.setText(R.string.txt_select_addresses);
        } else {
            tvTitle.setText(R.string.txt_my_addresses);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvAddresses.setLayoutManager(layoutManager);

        //set listeners
        ivBack.setOnClickListener(this);
        btnAddAddress.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mAddressList = new ArrayList<>();
        mAddressesAdapter = new AddressesRecyclerAdapter(this, mAddressList);
        rvAddresses.setAdapter(mAddressesAdapter);
        if (NetworkConnection.isNetworkConnected(this)) {
            getUserAddresses();
        } else {
            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    /**
     * Method used to delete user address
     *
     * @param position
     */
    public void deleteAddress(int position) {
        if (NetworkConnection.isNetworkConnected(this)) {
            deleteUserAddresses(position);
        } else {
            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    private void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(this);
        mProgressDialog.show();
    }

    /**
     * This method is used to hide the progress dialog
     *
     * @return void
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Method used to call the get user addresses web service
     */
    private void getUserAddresses() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        final ReqGetUserDetails reqGetUserAddress = new ReqGetUserDetails();
        reqGetUserAddress.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetUserAddress.setMethod(MethodFactory.GET_USER_ADDRESS.getMethod());
        reqGetUserAddress.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        Call<ResGetUserAddresses> resGetUserAddressesCall = client.getUserAddresses(reqGetUserAddress);
        resGetUserAddressesCall.enqueue(new Callback<ResGetUserAddresses>() {
            @Override
            public void onResponse(Call<ResGetUserAddresses> call, Response<ResGetUserAddresses> response) {
                dismissProgressDialog();
                ResGetUserAddresses resGetUserAddresses = response.body();
                if (resGetUserAddresses != null) {
                    if (resGetUserAddresses.getSuccess() == ServiceConstants.SUCCESS) {
                        mAddressList.addAll(resGetUserAddresses.getUserAddressList());
                        mAddressesAdapter.notifyDataSetChanged();
                        if (mAddressList.size() > 0) {
                            tvAddressCount.setText(String.format(getResources().getQuantityString(R.plurals.plural_address, mAddressList.size()), mAddressList.size()));
                        } else {
                            tvAddressCount.setVisibility(View.GONE);
                            rvAddresses.setVisibility(View.GONE);
                            tvNoAddresses.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvAddressCount.setVisibility(View.GONE);
                        rvAddresses.setVisibility(View.GONE);
                        tvNoAddresses.setVisibility(View.VISIBLE);
                        if (resGetUserAddresses.getErrorCode() != 1)
                            ToastUtils.showShortToast(MyAddressesActivity.this, resGetUserAddresses.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(MyAddressesActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetUserAddresses> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(MyAddressesActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the delete user addresses web service
     */
    private void deleteUserAddresses(final int position) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        final ReqDeleteUserAddresses reqDeleteUserAddresses = new ReqDeleteUserAddresses();
        reqDeleteUserAddresses.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqDeleteUserAddresses.setMethod(MethodFactory.DELETE_USER_ADDRESS.getMethod());
        reqDeleteUserAddresses.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqDeleteUserAddresses.setUserAddID(mAddressList.get(position).getUserAddID());
        Call<ResBase> resDeleteUserAddressCall = client.deleteUserAddresses(reqDeleteUserAddresses);
        resDeleteUserAddressCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                dismissProgressDialog();
                ResBase resDeleteUserAddress = response.body();
                if (resDeleteUserAddress != null) {
                    if (resDeleteUserAddress.getSuccess() == ServiceConstants.SUCCESS) {
                        mAddressList.remove(position);
                        mAddressesAdapter.notifyDataSetChanged();
                        if (mAddressList.size() > 0) {
                            tvAddressCount.setText(String.format(getResources().getQuantityString(R.plurals.plural_address, mAddressList.size()), mAddressList.size()));
                            tvAddressCount.setVisibility(View.VISIBLE);
                            rvAddresses.setVisibility(View.VISIBLE);
                            tvNoAddresses.setVisibility(View.GONE);
                        } else {
                            tvAddressCount.setVisibility(View.GONE);
                            rvAddresses.setVisibility(View.GONE);
                            tvNoAddresses.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtils.showShortToast(MyAddressesActivity.this, resDeleteUserAddress.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(MyAddressesActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(MyAddressesActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), AppConstants.RC_ADD_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_ADD_ADDRESS && resultCode == RESULT_OK) {
            if (data != null) {
                ResUserAddress address = data.getParcelableExtra(AppConstants.TAG_ADDRESS_MODEL);
                int position = data.getIntExtra(AppConstants.TAG_POSITION, -1);
                if (position >= 0)
                    mAddressList.set(position, address);
                else
                    mAddressList.add(address);
                mAddressesAdapter.notifyDataSetChanged();
                tvAddressCount.setText(String.format(getResources().getQuantityString(R.plurals.plural_address, mAddressList.size()), mAddressList.size()));
                tvAddressCount.setVisibility(View.VISIBLE);
                rvAddresses.setVisibility(View.VISIBLE);
                tvNoAddresses.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
