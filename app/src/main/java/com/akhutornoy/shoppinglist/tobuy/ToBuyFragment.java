package com.akhutornoy.shoppinglist.tobuy;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;

public class ToBuyFragment extends Fragment {

    private static final String ARG_SHOP_ID = "ARG_SHOP_ID";

    public static ToBuyFragment newInstance(String shopId) {
        ToBuyFragment fragment = new ToBuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_buy, container, false);

        if (getArguments() != null) {
            String shopId = getArguments().getString(ARG_SHOP_ID);
            Snackbar.make(view, "Chosen shop: " + shopId, Snackbar.LENGTH_SHORT).show();
        } else {
            throw new IllegalArgumentException("Argument should be passed");
        }

        initFab(view);
        return view;
    }

    private void initFab(View rootView) {
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}
