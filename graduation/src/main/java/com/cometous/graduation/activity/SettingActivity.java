package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.util.PreferenceUtil;
import com.cometous.graduation.util.ShareUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Devilsen on 2015/5/14.
 */
public class SettingActivity extends BaseActivity {

    private Switch phoneSwitch;
    private Switch emailSwitch;
    private TextView share;

    private boolean flag;

    private MyOnClickListener myOnClickListener;
    private ChangeListener mChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.setting_layout);

        actionBar.setTitle("Setting");


        init();

    }

    private void init() {
        phoneSwitch = (Switch) findViewById(R.id.my_phone_switch);
        emailSwitch = (Switch) findViewById(R.id.my_email_switch);
        share = (TextView) findViewById(R.id.setting_share_txt);

        myOnClickListener = new MyOnClickListener();
        share.setOnClickListener(myOnClickListener);

        mChangeListener = new ChangeListener();
        phoneSwitch.setOnCheckedChangeListener(mChangeListener);
        emailSwitch.setOnCheckedChangeListener(mChangeListener);

        initSetting();
    }

    private void initSetting(){
        boolean phoneCheck = PreferenceUtil.getSettingPreferences("phone_enable");
        boolean emailCheck = PreferenceUtil.getSettingPreferences("email_enable");

        phoneSwitch.setChecked(phoneCheck);
        emailSwitch.setChecked(emailCheck);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (flag){
            Task.settingUpdate(getParams(), new updateSettingListener(), errorListener);

        }


    }

    private HashMap<String,String> getParams(){
        HashMap<String,String> param = new HashMap<String,String>();
        boolean phoneCheck = phoneSwitch.isChecked();
        boolean emailCheck = emailSwitch.isChecked();
        //保存到本地
        PreferenceUtil.saveSettingPreference("email_enable",emailCheck);
        PreferenceUtil.saveSettingPreference("phone_enable",phoneCheck);

        if (phoneCheck){
            param.put("phone_enable", "1");
        }else{
            param.put("phone_enable", "0");
        }

        if (emailCheck){
            param.put("email_enable", "1");
        }else{
            param.put("email_enable", "0");
        }

        return param;
    }


    class updateSettingListener implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);
            int status = object.getInteger("status");

            if (status ==0){

            }
        }
    }

    class ChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            flag = true;
        }
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_share_txt:
                    ShareUtil.shareToIntent(SettingActivity.this,"web");
                    break;
            }
        }
    }




}
