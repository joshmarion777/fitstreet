package affle.com.fitstreet.ui.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.NotificationRecyclerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqDeleteAllNotification;
import affle.com.fitstreet.models.request.ReqGetPushNotification;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResPushNotification;
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

public class NotificationActivity extends BaseActivity implements IOnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.iv_no_new_notification)
    CustomTypefaceTextView ivNoNewNotification;
    @BindView(R.id.tv_clear_all)
    CustomTypefaceTextView tvClearAll;
    @BindView(R.id.rv_notifications)
    RecyclerView rvNotifications;
    private NotificationRecyclerAdapter mNotificationRecyclerAdapter;
    private ArrayList<ResPushNotification.NotificationDatum> mNotificationList = new ArrayList<>();
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        super.initData();
        mIntent = getIntent();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_notifications);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        tvClearAll.setOnClickListener(this);
        mNotificationRecyclerAdapter = new NotificationRecyclerAdapter(this, mNotificationList);
        rvNotifications.setAdapter(mNotificationRecyclerAdapter);
        mNotificationRecyclerAdapter.setOnItemClickListener(this);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        getNotifications();
    }

    @Override
    public void onBackPressed() {
        if (mIntent.getExtras() != null) {
            if (mIntent.getStringExtra(AppConstants.TAG_FROM_WHERE) != null && !mIntent.getStringExtra(AppConstants.TAG_FROM_WHERE).equals("")) {
                if (mIntent.getStringExtra(AppConstants.TAG_FROM_WHERE).equals(AppConstants.FROM_NOTIFICATION_ON_STATUS_BAR)) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }
        finish();
        super.onBackPressed();
    }

    /**
     * Getting user's notifications from server
     */
    public void getNotifications() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqGetPushNotification reqGetPushNotification = new ReqGetPushNotification();
        reqGetPushNotification.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetPushNotification.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, getString(R.string.txt_skip_user_id)));
        reqGetPushNotification.setMethod(MethodFactory.GET_PUSH_NOTIFICATION.getMethod());
        Call<ResPushNotification> call = client.getPushNotification(reqGetPushNotification);
        call.enqueue(new Callback<ResPushNotification>() {
            @Override
            public void onResponse(Call<ResPushNotification> call, Response<ResPushNotification> response) {
                AppDialog.showProgressDialog(NotificationActivity.this, false);
                ResPushNotification resPushNotification = response.body();
                if (resPushNotification != null) {
                    if (resPushNotification.getSuccess() == ServiceConstants.SUCCESS) {
                        mNotificationList.clear();
                        mNotificationList.addAll(resPushNotification.getNotificationData());
                        if (mNotificationList.size() > 0) {
                            rvNotifications.setVisibility(View.VISIBLE);
                            ivNoNewNotification.setVisibility(View.GONE);
                            tvClearAll.setVisibility(View.VISIBLE);
                        }
                        mNotificationRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        rvNotifications.setVisibility(View.GONE);
                        ivNoNewNotification.setVisibility(View.VISIBLE);
                        tvClearAll.setVisibility(View.GONE);
                        //ToastUtils.showShortToast(NotificationActivity.this, resPushNotification.getErrstr());
                    }
                } else {
                    rvNotifications.setVisibility(View.GONE);
                    ivNoNewNotification.setVisibility(View.VISIBLE);
                    tvClearAll.setVisibility(View.GONE);
                    ToastUtils.showShortToast(NotificationActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResPushNotification> call, Throwable t) {
                AppDialog.showProgressDialog(NotificationActivity.this, false);
                rvNotifications.setVisibility(View.GONE);
                ivNoNewNotification.setVisibility(View.VISIBLE);
                tvClearAll.setVisibility(View.GONE);
                ToastUtils.showShortToast(NotificationActivity.this, R.string.err_network_connection);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_clear_all:
                if (NetworkConnection.isNetworkConnected(this)) {
                    deleteAllNotifications();

                } else {
                    ToastUtils.showShortToast(NotificationActivity.this, R.string.err_network_connection);
                }
                break;
        }
    }

    /**
     * Deleting all notifications of user
     */
    private void deleteAllNotifications() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqDeleteAllNotification reqDeleteAllNotification = new ReqDeleteAllNotification();
        reqDeleteAllNotification.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
        reqDeleteAllNotification.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqDeleteAllNotification.setMethod(MethodFactory.DELETE_ALL_NOTIFICATIONS.getMethod());
        Call<ResBase> call = client.deleteAllNotification(reqDeleteAllNotification);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(NotificationActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        //ToastUtils.showShortToast(NotificationActivity.this, resBase.getErrstr());
                        mNotificationList.clear();
                        mNotificationRecyclerAdapter.notifyDataSetChanged();
                        ivNoNewNotification.setVisibility(View.VISIBLE);
                        tvClearAll.setVisibility(View.GONE);
                    } else
                        ToastUtils.showShortToast(NotificationActivity.this, resBase.getErrstr());
                } else {
                    ToastUtils.showShortToast(NotificationActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(NotificationActivity.this, false);
                ToastUtils.showShortToast(NotificationActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent intent;
        tag = Integer.parseInt(((ResPushNotification.NotificationDatum) view.getTag()).getNotifyType());
        if (tag == AppConstants.NOTIFICATION_NEW_CHALLENGE_CREATED) {
            intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_NOTIFICATION_ACTIVITY);
            startActivity(intent);
        } else if (tag == AppConstants.NOTIFICATION_ORDER_DISPATCHED) {
            intent = new Intent(this, MyOrdersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (tag == AppConstants.NOTIFICATION_PRODUCT_ADDED) {
            intent = new Intent(this, FsStoreMenWomenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            if (((ResPushNotification.NotificationDatum) view.getTag()).getGender().equals(String.valueOf(AppConstants.GENDER_FEMALE)))
                intent.putExtra("FsStoreGender", AppConstants.GENDER_FEMALE);
            else
                intent.putExtra("FsStoreGender", AppConstants.GENDER_MALE);
            intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_NOTIFICATION_ACTIVITY);
            startActivity(intent);
        } else if (tag == AppConstants.NOTIFICATION_CHALLENGE_WINNER) {
            intent = new Intent(this, PrizeMoneyDetailsActivity.class);
            startActivity(intent);
        }

    }
}
