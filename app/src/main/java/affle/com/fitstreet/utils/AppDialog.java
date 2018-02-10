package affle.com.fitstreet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.FsProductFiltersListAdapter;
import affle.com.fitstreet.adapters.ProductFiltersListAdapter;
import affle.com.fitstreet.interfaces.IOnDateSet;
import affle.com.fitstreet.models.response.ResFsStoreProductsList;
import affle.com.fitstreet.models.response.ResGetFilters;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.ui.activities.CheckoutActivity;
import affle.com.fitstreet.ui.activities.FsStoreMenWomenActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.activities.LoginActivity;
import affle.com.fitstreet.ui.activities.LoginOptionsActivity;
import affle.com.fitstreet.ui.activities.MyAddressesActivity;
import affle.com.fitstreet.ui.activities.MyOrdersViewActivity;
import affle.com.fitstreet.ui.activities.ProductsListActivity;
import affle.com.fitstreet.ui.activities.TrendingActivity;
import affle.com.fitstreet.ui.activities.TutorialActivity;

/**
 * Created by root on 3/22/16.
 */
public class AppDialog {
    private static ProgressDialog progressDialog;

    /**
     * method to open Date picker dialog
     */
    public static void showDatePickerDialog(final Context context, final IOnDateSet iOnDateSet, boolean isFutureDateAllowed) {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearSelected, int monthOfYear, int dayOfMonth) {
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (!isFutureDateAllowed) {
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
        }
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            DatePicker datePicker = dialog.getDatePicker();
                                            int dayOfMonth = datePicker.getDayOfMonth();
                                            int month = datePicker.getMonth();
                                            int year = datePicker.getYear();
                                            Calendar instance = Calendar.getInstance();
                                            int currentDayOfMonth = instance.get(Calendar.DAY_OF_MONTH);
                                            int currentMonth = instance.get(Calendar.MONTH);
                                            int currentYear = instance.get(Calendar.YEAR);
                                            if (year < currentYear) {
                                                iOnDateSet.onDateSet(year, month, dayOfMonth);
                                                dialog.dismiss();
                                                return;
                                            } else if (year == currentYear) {
                                                if (month < currentMonth) {
                                                    iOnDateSet.onDateSet(year, month, dayOfMonth);
                                                    dialog.dismiss();
                                                    return;
                                                } else if (month == currentMonth) {
                                                    if (dayOfMonth <= currentDayOfMonth) {
                                                        iOnDateSet.onDateSet(year, month, dayOfMonth);
                                                        dialog.dismiss();
                                                        return;
                                                    }
                                                }
                                            }
                                            ToastUtils.showShortToast(view.getContext(), R.string.err_date_invalid);
                                        }
                                    }
                );
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setMessage(context.getString(R.string.dialog_please_wait));
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * This method is used to show/dismiss dialog.
     *
     * @param activity activity context.
     * @param flag     set true to show dialog otherwise false.
     */
    public static void showProgressDialog(final Activity activity, final boolean flag) {
////
        if (flag) {
            if (progressDialog == null) {
                progressDialog = showProgressDialog(activity);
                progressDialog.show();
            }
        } else
//        else if (progressDialog != null && progressDialog.isShowing())
        {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }
//
//
//        //why this need
//
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (flag) {
//                    progressDialog.show();
//                } else {
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                }
//            }
//        });

    }

    public static void showForgotPasswordDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);
        final EditText etEmailId = (EditText) dialog.findViewById(R.id.et_email_forgot_pwd);
        dialog.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof LoginActivity) {
                    String emailId = etEmailId.getText().toString().trim();
                    if (emailId.isEmpty()) {
                        ToastUtils.showShortToast(context, R.string.err_email_empty);
                        return;
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                        ToastUtils.showShortToast(context, R.string.err_email_invalid);
                        return;
                    }
                    ((LoginActivity) context).forgotPassword(emailId);
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showCancelOrderDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_order);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MyOrdersViewActivity) {
                    ((MyOrdersViewActivity) context).cancelOrder();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showPlaceOrderDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_place_order);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                //to clear/remove any activities related to the task
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((CheckoutActivity) context).finish();
                context.startActivity(intent);

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * This method is used to show product filters popup dialog.
     *
     * @param activity
     * @return Dialog
     */
    public static Dialog showProductFiltersDialog(final Activity activity, int xOffset, int yOffset, final String searchText, List<String> filterTypeList,
                                                  final List<ResGetFilters.ResPartnerData> partnerDataList, final List<String> selectedPartnersList,
                                                  final List<ResGetFilters.ResBrandData> brandDataList, final List<String> selectedBrandsList,
                                                  final List<Integer> rangeList, final List<Boolean> selectedGenderList, final int pageIndex) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_filters);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);

        ImageView ivFilterIcon = (ImageView) dialog.findViewById(R.id.iv_filter_icon);
        LinearLayout llFilter = (LinearLayout) dialog.findViewById(R.id.ll_filters);
        final ExpandableListView elvFilters = (ExpandableListView) dialog.findViewById(R.id.elv_product_filters);

        TextView tvApply = (TextView) dialog.findViewById(R.id.tv_apply);
        TextView tvReset = (TextView) dialog.findViewById(R.id.tv_reset);

        ivFilterIcon.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivFilterIcon.getLayoutParams();
        params.rightMargin = (ivFilterIcon.getMeasuredWidth() - (ivFilterIcon.getMeasuredWidth() / 2));

        llFilter.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        LinearLayout.LayoutParams parentParams = (LinearLayout.LayoutParams) llFilter.getLayoutParams();
        parentParams.rightMargin = ivFilterIcon.getMeasuredWidth() / 2;
        parentParams.topMargin = yOffset;

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
        window.setAttributes(layoutParams);

        final ProductFiltersListAdapter adapter = new ProductFiltersListAdapter(activity, filterTypeList, partnerDataList, selectedPartnersList,
                brandDataList, selectedBrandsList, rangeList, selectedGenderList);
        elvFilters.setAdapter(adapter);


        elvFilters.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                if (groupPosition == 1) {
                    String brandId = brandDataList.get(childPosition).getBrandID();
                    if (selectedBrandsList.contains(brandId)) {
                        selectedBrandsList.remove(brandId);
                    } else {
                        selectedBrandsList.add(brandId);
                    }
                    adapter.notifyDataSetChanged();
                    Logger.e("selected partner id list -----> " + selectedBrandsList);
                    return true;
                } else if (groupPosition == 2) {
                    String partnerID = partnerDataList.get(childPosition).getPartnerID();
                    if (selectedPartnersList.contains(partnerID)) {
                        selectedPartnersList.remove(partnerID);
                    } else {
                        selectedPartnersList.add(partnerID);
                    }
                    adapter.notifyDataSetChanged();
                    Logger.e("selected partner id list -----> " + selectedPartnersList);
                    return true;
                }
                return false;
            }
        });

        elvFilters.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long id) {
                int lastOpenedPosition = adapter.getLastOpenedPosition();
                if (lastOpenedPosition != position) {
                    elvFilters.collapseGroup(lastOpenedPosition);
                    elvFilters.expandGroup(position);
                    adapter.setLastOpenedPosition(position);
                } else {
                    elvFilters.collapseGroup(lastOpenedPosition);
                    adapter.setLastOpenedPosition(-1);
                }
//                if (expandableListView.isGroupExpanded(position)) {
//                    expandableListView.collapseGroup(position);
//                } else {
//                    expandableListView.expandGroup(position);
//                }
                return true;
            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (NetworkConnection.isNetworkConnected(activity)) {
                    int minPrice = adapter.getMinRange();
                    int maxPrice = adapter.getMaxRange();
                    boolean isMaleSelected = adapter.isMaleSelected();
                    boolean isFemaleSelected = adapter.isFemaleSelected();
                    //todo why is below code needed
//                    rangeList.set(0, minPrice);
//                    rangeList.set(1, maxPrice);
                    selectedGenderList.set(0, isMaleSelected);
                    selectedGenderList.set(1, isFemaleSelected);
                    String gender = ((isMaleSelected && isFemaleSelected) || (!isMaleSelected && !isFemaleSelected)) ?
                            "" :
                            (isMaleSelected ? String.valueOf(ServiceConstants.GENDER_MALE) : String.valueOf(ServiceConstants.GENDER_FEMALE));
                    String minRange;
                    String maxRange;
                    if(activity instanceof ProductsListActivity) {
                        if (((ProductsListActivity) (activity)).mSelectedRangeForAffiliateProducts != null && ((ProductsListActivity) (activity)).mSelectedRangeForAffiliateProducts.size() > 0) {
                            minPrice = ((ProductsListActivity) (activity)).mSelectedRangeForAffiliateProducts.get(0);
                            maxPrice = ((ProductsListActivity) (activity)).mSelectedRangeForAffiliateProducts.get(1);
                        }
                    }
                    else if(activity instanceof TrendingActivity) {
                        if (((TrendingActivity) (activity)).mSelectedRangeForTrendingProducts != null && ((TrendingActivity) (activity)).mSelectedRangeForTrendingProducts.size() > 0) {
                            minPrice = ((TrendingActivity) (activity)).mSelectedRangeForTrendingProducts.get(0);
                            maxPrice = ((TrendingActivity) (activity)).mSelectedRangeForTrendingProducts.get(1);
                        }
                    }
                    if (minPrice == AppConstants.FILTER_MIN && maxPrice == AppConstants.FILTER_MAX) {
                        minRange = "";
                        maxRange = "";
                    } else {
                        minRange = String.valueOf(minPrice);
                        maxRange = String.valueOf(maxPrice);
                    }
                    Logger.e("BRAND LIST ------> " + selectedBrandsList);
                    Logger.e("PARTNERS LIST ------> " + selectedPartnersList);
                    Logger.e("GENDER ------> " + gender);
                    Logger.e("MIN RANGE ------> " + minRange);
                    Logger.e("MAX RANGE ------> " + maxRange);
                    if (activity instanceof ProductsListActivity) {
                        ((ProductsListActivity) activity).getProductsList(searchText, gender, minRange, maxRange, 0, 1, true);
                        if (selectedPartnersList.size() > 0 || selectedBrandsList.size() > 0 ||
                                isMaleSelected || isFemaleSelected ||
                                minPrice != AppConstants.FILTER_MIN || maxPrice != AppConstants.FILTER_MAX) {
                            ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.VISIBLE);
                        } else {
                            ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.GONE);
                        }
                    } else if (activity instanceof TrendingActivity) {
                        ((TrendingActivity) activity).getTrendingProducts(searchText, gender, minRange, maxRange, 0, 1, true);
                        if (selectedPartnersList.size() > 0 || selectedBrandsList.size() > 0 ||
                                isMaleSelected || isFemaleSelected ||
                                minPrice != AppConstants.FILTER_MIN || maxPrice != AppConstants.FILTER_MAX) {
                            ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.VISIBLE, true);
                        } else {
                            ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.GONE, false);
                        }
                    }
                } else {
                    ToastUtils.showShortToast(activity, R.string.err_network_connection);
                }
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectedPartnersList.clear();
                selectedBrandsList.clear();
                //range change 11 aug 2016


                rangeList.set(0, AppConstants.FILTER_MIN);
                rangeList.set(1, AppConstants.FILTER_MAX);
                selectedGenderList.set(0, false);
                selectedGenderList.set(1, false);
                adapter.setMaleSelected(false);
                adapter.setFemaleSelected(false);
                adapter.setMinRange(AppConstants.FILTER_MIN);
                adapter.setMaxRange(AppConstants.FILTER_MAX);
                adapter.notifyDataSetChanged();
                if (activity instanceof ProductsListActivity) {
                    ((ProductsListActivity) activity).mSelectedRangeForAffiliateProducts.clear();
                    ((ProductsListActivity) activity).getProductsList(searchText, "", "", "", 0, 1, true);
                    ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.GONE);
                } else if (activity instanceof TrendingActivity) {
                    ((TrendingActivity) activity).mSelectedRangeForTrendingProducts.clear();
                    ((TrendingActivity) activity).getTrendingProducts(searchText, "", "", "", 1, 1, true);
                    ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.GONE, false);
                }
            }
        });

        dialog.show();
        return dialog;
    }

    public static Dialog showFSProductFiltersDialog(final Activity activity, final int gender, int xOffset, int yOffset, final String searchText, List<String> filterTypeList,
                                                    final List<ResFsStoreProductsList.FsCatDatum> categoryDataList, final List<String> selectedCategoriesList,
                                                    final List<Integer> rangeList, final int pageIndex) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_filters);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);

        ImageView ivFilterIcon = (ImageView) dialog.findViewById(R.id.iv_filter_icon);
        LinearLayout llFilter = (LinearLayout) dialog.findViewById(R.id.ll_filters);
        final ExpandableListView elvFilters = (ExpandableListView) dialog.findViewById(R.id.elv_product_filters);
        TextView tvApply = (TextView) dialog.findViewById(R.id.tv_apply);
        TextView tvReset = (TextView) dialog.findViewById(R.id.tv_reset);

        ivFilterIcon.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivFilterIcon.getLayoutParams();
        params.rightMargin = (ivFilterIcon.getMeasuredWidth() - (ivFilterIcon.getMeasuredWidth() / 2));

        llFilter.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        LinearLayout.LayoutParams parentParams = (LinearLayout.LayoutParams) llFilter.getLayoutParams();
        parentParams.rightMargin = ivFilterIcon.getMeasuredWidth() / 2;
        parentParams.topMargin = yOffset;

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
        window.setAttributes(layoutParams);

        final FsProductFiltersListAdapter adapter = new FsProductFiltersListAdapter(activity, filterTypeList, categoryDataList, selectedCategoriesList, rangeList);
        elvFilters.setAdapter(adapter);

        elvFilters.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    String proCatID = categoryDataList.get(childPosition).getProCatID();
                    if (selectedCategoriesList.contains(proCatID)) {
                        selectedCategoriesList.remove(proCatID);
                    } else {
                        selectedCategoriesList.add(proCatID);
                    }
                    adapter.notifyDataSetChanged();
                    Logger.e("selected partner id list -----> " + selectedCategoriesList);
                    return true;
                }
                return false;
            }
        });

        elvFilters.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long id) {
                int lastOpenedPosition = adapter.getLastOpenedPosition();
                if (lastOpenedPosition != position) {
                    elvFilters.collapseGroup(lastOpenedPosition);
                    elvFilters.expandGroup(position);
                    adapter.setLastOpenedPosition(position);
                } else {
                    elvFilters.collapseGroup(lastOpenedPosition);
                    adapter.setLastOpenedPosition(-1);
                }
//                if (expandableListView.isGroupExpanded(position)) {
//                    expandableListView.collapseGroup(position);
//                } else {
//                    expandableListView.expandGroup(position);
//                }
                return true;
            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (NetworkConnection.isNetworkConnected(activity)) {
                    int minPrice = adapter.getMinRange();
                    int maxPrice = adapter.getMaxRange();
                    //boolean isMaleSelected = adapter.isMaleSelected();
                    // boolean isFemaleSelected = adapter.isFemaleSelected();

                    //added on 26 dec 2016 to get the selected range of seek bar in filter dialog
                    if (((FsStoreMenWomenActivity) (activity)).mSelectedRangeForFSProducts != null && ((FsStoreMenWomenActivity) (activity)).mSelectedRangeForFSProducts.size() > 0) {
                        minPrice = ((FsStoreMenWomenActivity) (activity)).mSelectedRangeForFSProducts.get(0);
                        maxPrice = ((FsStoreMenWomenActivity) (activity)).mSelectedRangeForFSProducts.get(1);
                    }
                    //todo why is below code needed
//                    rangeList.set(0, minPrice);
//                    rangeList.set(1, maxPrice);
                    //selectedGenderList.set(0, isMaleSelected);

                    //selectedGenderList.set(1, isFemaleSelected);
//                    String mGender = ((isMaleSelected && isFemaleSelected) || (!isMaleSelected && !isFemaleSelected)) ?
//                            "" :
//                            (isMaleSelected ? String.valueOf(ServiceConstants.GENDER_MALE) : String.valueOf(ServiceConstants.GENDER_FEMALE));
                    String minRange;
                    String maxRange;
                    if (minPrice == AppConstants.FILTER_MIN && maxPrice == AppConstants.FILTER_MAX) {
                        minRange = "";
                        maxRange = "";
                    } else {
                        minRange = String.valueOf(minPrice);
                        maxRange = String.valueOf(maxPrice);
                    }
//                    rangeList.set(0,Integer.parseInt(minRange));
//                    rangeList.set(1,Integer.parseInt(maxRange));
//                    adapter.setMinRange(Integer.parseInt(minRange));
//                    adapter.setMaxRange(Integer.parseInt(maxRange));
//                    adapter.notifyDataSetChanged();
                    //   Logger.e("BRAND LIST ------> " + selectedBrandsList);
                    Logger.e("PARTNERS LIST ------> " + selectedCategoriesList);
                    //  Logger.e("GENDER ------> " + mGender);
                    Logger.e("MIN RANGE ------> " + minRange);
                    Logger.e("MAX RANGE ------> " + maxRange);
//                    if (activity instanceof ProductsListActivity) {
//                        ((ProductsListActivity) activity).getProductsList(searchText, "", minRange, maxRange, 0);
//                        if (selectedCategoriesList.size() > 0 || minPrice != AppConstants.FILTER_MIN || maxPrice != AppConstants.FILTER_MAX) {
//                            ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.VISIBLE);
//                        } else {
//                            ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.GONE);
//                        }

                    if (activity instanceof FsStoreMenWomenActivity) {
                        ((FsStoreMenWomenActivity) activity).getFsStoreProducts("", gender, maxRange, minRange, 1, true);
                        if (selectedCategoriesList.size() > 0 || minPrice != AppConstants.FILTER_MIN || maxPrice != AppConstants.FILTER_MAX) {
                            ((FsStoreMenWomenActivity) activity).setFilterIndicatorVisibility(View.VISIBLE);
                        } else {
                            ((FsStoreMenWomenActivity) activity).setFilterIndicatorVisibility(View.GONE);
                        }

//                    } else if (activity instanceof TrendingActivity) {
//                        ((TrendingActivity) activity).getTrendingProducts(searchText, mGender, minRange, maxRange, 0);
//                        if (selectedCategoriesList.size() > 0 || selectedBrandsList.size() > 0 ||
//                                isMaleSelected || isFemaleSelected ||
//                                minPrice != AppConstants.FILTER_MIN || maxPrice != AppConstants.FILTER_MAX) {
//                            ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.VISIBLE, true);
//                        } else {
//                            ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.GONE, false);
//                        }
//                    }
                    } else {
                        ToastUtils.showShortToast(activity, R.string.err_network_connection);
                    }
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectedCategoriesList.clear();
                //range change 11 aug 2016
                ((FsStoreMenWomenActivity) activity).mSelectedRangeForFSProducts.clear();
                rangeList.set(0, AppConstants.FILTER_MIN);
                rangeList.set(1, AppConstants.FILTER_MAX);
                adapter.setMinRange(AppConstants.FILTER_MIN);
                adapter.setMaxRange(AppConstants.FILTER_MAX);
                adapter.notifyDataSetChanged();
                if (activity instanceof ProductsListActivity) {
                    ((ProductsListActivity) activity).getProductsList(searchText, "", "", "", 0, 1, true);
                    ((ProductsListActivity) activity).setFilterIndicatorVisibility(View.GONE);
                } else if (activity instanceof TrendingActivity) {
                    ((TrendingActivity) activity).getTrendingProducts(searchText, "", "", "", 0, 1, true);
                    ((TrendingActivity) activity).setProductFilterIndicatorVisibility(View.GONE, false);
                } else if (activity instanceof FsStoreMenWomenActivity) {
                    ((FsStoreMenWomenActivity) activity).getFsStoreProducts("", gender, "", "", 1, true);
                    ((FsStoreMenWomenActivity) activity).setFilterIndicatorVisibility(View.GONE);
                }
            }
        });

        dialog.show();
        return dialog;
    }


    public static void showLoginAlertDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_login)
                .setNegativeButton(R.string.txt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, LoginOptionsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        dialogInterface.dismiss();
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static void showVerifyEmailAlertDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_verify_email)
                .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static void showCannotChangePasswordAlertDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_cannot_change_pwd)
                .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static void showLogoutAlertDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_logout)
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Activity activity = (Activity) context;
                        if (NetworkConnection.isNetworkConnected(context))
                            SocialNetworkUtils.getInstance(activity).logoutFromFb(activity, SimpleFacebook.getInstance(activity));
                        File file = new File(AppConstants.FITSTREET_DIRECTORY_PATH);
                        if (!file.isDirectory())
                            file.mkdir();
                        try {
                            File f = new File(AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH);
                            if (f.isFile()) {
                                f.delete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        AppSharedPreference.getInstance(context).clearEditor();
                        Intent intent = new Intent(context, TutorialActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        activity.finish();
                        dialogInterface.dismiss();
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static void showExitAlertDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_exit)
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity) context).finish();
                        dialogInterface.dismiss();
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }

    public static void showRemoveAddressAlertDialog(final Context context, final int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.alert_remove_address)
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (context instanceof MyAddressesActivity) {
                            ((MyAddressesActivity) context).deleteAddress(position);
                        }
                    }
                }).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppUtilMethods.overrideDialogFonts(context, dialog);
    }
}
