package com.mobile.wishtrack.ui.customUi;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mobile.wishtrack.R;

public class SearchProduct extends LinearLayout {

    public SearchProduct(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.search_product, this, true);
    }
}