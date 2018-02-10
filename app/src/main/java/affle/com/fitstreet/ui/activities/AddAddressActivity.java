package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.PlaceModel;
import affle.com.fitstreet.models.request.ReqAddUserAddress;
import affle.com.fitstreet.models.response.ResAddUserAddress;
import affle.com.fitstreet.models.response.ResUserAddress;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_pin_code)
    EditText etPinCode;
    @BindView(R.id.et_landmark)
    EditText etLandmark;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save)
    Button btnSave;
    private ProgressDialog mProgressDialog;
    private String mName, mAddress, mMobileNo, mPinCode, mLandmark, mState, mCity;
    private ResUserAddress mUserAddress;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        super.initData();
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        mUserAddress = intent.getParcelableExtra(AppConstants.TAG_ADDRESS_MODEL);
        mPosition = intent.getIntExtra(AppConstants.TAG_POSITION, -1);
        if (getIntent().getData() != null) {
            if ((intent.getStringExtra(AppConstants.TAG_FROM_WHERE)).equals("fromCheckoutActivity"))
                AppDialog.showProgressDialog(this, false);
        }
        tvTitle.setText(R.string.txt_my_addresses);

        if (mUserAddress != null) {
            etName.setText(mUserAddress.getName());
            etAddress.setText(mUserAddress.getAddress());
            etMobileNo.setText(mUserAddress.getPhone());
            etPinCode.setText(mUserAddress.getPincode());
            etLandmark.setText(mUserAddress.getLandmark());
            etState.setText(mUserAddress.getState());
            etCity.setText(mUserAddress.getCity());
        }

        //set listeners
        ivBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etCity.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
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
     * Checking validity of addresses data
     *
     * @return
     */
    private boolean isValid() {
        mName = etName.getText().toString().trim();
        mAddress = etAddress.getText().toString().trim();
        mMobileNo = etMobileNo.getText().toString().trim();
        mPinCode = etPinCode.getText().toString().trim();
        mLandmark = etLandmark.getText().toString().trim();
        mState = etState.getText().toString().trim();
        mCity = etCity.getText().toString().trim();
        if (mName.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_name_empty);
            return false;
        }
        if (mAddress.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_address_empty);
            return false;
        }
        if (mMobileNo.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_mobile_empty);
            return false;
        }
        if (!mMobileNo.isEmpty() && mMobileNo.length() < 10) {
            ToastUtils.showShortToast(this, R.string.err_mobile_invalid);
            return false;
        }
        if (mPinCode.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_pin_code_empty);
            return false;
        }
        if (mPinCode.length() != 6) {
            ToastUtils.showShortToast(this, R.string.err_pin_code_invalid);
            return false;
        }
//        if (mLandmark.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_landmark_empty);
//            return false;
//        }

        if (mCity.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_city_empty);
            return false;
        }
        if (mState.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_state_empty);
            return false;
        }
        return true;
    }

    /**
     * Method used to call the add / update user addresses web service
     */
    public void addAddress() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        final ReqAddUserAddress reqAddUserAddress = new ReqAddUserAddress();
        reqAddUserAddress.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqAddUserAddress.setMethod(MethodFactory.ADD_USER_ADDRESS.getMethod());
        reqAddUserAddress.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqAddUserAddress.setUserAddId(mUserAddress != null ? mUserAddress.getUserAddID() : "");
        reqAddUserAddress.setName(mName);
        reqAddUserAddress.setAddress(mAddress);
        reqAddUserAddress.setPhone(mMobileNo);
        reqAddUserAddress.setPincode(mPinCode);
        reqAddUserAddress.setLandmark(mLandmark);
        reqAddUserAddress.setState(mState);
        reqAddUserAddress.setCity(mCity);
        Call<ResAddUserAddress> resAddUserAddressCall = client.addUserAddress(reqAddUserAddress);
        resAddUserAddressCall.enqueue(new Callback<ResAddUserAddress>() {
            @Override
            public void onResponse(Call<ResAddUserAddress> call, Response<ResAddUserAddress> response) {
                dismissProgressDialog();
                ResAddUserAddress resAddUserAddress = response.body();
                if (resAddUserAddress != null) {
                    if (resAddUserAddress.getSuccess() == ServiceConstants.SUCCESS) {
                        Intent intent = new Intent();
                        intent.putExtra(AppConstants.TAG_ADDRESS_MODEL, resAddUserAddress.getUserAddress());
                        intent.putExtra(AppConstants.TAG_POSITION, mPosition);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        ToastUtils.showShortToast(AddAddressActivity.this, resAddUserAddress.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(AddAddressActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResAddUserAddress> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(AddAddressActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.et_city:
                if (PermissionUtils.hasReadGServicesPermission(this)) {
                    if (NetworkConnection.isNetworkConnected(AddAddressActivity.this)) {
                        Intent intent = new Intent(AddAddressActivity.this, ChooseLocationActivity.class);
                        intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_ADDRESS);
                        startActivityForResult(intent, AppConstants.RC_CHOOSE_LOCATION);
                    } else {
                        ToastUtils.showShortToast(AddAddressActivity.this, R.string.err_network_connection);
                    }
                }
                break;
            case R.id.btn_save:
                if (isValid()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        addAddress();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (permissions.length > 0) {
                        Intent intent = new Intent(AddAddressActivity.this, ChooseLocationActivity.class);
                        intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_ADDRESS);
                        startActivityForResult(intent, AppConstants.RC_CHOOSE_LOCATION);
                    }
                } else {
                    // Permission Denied
                    ToastUtils.showShortToast(this, getString(R.string.err_permission_denied));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_CHOOSE_LOCATION && resultCode == RESULT_OK) {
            PlaceModel placeModel = data.getParcelableExtra(AppConstants.TAG_PLACE_MODEL);
            etCity.setText(placeModel.getName());
            etState.setText(placeModel.getState());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
