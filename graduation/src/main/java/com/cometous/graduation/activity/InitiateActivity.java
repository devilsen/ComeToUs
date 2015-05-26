package com.cometous.graduation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.MapPoi;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.PickTypeDialogListener;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.util.Log4Utils;
import com.cometous.graduation.util.PreferenceUtil;
import com.cometous.graduation.util.ShareUtil;
import com.cometous.graduation.util.UploadFile;
import com.cometous.graduation.view.PickTypeDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Handler;

import de.greenrobot.event.EventBus;

/**
 * Created by Devilsen on 2015/4/20.
 */
public class InitiateActivity extends BaseActivity {

    private static final int ADD_IMAGE = 1;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("：MM月dd日 aa hh:mm");
    private Date startDate = null;
    private Date endDate = null;

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
    /** 活动类型 */
    private TextView type1Txt;
    private TextView type2Txt;
    private TextView type3Txt;
    private TextView type4Txt;
    /** 地址信息 */
    private double longitude;
    private double latitude;
    /** 图片路径 */
    private String picturePath = null;
    private PictureHandler pictureHandler;

    String titleString = null;
    String introduceString = null;
    String locationString = null;
    String startString = null;
    String endString = null;
    String myTypeString = null;
    String imgUrl = null;

    private PickTypeDialog typeDialog;
    private LinearLayout typeLayout;

    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.initiate_activity_layout);

        actionBar.setTitle("Initiate");

        mOnclickListener = new mClickListener();

        EventBus.getDefault().register(this);

        init();
        getInfo();
    }

    private void init() {
        titleEdit = (EditText) findViewById(R.id.activity_title_edit);
        introduceEdit = (EditText) findViewById(R.id.activity_introduce);
        locationTxt = (TextView) findViewById(R.id.activity_location_txt);

        typeLayout = (LinearLayout) findViewById(R.id.pick_type_layout);
        type1Txt = (TextView) findViewById(R.id.type_1_txt);
        type2Txt = (TextView) findViewById(R.id.type_2_txt);
        type3Txt = (TextView) findViewById(R.id.type_3_txt);
        type4Txt = (TextView) findViewById(R.id.type_4_txt);

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
        typeLayout.setOnClickListener(mOnclickListener);

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
                    break;
                case R.id.pick_type_layout:
                    pickType();
                    break;
            }
        }
    }

    /**
     * 选取活动类型
     */
    private void pickType() {
        PickTypeDialogListener dialogListener = new PickTypeDialogListener() {
            @Override
            public void getTypeString(String typeString) {
                if (typeString != null || !typeString.isEmpty()){
                    myTypeString = typeString;
                    setTypeByArray(typeString);
                }
            }
        };

        typeDialog = new PickTypeDialog(this,R.style.PickTypeDialog);
        typeDialog.setDialogListener(dialogListener);
        typeDialog.show();
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
            picturePath = data.getStringExtra("imgPath");
            images.setImageURI(Uri.fromFile(new File(picturePath)));
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
            startTimeTxt.setText(mFormatter.format(startDate));
        }
    };
    private SlideDateTimeListener endTimelistener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            endDate = date;
            endTimedTxt.setText(mFormatter.format(endDate));
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
                if (picturePath == null || picturePath.isEmpty()){
                    Task.initiateActivityNet(getParams(), new InitiateActivityNet(), errorListener);
                }else{
                    pictureHandler = new PictureHandler();
                    PictureThread pictureThread = new PictureThread();
                    new Thread(pictureThread).start();
                }

            }
        }else if (item.getItemId() == android.R.id.home ){
            finish();
        }
        return true;
    }

    private boolean checkEmpty(){
        boolean flag = true;
        titleString = titleEdit.getText().toString().trim();
        introduceString = introduceEdit.getText().toString().trim();
        locationString = locationTxt.getText().toString().trim();
        startString = startTimeTxt.getText().toString().trim();
        endString = endTimedTxt.getText().toString().trim();

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


    /**
     * 参加活动的网络回调
     */
    class InitiateActivityNet implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);

            status = object.getInteger("status");
            if (status == 0){
                showToast("发起成功");
                dialog.dismiss();
                finish();
            }else{
                showToast("发起失败");
                dialog.dismiss();
            }
        }
    }


    private HashMap<String,String> getParams(){
        HashMap<String,String> param = new HashMap<String,String>();
        param.put("name",titleString);
//        param.put("create_date",startDate.getTime() +"");
        param.put("start_date",startDate.getTime() +"");
        param.put("end_date",endDate.getTime() + "");
        param.put("edit_date",endDate.getTime() + "");
        param.put("desc",introduceString);
        param.put("addr_name",locationString);
        param.put("addr_position_x",longitude + "");
        param.put("addr_position_y",latitude + "");
        param.put("img_url", imgUrl);

        Log4Utils.i("start_date", startDate.getTime() + "");
        Log4Utils.i("end_date", endDate.getTime() + "");
        Log4Utils.i("end_date", longitude + "");
        Log4Utils.i("end_date", latitude + "");
//        param.put("creator","ObjectId");

        return param;
    }

    /** eventbus 主线程 */
    public void onEventMainThread(MapPoi poi) {
        locationString = poi.getName().replace("\\", "");
        latitude = poi.getPosition().latitude;
        longitude = poi.getPosition().longitude;
        locationTxt.setText(locationString);
    }

    /**
     * 通过array设置类别
     * @param typeString
     */
    private void setTypeByArray(String typeString){
        String [] typeArray = typeString.split("@");

        int length = typeArray.length;
        if(length == 1){
            type1Txt.setText(typeArray[0]);
            type2Txt.setVisibility(View.INVISIBLE);
            type3Txt.setVisibility(View.INVISIBLE);
            type4Txt.setVisibility(View.INVISIBLE);
        }
        if(length == 2){
            type1Txt.setText(typeArray[0]);
            type2Txt.setVisibility(View.VISIBLE);
            type2Txt.setText(typeArray[1]);
            type3Txt.setVisibility(View.INVISIBLE);
            type4Txt.setVisibility(View.INVISIBLE);
        }
        if(length == 3){
            type1Txt.setText(typeArray[0]);
            type2Txt.setVisibility(View.VISIBLE);
            type2Txt.setText(typeArray[1]);
            type3Txt.setVisibility(View.VISIBLE);
            type3Txt.setText(typeArray[2]);
            type4Txt.setVisibility(View.INVISIBLE);
        }
        if(length == 4){
            type1Txt.setText(typeArray[0]);
            type2Txt.setVisibility(View.VISIBLE);
            type2Txt.setText(typeArray[1]);
            type3Txt.setVisibility(View.VISIBLE);
            type3Txt.setText(typeArray[2]);
            type4Txt.setVisibility(View.VISIBLE);
            type4Txt.setText(typeArray[3]);
        }
    }

    /** 保存尚未发布的数据 */
    private void saveInfo(){
        titleString = titleEdit.getText().toString().trim();
        introduceString = introduceEdit.getText().toString().trim();
        locationString = locationTxt.getText().toString().trim();
        startString = startTimeTxt.getText().toString().trim();
        endString = endTimedTxt.getText().toString().trim();
        if (!titleString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.title", titleString);
        }
        if (!introduceString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.introduce", introduceString);
        }
        if (!locationString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.location", locationString);
            PreferenceUtil.saveInitiatePreference("initieate.latitude", latitude+"");
            PreferenceUtil.saveInitiatePreference("initieate.longitude", longitude + "");
        }
        if (!startString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.starttime", startString);
        }
        if (!endString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.endtime", endString);
        }
        if (myTypeString != null && !myTypeString.isEmpty()){
            PreferenceUtil.saveInitiatePreference("initieate.type", myTypeString);
        }

    }

    /** 得到尚未发布的数据 */
    private void getInfo() {
        titleString = PreferenceUtil.getInitiatePreferencesByKey("initieate.title");
        introduceString = PreferenceUtil.getInitiatePreferencesByKey("initieate.introduce");
        locationString = PreferenceUtil.getInitiatePreferencesByKey("initieate.location");
        startString = PreferenceUtil.getInitiatePreferencesByKey("initieate.starttime");
        endString = PreferenceUtil.getInitiatePreferencesByKey("initieate.endtime");
        myTypeString = PreferenceUtil.getInitiatePreferencesByKey("initieate.type");

        String latitudeString = PreferenceUtil.getInitiatePreferencesByKey("initieate.latitude");
        String longitudeString = PreferenceUtil.getInitiatePreferencesByKey("initieate.longitude");
        if (latitudeString != null && !latitudeString.isEmpty()){
            latitude = Double.parseDouble(latitudeString);
        }
        if (longitudeString != null && !longitudeString.isEmpty()){
            longitude = Double.parseDouble(longitudeString);
        }

        if ( titleString != null){
            titleEdit.setText(titleString);
        }
        if ( introduceString != null){
            introduceEdit.setText(introduceString);
        }
        if ( locationString != null){
            locationTxt.setText(locationString);
        }
        if ( startString != null){
            startTimeTxt.setText(startString);
        }
        if ( endString != null){
            endTimedTxt.setText(endString);
        }
        if ( myTypeString != null){
            setTypeByArray(myTypeString);
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        saveInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




    class PictureThread implements Runnable{
        String pictureUrl;
        @Override
        public void run() {
            pictureUrl = UploadFile.uploadFile(picturePath);
            Log4Utils.i("imageFile",pictureUrl);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result", pictureUrl);
            msg.setData(bundle);
            pictureHandler.sendMessage(msg);
        }
    }

    class PictureHandler extends android.os.Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String result = bundle.getString("result");

            if(result != null){
                imgUrl = result;
                Task.initiateActivityNet(getParams(), new InitiateActivityNet(), errorListener);
                dialog.dismiss();
            }else{
                showToast("图片上传失败");
                dialog.dismiss();
            }

        }
    }

}
