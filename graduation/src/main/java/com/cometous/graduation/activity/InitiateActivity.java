package com.cometous.graduation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cometous.graduation.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Devilsen on 2015/4/20.
 */
public class InitiateActivity extends BaseActivity {

    private static final int ADD_IMAGE = 1;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("：MM月dd日 aa hh:mm");
    private Date startDate = null;

    private mClickListener mOnclickListener;
    private LinearLayout mLocationLayout;
    private LinearLayout mTimeLayout;
    private LinearLayout mEndTimeLayout;
    /** 开始时间 */
    private TextView startTimeTxt;
    /** 结束时间 */
    private TextView endTimedTxt;
    /** 展示图片 */
    private ImageView images;
    /** 活动标题 */
    private EditText titleEdit;
    /** 活动介绍 */
    private EditText introduceEdit;
    /** 活动地点 */
    private TextView locationTxt;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.initiate_activity_layout);

        mOnclickListener = new mClickListener();
        init();
    }

    private void init() {
        titleEdit = (EditText) findViewById(R.id.activity_title_edit);
        introduceEdit = (EditText) findViewById(R.id.activity_introduce);
        locationTxt = (TextView) findViewById(R.id.activity_location_txt);

        mLocationLayout = (LinearLayout) findViewById(R.id.activity_location_layout);
        mTimeLayout = (LinearLayout) findViewById(R.id.initiate_time_layout);
        startTimeTxt = (TextView) findViewById(R.id.start_time_txt);
        mEndTimeLayout = (LinearLayout) findViewById(R.id.initiate_end_time_layout);
        endTimedTxt = (TextView) findViewById(R.id.end_time_txt);
        images = (ImageView) findViewById(R.id.activity_picture_choose);


        mLocationLayout.setOnClickListener(mOnclickListener);
        mTimeLayout.setOnClickListener(mOnclickListener);
        mEndTimeLayout.setOnClickListener(mOnclickListener);
        images.setOnClickListener(mOnclickListener);



    }

    /**
     * 点击监听
     */
    class mClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch ( v.getId() ){
                case R.id.activity_location_layout:
                    Intent location = new Intent(InitiateActivity.this,SetLocationActivity.class);
                    startActivity(location);
                    break;
                case R.id.initiate_time_layout:
                    pickStartTime();
                    break;
                case R.id.initiate_end_time_layout:
                    pickEndTime();
                    break;
                case R.id.activity_picture_choose:
                    pictrueChooser();
            }
        }
    }

    /**
     * 图片选取
     */
    private void pictrueChooser() {
        startActivityForResult(
                new Intent(InitiateActivity.this, de.j4velin.picturechooser.Main.class)
                        .putExtra("crop", true),
                ADD_IMAGE);
    }
    protected void onActivityResult(int request, int result, final Intent data) {
        if (result == RESULT_OK && request == ADD_IMAGE) {
            String path = data.getStringExtra("imgPath");
            images.setImageURI(Uri.fromFile(new File(path)));
        }
    }

    /**
     * 选取时间
     */
    private void pickStartTime(){
        if (startDate == null){
            startDate = new Date();
        }
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(startTimeListener)
                .setInitialDate(startDate)
                .build()
                .show();
    }
    private void pickEndTime(){
        if (startDate == null){
            startDate = new Date();
        }
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(endTimelistener)
                .setInitialDate(startDate)
                .build()
                .show();
    }
    /**
     * 时间选取监听
     */
    private SlideDateTimeListener startTimeListener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            startDate = date;
            startTimeTxt.setText(mFormatter.format(date));
        }
    };
    private SlideDateTimeListener endTimelistener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            endTimedTxt.setText(mFormatter.format(date));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.initiate_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_send){
            if ( checkEmpty() ){
                showDialog("正在发起...");
            }
        }else if (item.getItemId() == android.R.id.home ){
            finish();
        }
        return true;
    }

    private boolean checkEmpty(){
        boolean flag = true;
        String titleString = titleEdit.getText().toString().trim();
        String introduceString = introduceEdit.getText().toString().trim();
        String locationString = locationTxt.getText().toString().trim();
        String startString = startTimeTxt.getText().toString().trim();
        String endString = endTimedTxt.getText().toString().trim();

        if ( titleString.isEmpty() ){
            showToast("标题不能为空哦");
            flag = false;
        }else if (introduceString.isEmpty()){
            showToast("介绍不能为空哦");
            flag = false;
        }else if (locationString.isEmpty()){
            showToast("要在哪里举办你活动呢");
            flag = false;
        }else if (startString.isEmpty()){
            showToast("什么时候开始呢");
            flag = false;
        }else if (endString.isEmpty()){
            showToast("啥时候结束啊");
            flag = false;
        }

        return flag;
    }

    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
