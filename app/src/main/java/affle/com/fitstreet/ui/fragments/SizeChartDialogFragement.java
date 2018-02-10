package affle.com.fitstreet.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import affle.com.fitstreet.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akash on 7/9/16.
 */
public class SizeChartDialogFragement extends DialogFragment implements View.OnClickListener {
    @BindView(R.id.iv_size_chart)
    ImageView ivSizeChart;
    @BindView(R.id.iv_cross_dialog)
    ImageView ivCrossDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.size_chart_dialog, container, false);
        ButterKnife.bind(this, v);
//        ivCrossDialog = (ImageView) v.findViewById(R.id.iv_cross_dialog);
//        ivSizeChart= (ImageView) v.findViewById(R.id.iv_size_chart);
        ivCrossDialog.setOnClickListener(this);
        ivSizeChart.setOnClickListener(this);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cross_dialog:
                if (this != null && this.isVisible()) {

                    getActivity().onBackPressed();
                }
                break;
            case R.id.iv_size_chart:

                break;
        }
    }
}
