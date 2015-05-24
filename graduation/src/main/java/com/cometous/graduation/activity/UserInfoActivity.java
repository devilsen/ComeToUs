package com.cometous.graduation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.ParseError;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.model.User;
import com.cometous.graduation.util.CacheUtil;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Devilsen on 2015/4/27.
 */
public class UserInfoActivity extends BaseActivity implements ProgressGenerator.OnCompleteListener{

    private static final int ADD_IMAGE = 1;

    private CircleImageView headImg;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton queitBut;

    private LinearLayout joinTimeLayout;
    private LinearLayout initiateLayout;

    private MyOnclickListener mOnclickListener;

    private User userinfo;

    private TextView joinCount;
    private TextView initCount;
    private TextView usernameTxt;
    private TextView schoolTxt;
    private TextView jobTxt;
    private TextView phoneTxt;
    private TextView emailTxt;

    private String joinString;
    private String initString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.user_info_layout);

        actionBar.setTitle("Me");

        init();


    }

    private void init() {
        mOnclickListener = new MyOnclickListener();
        headImg = (CircleImageView) findViewById(R.id.user_info_head_img);
        joinTimeLayout = (LinearLayout) findViewById(R.id.user_info_jion_time_layout);
        initiateLayout = (LinearLayout) findViewById(R.id.user_info_initiate_time_layout);
        joinCount = (TextView) findViewById(R.id.user_info_jion_time_txt);
        initCount = (TextView) findViewById(R.id.user_info_initiate_time_txt);
        usernameTxt = (TextView) findViewById(R.id.username_txt);
        schoolTxt = (TextView) findViewById(R.id.user_shool_txt);
        jobTxt = (TextView) findViewById(R.id.user_job_txt);
        phoneTxt = (TextView) findViewById(R.id.user_phone_txt);
        emailTxt = (TextView) findViewById(R.id.user_email_txt);

        headImg.setOnClickListener(mOnclickListener);
        joinTimeLayout.setOnClickListener(mOnclickListener);
        initiateLayout.setOnClickListener(mOnclickListener);
        phoneTxt.setOnClickListener(mOnclickListener);
        emailTxt.setOnClickListener(mOnclickListener);

        //退出按钮
        progressGenerator = new ProgressGenerator(this);
        queitBut = (ActionProcessButton) findViewById(R.id.queit_but);
        queitBut.setMode(ActionProcessButton.Mode.ENDLESS);

        queitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(queitBut);
                Intent queitIntent = new Intent(UserInfoActivity.this, SignInActivity.class);
                startActivity(queitIntent);
                finish();
            }
        });

        Task.getUserInfo("555857bfad99d7b5379430d5",new UserInfoListener(),errorListener);

    }

    /**
     * 头像选取
     */
    private void pictrueChooser() {
        startActivityForResult(
                new Intent(UserInfoActivity.this, de.j4velin.picturechooser.Main.class)
                        .putExtra("crop", true)
                        .putExtra("aspectX", 10)
                        .putExtra("aspectY", 9),
                ADD_IMAGE);
    }
    protected void onActivityResult(int request, int result, final Intent data) {
        if (result == RESULT_OK && request == ADD_IMAGE) {
            String path = data.getStringExtra("imgPath");
            headImg.setImageURI(Uri.fromFile(new File(path)));
        }
    }

    @Override
    public void onComplete() {

    }


    class MyOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.user_info_head_img:
                    pictrueChooser();
                    break;
                case R.id.user_info_jion_time_layout:
                    Intent joinIntent = new Intent(UserInfoActivity.this,JoinOrInitActivity.class);
                    joinIntent.putExtra("joinorinit","join");
                    startActivity(joinIntent);
                    break;
                case R.id.user_info_initiate_time_layout:
                    Intent initIntent = new Intent(UserInfoActivity.this,JoinOrInitActivity.class);
                    initIntent.putExtra("joinorinit","init");
                    startActivity(initIntent);
                    break;
                case R.id.user_phone_txt:
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneTxt.getText().toString().trim()));
                    UserInfoActivity.this.startActivity(intent);
                    break;
                case R.id.user_email_txt:
                    Intent data=new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse("mailto:" + emailTxt.getText().toString().trim()));
                    startActivity(data);
                    break;
            }

        }
    }

    private class UserInfoListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try{
                JSONObject object = JSON.parseObject(response);
                userinfo = JSON.parseObject(object.getString("message"), User.class);
                joinString = object.getString("countOfJoin");
                initString = object.getString("countOfMy");

                if (userinfo != null){
                    setText();
                }else {
                    Toast.makeText(UserInfoActivity.this,"获取个人信息失败",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                callback.onException(new ParseError());
            }

        }
    }

    private void setText(){

        ImageLoader.getInstance().displayImage("http://gexing.edujq.com/img/2013/04/19/04191009418291.jpg", headImg);

        if (joinString == null || joinString.isEmpty()){
            joinCount.setText("0");
        }else{
            joinCount.setText(joinString);
        }
        if (initString == null || initString.isEmpty()){
            initCount.setText("0");
        }else{
            joinCount.setText(initString);
        }
        usernameTxt.setText(userinfo.getLoginname());
        schoolTxt.setText(userinfo.getSchool());
        jobTxt.setText(userinfo.getTitle());
        phoneTxt.setText(userinfo.getPhone());
        emailTxt.setText(userinfo.getEmail());
    }


}
