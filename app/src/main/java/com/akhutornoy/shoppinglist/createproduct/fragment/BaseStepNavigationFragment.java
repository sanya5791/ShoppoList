package com.akhutornoy.shoppinglist.createproduct.fragment;

import android.content.Context;
import android.support.annotation.Nullable;

import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.createproduct.OnStepsNavigation;

public abstract class BaseStepNavigationFragment extends BaseFragment {
    protected static final String ARG_PRODUCT_NAME = "ARG_PRODUCT_NAME";
    protected OnStepsNavigation mOnStepsNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepsNavigation) {
            mOnStepsNavigation = (OnStepsNavigation) context;
        } else {
            throw new IllegalArgumentException("Host Activity for fragment should implement " + OnStepsNavigation.class.getSimpleName());
        }
    }

    @Nullable
    protected String getProductName() {
        return getArgumentString(ARG_PRODUCT_NAME);
    }
}
