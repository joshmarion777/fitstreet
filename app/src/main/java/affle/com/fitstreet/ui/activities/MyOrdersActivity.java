package affle.com.fitstreet.ui.activities;

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
import affle.com.fitstreet.adapters.CompletedOrdersRecycleAdapters;
import affle.com.fitstreet.adapters.RecentOrdersRecycleAdapters;
import affle.com.fitstreet.customviews.SerializableArrayList;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqMyOrders;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends BaseActivity implements IOnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_recent_orders)
    Button btnRecentOrders;
    @BindView(R.id.btn_completed_orders)
    Button btnCompletedOrders;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_my_recent_orders)
    RecyclerView rvMyRecentOrders;
    @BindView(R.id.rv_my_completed_orders)
    RecyclerView rvMyCompletedOrders;
    private RecentOrdersRecycleAdapters mRecentOrdersRecycleAdapters;
    private CompletedOrdersRecycleAdapters mCompletedOrdersRecycleAdapters;
    private List<ResMyOrders.OrderDatum> mRecentOrderList = new ArrayList<>();
    private List<ResMyOrders.OrderDatum> mCompletedOrderList = new ArrayList<>();
    private List<ResMyOrders.Product> mCurrentOrderList = new ArrayList<>();
    private ArrayList<String> mSelectedPositionList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_MY_ORDERS && resultCode == RESULT_OK) {
            SerializableArrayList serializableArrayList = (SerializableArrayList) data.getSerializableExtra("serializableOrderList");
            mCurrentOrderList.clear();
            int orderPosition;
            if (data.getStringExtra("orderPosition") != null) {
                orderPosition = Integer.parseInt(data.getStringExtra("orderPosition"));
                mSelectedPositionList.addAll(data.getStringArrayListExtra("mProductPosition"));
                if (mSelectedPositionList != null) {
                    for (int i = 0; i < mSelectedPositionList.size(); i++) {
                        mRecentOrderList.get(orderPosition).getProducts().get(Integer.parseInt(mSelectedPositionList.get(i))).setStatus("" + ServiceConstants.ORDER_INACTIVE);
                    }
                }
                mCurrentOrderList.addAll(serializableArrayList.getOrdersArrayList());
                mRecentOrdersRecycleAdapters.notifyDataSetChanged();
                mRecentOrdersRecycleAdapters.notifyItemChanged(orderPosition);

            } else {
                mRecentOrdersRecycleAdapters.notifyDataSetChanged();
            }
            getMyOrders();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_title_my_orders);
        getMyOrders();
        btnRecentOrders.setSelected(true);
        ivBack.setOnClickListener(this);
        btnCompletedOrders.setOnClickListener(this);
        btnRecentOrders.setOnClickListener(this);
        mCompletedOrdersRecycleAdapters = new CompletedOrdersRecycleAdapters(this, mCompletedOrderList);
        mRecentOrdersRecycleAdapters = new RecentOrdersRecycleAdapters(this, mRecentOrderList);
        rvMyCompletedOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMyCompletedOrders.setAdapter(mCompletedOrdersRecycleAdapters);
        rvMyRecentOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMyRecentOrders.setAdapter(mRecentOrdersRecycleAdapters);
        mCompletedOrdersRecycleAdapters.setiOnItemClickListener(this);
        mRecentOrdersRecycleAdapters.setiOnItemClickListener(this);

    }

    @Override
    protected void initVariables() {
        rvMyRecentOrders.setVisibility(View.VISIBLE);
        rvMyCompletedOrders.setVisibility(View.GONE);
        mCompletedOrdersRecycleAdapters.notifyDataSetChanged();
        mRecentOrdersRecycleAdapters.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_completed_orders:
                btnCompletedOrders.setSelected(true);
                btnRecentOrders.setSelected(false);
                rvMyCompletedOrders.setVisibility(View.VISIBLE);
                rvMyRecentOrders.setVisibility(View.GONE);
//                if (mCompletedOrderList.isEmpty()) {
//                    ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_no_completed_orders_found);
//                }
                break;
            case R.id.btn_recent_orders:
                btnRecentOrders.setSelected(true);
                btnCompletedOrders.setSelected(false);
                rvMyCompletedOrders.setVisibility(View.GONE);
                rvMyRecentOrders.setVisibility(View.VISIBLE);
//                if (mRecentOrderList.isEmpty()) {
//                    ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_no_recent_orders_found);
//                }
                break;
        }
    }

    /**
     * getting order list from server
     */
    private void getMyOrders() {
        AppDialog.showProgressDialog(MyOrdersActivity.this, true);
        IApiClient client = ApiClient.getApiClient();
        final ReqMyOrders reqMyOrders = new ReqMyOrders();
        reqMyOrders.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqMyOrders.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqMyOrders.setMethod(MethodFactory.GET_ORDERS.getMethod());
        Call<ResMyOrders> call = client.getMyOrders(reqMyOrders);
        call.enqueue(new Callback<ResMyOrders>() {
            @Override
            public void onResponse(Call<ResMyOrders> call, Response<ResMyOrders> response) {
                AppDialog.showProgressDialog(MyOrdersActivity.this, false);
                ResMyOrders resMyOrders = response.body();
                if (resMyOrders != null) {
                    if (resMyOrders.getSuccess() == ServiceConstants.SUCCESS) {
                        mCompletedOrderList.clear();
                        mRecentOrderList.clear();
                        for (int i = 0; i < resMyOrders.getOrderData().size(); i++) {
                            if (Integer.parseInt(resMyOrders.getOrderData().get(i).getOrderStatus()) == ServiceConstants.ORDER_DELIVERED ||
                                    Integer.parseInt(resMyOrders.getOrderData().get(i).getOrderStatus()) == ServiceConstants.ORDER_CANCELLED) {
                                mCompletedOrderList.add(resMyOrders.getOrderData().get(i));
                            } else {
                                mRecentOrderList.add(resMyOrders.getOrderData().get(i));
                            }
                        }
                        mCompletedOrdersRecycleAdapters.notifyDataSetChanged();
                        mRecentOrdersRecycleAdapters.notifyDataSetChanged();
                        // if(isFromResult) {
//                        if (mRecentOrderList.isEmpty()) {
//                            ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_no_recent_orders_found);
//                        } else if (mCompletedOrderList.isEmpty()) {
//                            ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_no_completed_orders_found);
//                            //        }
//                        }
                    } else {
                        //ToastUtils.showShortToast(MyOrdersActivity.this, resMyOrders.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResMyOrders> call, Throwable t) {
                AppDialog.showProgressDialog(MyOrdersActivity.this, false);
                ToastUtils.showShortToast(MyOrdersActivity.this, R.string.err_network_connection);
            }
        });


    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        if (btnCompletedOrders.isSelected()) {
            intent.putExtra("orderId", mCompletedOrderList.get(position).getOrderID());
            startActivityForResult(intent, AppConstants.RC_ORDER_DETAILS);
        } else {
            intent.putExtra("orderId", mRecentOrderList.get(position).getOrderID());
            startActivityForResult(intent, AppConstants.RC_ORDER_DETAILS);
        }
    }
}
