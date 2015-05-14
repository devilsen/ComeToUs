package com.cometous.graduation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cometous.graduation.R;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Devilsen on 2015/4/27.
 */
public class UserInfoActivity extends BaseActivity implements ProgressGenerator.OnCompleteListener{

    private static final int ADD_IMAGE = 1;

    private CircleImageView headImg;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton queitBut;

    private MyOnclickListener mOnclickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.user_info_layout);

        init();


    }

    private void init() {
        mOnclickListener = new MyOnclickListener();
        headImg = (CircleImageView) findViewById(R.id.user_info_head_img);

        headImg.setOnClickListener(mOnclickListener);
        //退出按钮
        progressGenerator = new ProgressGenerator(this);
        queitBut = (ActionProcessButton) findViewById(R.id.queit_but);
        queitBut.setMode(ActionProcessButton.Mode.ENDLESS);

        queitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(queitBut);
                Intent queitIntent = new Intent(UserInfoActivity.this,SignInActivity.class);
                startActivity(queitIntent);
                finish();
            }
        });
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
            }

        }
    }
}
