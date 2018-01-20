package com.akhutornoy.shoppinglist.createproduct.fragment;

import android.content.Context;

import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.createproduct.OnStepsNavigation;

public abstract class BaseStepNavigationFragment extends BaseFragment {
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
}
