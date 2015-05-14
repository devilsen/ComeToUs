package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.cometous.graduation.R;

/**
 * Created by Devilsen on 2015/5/14.
 */
public class SettingActivity extends BaseActivity {

    private Switch openMyPhone;
    private Switch openMyEmail;
    private TextView share;

    private MyOnClickListener myOnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.setting_layout);

        actionBar.setTitle("Setting");


        init();



    }

    private void init() {
        openMyPhone = (Switch) findViewById(R.id.my_phone_switch);
        openMyEmail = (Switch) findViewById(R.id.my_email_switch);
        share = (TextView) findViewById(R.id.setting_share_txt);

        myOnClickListener = new MyOnClickListener();
        share.setOnClickListener(myOnClickListener);


    }


    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_share_txt:
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
                            "baidu Page :  https://www.baidu.com\n" +
                            "Sample App : https://play.google.com/store/apps/details?id=" +
                            getPackageName());
                    startActivity(Intent.createChooser(share, getString(R.string.app_name)));
                    break;
            }
        }
    }




}
