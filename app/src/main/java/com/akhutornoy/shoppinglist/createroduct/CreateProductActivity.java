package com.akhutornoy.shoppinglist.createroduct;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.createroduct.fragment.CreateProductTypeFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.InShopsAvailableFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.ProductDefaultQuantityFragment;
import com.akhutornoy.shoppinglist.createroduct.fragment.ProductNameFragment;

public class CreateProductActivity extends AppCompatActivity implements OnStepsNavigation {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Toolbar mToolbar;
    private TextView mTvProgressStep;
    private ProgressBar mProgressBar;

    public static Intent createIntent(Context context) {
        return new Intent(context, CreateProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        initToolbar();
        initViewPager();
        initViews();
        setProgressStep(1);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initToolbarBackButton(mToolbar);
        mToolbar.setTitle(R.string.title_create_product);
    }

    private void initToolbarBackButton(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(getOnPageChangeListener());
    }

    @NonNull
    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                setProgressStep(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        };
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress_bar);
        mTvProgressStep = findViewById(R.id.tv_progress_title);
    }

    private void setProgressStep(int step) {
        mToolbar.setSubtitle(getSubtitleByStep(step));
        mProgressBar.setProgress(step);
        mTvProgressStep.setText(getString(R.string.create_product_progress_step, step));
    }

    private int getSubtitleByStep(int step) {
        switch (step) {
            case 1:
                return R.string.create_product_subtitle_step_1;
            case 2:
                return R.string.create_product_subtitle_step_2;
            case 3:
                return R.string.create_product_subtitle_step_3;
            case 4:
                return R.string.create_product_subtitle_step_4;
            default:
                throw new IllegalArgumentException("No Subtitle for step " + step);
        }
    }

    @Override
    public void onStepFinished() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final int TABS_COUNT = 4;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ProductNameFragment.newInstance();
                case 1:
                    return CreateProductTypeFragment.newInstance();
                case 2:
                    return ProductDefaultQuantityFragment.newInstance();
                case 3:
                    return InShopsAvailableFragment.newInstance();
                default:
                    throw new IllegalArgumentException("No Fragment for position " + position);
            }
        }

        @Override
        public int getCount() {
            return TABS_COUNT;
        }
    }
}
