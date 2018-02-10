package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.PlacesAutoCompleteAdapter;
import affle.com.fitstreet.models.PlaceModel;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppUtilMethods;
import butterknife.BindView;

public class ChooseLocationActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_cross)
    ImageView ivCross;
    @BindView(R.id.atv_Address)
    AutoCompleteTextView atvAddress;
    @BindView(R.id.lv_address)
    ListView lvAddress;
    private PlacesAutoCompleteAdapter mAdapter;
    private int mFromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        super.initData();
    }

    @Override
    protected void initViews() {
        atvAddress.requestFocus();
        AppUtilMethods.showKeyBoard(this);

        //set listeners
        ivBack.setOnClickListener(this);
        ivCross.setOnClickListener(this);
        lvAddress.setOnItemClickListener(this);
    }

    @Override
    protected void initVariables() {
        mFromWhere = getIntent().getIntExtra(AppConstants.TAG_FROM_WHERE, 0);
        String type = mFromWhere == AppConstants.FROM_PROFILE ? "" : AppConstants.TYPE_CITY;
        mAdapter = new PlacesAutoCompleteAdapter(this, type);
        lvAddress.setAdapter(mAdapter);
        atvAddress.setAdapter(mAdapter);
        atvAddress.setDropDownHeight(0);
        atvAddress.setDropDownWidth(0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        AppUtilMethods.hideKeyBoard(this);
        PlaceModel placeModel = mAdapter.getItem(position);
//        String placeDescription = placeModel.getDescription();
//        int indexOfSeparator = placeDescription.indexOf(',');
//        String place = placeDescription;
//        if (indexOfSeparator > -1) {
//            place = placeDescription.substring(0, indexOfSeparator);
//        }
//        Logger.e("Location " + placeDescription + " ----- " + place);
        Intent intent = new Intent();
        intent.putExtra(AppConstants.TAG_PLACE_MODEL, placeModel);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cross:
                atvAddress.setText("");
                AppUtilMethods.hideKeyBoard(this);
                break;
        }
    }
}
