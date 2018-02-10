package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.MyOrdersViewRecyclerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.customviews.SerializableArrayList;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqCancelOrder;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.GridSpacingItemDecoration;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersViewActivity extends BaseActivity implements IOnItemClickListener {
    @BindView(R.id.rv_my_orders_list)
    RecyclerView rvMyOrdersList;
    @BindView(R.id.tv_order_number)
    CustomTypefaceTextView tvOrderNumber;
    @BindView(R.id.tv_select_all)
    CustomTypefaceTextView tvSelectAll;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private String mOrderId;
    private String mOrderPosition;
    private List<ResMyOrders.Product> mArrList;
    private MyOrdersViewRecyclerAdapter mMyOrdersViewRecyclerAdapter;
    @BindView(R.id.tv_cancel_item)
    CustomTypefaceTextView tvCancelItem;
    private ArrayList<String> mSelectedProductList = new ArrayList<>();
    private ArrayList<String> mSelectedPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_view);
        super.initData();
        Intent intent = getIntent();
        mOrderId = intent.getStringExtra("orderId");
        mOrderPosition = String.valueOf(intent.getExtras().get("orderPosition"));
        SerializableArrayList serializableArrayList = (SerializableArrayList) intent.getSerializableExtra("serializableOrderList");
        mArrList = serializableArrayList.getOrdersArrayList();
        rvMyOrdersList.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyOrdersViewActivity.this, 2);
        rvMyOrdersList.setLayoutManager(gridLayoutManager);
        mMyOrdersViewRecyclerAdapter = new MyOrdersViewRecyclerAdapter(MyOrdersViewActivity.this, mArrList);
        rvMyOrdersList.setAdapter(mMyOrdersViewRecyclerAdapter);
        tvOrderNumber.setText(getString(R.string.txt_order_num_) + mOrderId);


    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_my_orders);
        mMyOrdersViewRecyclerAdapter.setiOnItemClickListener(this);
        tvCancelItem.setOnClickListener(this);
        tvSelectAll.setOnClickListener(this);
    }

    /**
     * Setting product IDs that are selected to be cancelled.
     *
     * @param arrayList        arraylist of IDs of products
     * @param selectedPosition selected positions of the products
     */
    public void setProductIds(ArrayList<String> arrayList, ArrayList<String> selectedPosition) {
        mSelectedProductList.clear();
        mSelectedPositionList.clear();
        mSelectedProductList.addAll(arrayList);
        mSelectedPositionList.addAll(selectedPosition);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_cancel_item:
                if (!mSelectedProductList.isEmpty()) {
                    AppDialog.showCancelOrderDialog(this);
                } else {
                    ToastUtils.showShortToast(this, getString(R.string.select_products_first));
                }
                break;
            case R.id.tv_select_all:
                for (int i = 0; i < mArrList.size(); i++) {
                    mArrList.get(i).setSelected(true);
                }
                mMyOrdersViewRecyclerAdapter.notifyDataSetChanged();
                break;

        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("orderPosition", mOrderPosition);
        intent.putStringArrayListExtra("mProductPosition", mSelectedPositionList);
        intent.putExtra("serializableOrderList", new SerializableArrayList(mArrList));
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * To Cancel any order product wise.
     */
    public void cancelOrder() {
        AppDialog.showProgressDialog(this, true);
        IApiClient apiClient = ApiClient.getApiClient();
        ReqCancelOrder reqCancelOrder = new ReqCancelOrder();
        reqCancelOrder.setMethod(MethodFactory.CANCEL_ORDER.getMethod());
        reqCancelOrder.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCancelOrder.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCancelOrder.setOrderID(mOrderId);
        ArrayList<ReqCancelOrder.ProDatum> arrayList = new ArrayList<>();

        for (int i = 0; i < mSelectedProductList.size(); i++) {
            ReqCancelOrder.ProDatum proDatum = new ReqCancelOrder.ProDatum();
            proDatum.setOrdProMapID(mSelectedProductList.get(i));
            arrayList.add(proDatum);
        }
        reqCancelOrder.setProData(arrayList);

        Call<ResBase> call = apiClient.cancelOrder(reqCancelOrder);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(MyOrdersViewActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(MyOrdersViewActivity.this, resBase.getErrstr());
                        for (int i = 0; i < mSelectedPositionList.size(); i++) {
                            mArrList.get(Integer.parseInt(mSelectedPositionList.get(i))).setStatus("" + ServiceConstants.ORDER_INACTIVE);
                        }
                        if (mSelectedPositionList != null && mSelectedPositionList.size() > 0) {
                            for (int j = 0; j < mSelectedPositionList.size(); j++) {
                                mMyOrdersViewRecyclerAdapter.notifyItemChanged(Integer.parseInt(mSelectedPositionList.get(j)));
                            }
                        }
                    } else {
                        ToastUtils.showShortToast(MyOrdersViewActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(MyOrdersViewActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(MyOrdersViewActivity.this, false);
                ToastUtils.showShortToast(MyOrdersViewActivity.this, R.string.err_network_connection);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position, int tag) {
    }
}
