package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;

/**
 * Created by Devilsen on 2015/5/16.
 */
public class RegisterActivity extends BaseActivity implements ProgressGenerator.OnCompleteListener{

    private ProgressGenerator progressGenerator;
    private ActionProcessButton registerBtn;

    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText schoolEdit;
    private EditText jobEdit;
    private EditText phoneEdit;
    private EditText emailEdit;

    private String usernameString;
    private String passwordString;
    private String schoolString;
    private String jobString;
    private String phoneString;
    private String emailString;

    int status = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.register_layout);

        usernameEdit = (EditText) findViewById(R.id.register_username_edit);
        passwordEdit = (EditText) findViewById(R.id.register_password_edit);
        schoolEdit = (EditText) findViewById(R.id.register_school_edit);
        jobEdit = (EditText) findViewById(R.id.register_job_edit);
        phoneEdit = (EditText) findViewById(R.id.register_phone_edit);
        emailEdit = (EditText) findViewById(R.id.register_email_edit);

        progressGenerator = new ProgressGenerator(this);
        registerBtn = (ActionProcessButton) findViewById(R.id.register_btn);
        registerBtn.setMode(ActionProcessButton.Mode.ENDLESS);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check( );
            }
        });
    }

    private void check( ){

        usernameString = usernameEdit.getText().toString().trim();
        passwordString = passwordEdit.getText().toString().trim();
        schoolString = schoolEdit.getText().toString().trim();
        jobString = jobEdit.getText().toString().trim();
        phoneString = phoneEdit.getText().toString().trim();
        emailString = emailEdit.getText().toString().trim();

        if (usernameString.isEmpty()){
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        }else if (passwordString.isEmpty()){
            Toast.makeText(this,"密码为空",Toast.LENGTH_SHORT).show();
        }else if (schoolString.isEmpty()){
            Toast.makeText(this,"学校为空",Toast.LENGTH_SHORT).show();
        }else if (jobString.isEmpty()){
            Toast.makeText(this,"职务为空",Toast.LENGTH_SHORT).show();
        }else if (phoneString.isEmpty()){
            Toast.makeText(this,"电话为空",Toast.LENGTH_SHORT).show();
        }else if (emailString.isEmpty()){
            Toast.makeText(this,"邮箱为空",Toast.LENGTH_SHORT).show();
        }else{
            progressGenerator.start(registerBtn);
            Task.register(getParams(),new RegisterListener(),errorListener);
        }
    }


    private HashMap<String,String> getParams(){
        HashMap<String,String> param = new HashMap<String,String>();
        param.put("loginname",usernameString);
        param.put("passwd",passwordString);
        param.put("email",emailString);
        param.put("phone",phoneString);
        param.put("title",jobString);
        param.put("school",schoolString);

        return param;
    }

    /**
     * 注册接口
     */
    class RegisterListener implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);
            status = object.getInteger("status");

        }
    }


    @Override
    public void onComplete() {
        if (status == 1){
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if(status == 0){
            Toast.makeText(RegisterActivity.this,"用户已存在",Toast.LENGTH_SHORT).show();
            registerError();
        }else if (status == 2 ){
            Toast.makeText(RegisterActivity.this,"用户名或者密码格式有问题",Toast.LENGTH_SHORT).show();
            registerError();
        }else{
            Toast.makeText(RegisterActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
            registerError();
        }
    }

    private void registerError(){
        registerBtn.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        registerBtn.setText(R.string.register);
    }
}
