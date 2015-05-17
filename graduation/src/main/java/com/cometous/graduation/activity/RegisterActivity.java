package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cometous.graduation.R;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

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
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onComplete() {
        
    }
}
