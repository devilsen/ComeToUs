package com.cometous.graduation.activity;

import android.os.Bundle;

import com.cometous.graduation.R;

/**
 * Created by Devilsen on 2015/4/27.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.about_layout);

        actionBar.hide();

    }
}
