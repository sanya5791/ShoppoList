package com.akhutornoy.shoppinglist.createroduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createroduct.fragment.CreateProductTypeFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.InShopsAvailableFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.ProductDefaultQuantityFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.ProductNameFragment;

public class CreateProductActivity extends BaseToolbarActivity implements OnStepsNavigation {
    private static final int FLOW_STEPS_COUNT = 4;
    private int mCurrentStep = 0;

    private TextView mTvProgressStep;
    private ProgressBar mProgressBar;

    public static Intent createIntent(Context context) {
        return new Intent(context, CreateProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_create_product);
        initViews();
        showNextStep();
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_create_product;
    }

    @Override
    @LayoutRes
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress_bar);
        mTvProgressStep = findViewById(R.id.tv_progress_title);
    }

    private void showNextStep() {
        if (mCurrentStep >= FLOW_STEPS_COUNT) {
            finish();
            return;
        }
        ++mCurrentStep;
        showCurrentStep();
    }

    @Override
    public void onBackPressed() {
        showPreviousStep();
    }

    private void showPreviousStep() {
        if (mCurrentStep <= 1) {
            finish();
            return;
        }
        --mCurrentStep;
        showCurrentStep();
    }

    private void showCurrentStep() {
        setTitlesForStep();
        showFragment(getFragmentForStep());
    }

    private void setTitlesForStep() {
        setToolbarSubTitle(getToolbarSubtitleForStep());
        mProgressBar.setProgress(mCurrentStep);
        mTvProgressStep.setText(getString(R.string.create_product_progress_step, mCurrentStep));
    }

    private int getToolbarSubtitleForStep() {
        switch (mCurrentStep) {
            case 1:
                return R.string.create_product_subtitle_step_1;
            case 2:
                return R.string.create_product_subtitle_step_2;
            case 3:
                return R.string.create_product_subtitle_step_3;
            case 4:
                return R.string.create_product_subtitle_step_4;
            default:
                throw new IllegalArgumentException("No Subtitle for step " + mCurrentStep);
        }
    }

    private Fragment getFragmentForStep() {
        switch (mCurrentStep) {
            case 1:
                return ProductNameFragment.newInstance();
            case 2:
                return CreateProductTypeFragment.newInstance();
            case 3:
                return ProductDefaultQuantityFragment.newInstance();
            case 4:
            return InShopsAvailableFragment.newInstance();
            default:
                throw new IllegalArgumentException("No Fragment for step " + mCurrentStep);
        }
    }

    @Override
    public void onStepFinished() {
        showNextStep();
    }
}
