package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.MyApplication;
import com.cometous.graduation.R;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.util.CacheUtil;
import com.cometous.graduation.util.Log4Utils;
import com.cometous.graduation.util.ShareUtil;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yalantis.phoenix.PullToRefreshView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by lenovo on 2015/4/11.
 */
public class DetailActivity extends BaseActivity {

    private SimpleDateFormat mFormatter = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat mSimpleFormatter = new SimpleDateFormat("hh");
    private SimpleDateFormat morningFormatter = new SimpleDateFormat("a");
    private SimpleDateFormat weekFormatter = new SimpleDateFormat("EE");
    private SimpleDateFormat monthFormatter = new SimpleDateFormat("MM月dd日");


    private ActionProcessButton joinButton;
    private ActionProcessButton quitButton;
    private ProgressGenerator progressGenerator;
    private ProgressGenerator quitProgressGenerator;

    private Exercise exercise;
    private String paramId;

    private Card card;
    private CardHeader header;

    private TextView startEndTxt;
    private TextView continuedTxt;
    private TextView weekTxt;
    private TextView dateTxt;

    private TextView detailLocationTxt;
    private TextView distanceTxt;
    private TextView detailLocationUpTxt;

    private TextView chargePersonTxt;
    private TextView personNumTxt;

    private ImageView detailImage;
    /** 报名返回状态 */
    private int status = 0;
    private int quitstatus = 0;
    private boolean isFork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.detials_layout);

        actionBar.setTitle("Activity");

        initSwipeBackLayout();
        initPullRefresh(R.id.base_pull_to_refresh);

        init();
        initView();

        Intent intent = getIntent();
        paramId = intent.getStringExtra("paramId");

        getFromMemory();


    }

    /**
     * 初始化按钮
     */
    private void init(){
        lodingView.setVisibility(View.VISIBLE);
        progressGenerator = new ProgressGenerator(new joinPrograss());
        quitProgressGenerator = new ProgressGenerator(new quitPrograss());
        joinButton = (ActionProcessButton) findViewById(R.id.join_btn);
        quitButton = (ActionProcessButton) findViewById(R.id.detail_quit_btn);

        joinButton.setMode(ActionProcessButton.Mode.ENDLESS);
        quitButton.setMode(ActionProcessButton.Mode.ENDLESS);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(joinButton);
                Task.joinActivity(exercise.getId(), new JoinActivity(), errorListener);
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitProgressGenerator.start(quitButton);
                Task.quitActivity(exercise.getId(), new QuitActivity(), errorListener);
            }
        });

        //下拉监听
        mPullToRefresh.setOnRefreshListener(new mDetailRefreshListener());
    }

    /**
     * 按钮回调
     */
    class joinPrograss implements ProgressGenerator.OnCompleteListener{
        @Override
        public void onComplete() {
            if (status == 0){
                String num = personNumTxt.getText().toString().trim();
                if (!num.isEmpty()){
                    int count = Integer.parseInt(num) + 1;
                    personNumTxt.setText(count+"");
                    joinButton.setEnabled(false);

                    CacheUtil.removeMemory(paramId);
                }
            }else{
                Toast.makeText(DetailActivity.this,"报名失败了,再试一试吧",Toast.LENGTH_SHORT).show();
                joinButton.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
                joinButton.setText("我要参加");

            }
        }
    }
    class quitPrograss implements ProgressGenerator.OnCompleteListener{
        @Override
        public void onComplete() {
            if (quitstatus == 0){
                String num = personNumTxt.getText().toString().trim();
                if (!num.isEmpty()){
                    int count = Integer.parseInt(num) - 1;
                    if (count == -1){
                        personNumTxt.setText("0");
                    }else{
                        personNumTxt.setText(count+"");
                    }
                    quitButton.setEnabled(false);

                    CacheUtil.removeMemory(paramId);
                }
            }else{
                Toast.makeText(DetailActivity.this,"退出失败，网络错误",Toast.LENGTH_SHORT).show();
                quitButton.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
                quitButton.setText("退出活动");

            }
        }
    }


    /**
     * initView
     */
    private void initView() {
        //时间标签
        startEndTxt = (TextView) findViewById(R.id.start_end_time_txt);
        continuedTxt = (TextView) findViewById(R.id.continued_txt);
        weekTxt = (TextView) findViewById(R.id.week_txt);
        dateTxt = (TextView) findViewById(R.id.date_txt);
        //距离标签
        detailLocationTxt = (TextView) findViewById(R.id.detail_location_txt);
        detailLocationUpTxt = (TextView) findViewById(R.id.detail_location_up_txt);
        distanceTxt = (TextView) findViewById(R.id.distance_txt);
        //负责人标签
        chargePersonTxt = (TextView) findViewById(R.id.charge_person_txt);
        personNumTxt = (TextView) findViewById(R.id.paerson_num_txt);
        //图片
        detailImage = (ImageView) findViewById(R.id.detail_image);

    }
    /**
     * 设置标签内容
     */
    private void setTxtView(){
        Date startDate = new Date(exercise.getStart_date());
        Date endDate = new Date(exercise.getEnd_date());
        String startTime = mFormatter.format(startDate);
        String endTime = mFormatter.format(endDate);
        String startEndTime = mSimpleFormatter.format(startDate.getHours()-endDate.getHours());
        startEndTxt.setText(startTime + "-" + endTime);
        continuedTxt.setText(startEndTime+"个小时");
        weekTxt.setText(weekFormatter.format(startDate) + " " + morningFormatter.format(startDate));
        dateTxt.setText(monthFormatter.format(endDate));

        //设置地点
        String location = exercise.getAddr_name();
        if (location.contains("-")){
            detailLocationTxt.setText(location.substring(location.indexOf("-")+1));
            detailLocationUpTxt.setText(location.substring(0,location.indexOf("-")));
        }else{
            detailLocationTxt.setText(location);
            detailLocationUpTxt.setText(location);
        }


        chargePersonTxt.setText(exercise.getCreator());
        personNumTxt.setText(exercise.getFork_count());

        if ( exercise.getImg_url() != null && !exercise.getImg_url().isEmpty()){
            ImageLoader.getInstance().displayImage(Task.HOST + exercise.getImg_url(), detailImage, MyApplication.options);
        }else{
            detailImage.setImageDrawable(getResources().getDrawable(R.drawable.no_picture_1));
        }

        init_card_inner_layout();

        lodingView.setVisibility(View.GONE);

    }


    /**
     * 下拉刷新监听
     */
    class mDetailRefreshListener implements PullToRefreshView.OnRefreshListener{

        @Override
        public void onRefresh() {
            mPullToRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullToRefresh.setRefreshing(false);
                    Task.getActivityDetail(paramId,new ActivityDetail(),errorListener);
                }
            },2000);
        }
    }

    private void getFromMemory(){
        String text =  CacheUtil.getMemory(paramId);
        if(text != null){
            JSONObject object = JSON.parseObject(text);
            exercise = JSON.parseObject(object.getString("message"),Exercise.class);

            isFork = object.getBoolean("fork");
            if (isFork){
                joinButton.setVisibility(View.GONE);
                quitButton.setVisibility(View.VISIBLE);
            }else{
                joinButton.setVisibility(View.VISIBLE);
                quitButton.setVisibility(View.GONE);
            }

            setTxtView();
        }else{
            Task.getActivityDetail(paramId, new ActivityDetail(), errorListener);
        }
    }

    /**
     * 获取详细信息网络回调
     */
    class ActivityDetail implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            CacheUtil.addMemory(paramId,response);
            JSONObject object = JSON.parseObject(response);

            exercise = JSON.parseObject(object.getString("message"),Exercise.class);

            isFork = object.getBoolean("fork");
            if (isFork){
                joinButton.setVisibility(View.GONE);
                quitButton.setVisibility(View.VISIBLE);
            }else{
                joinButton.setVisibility(View.VISIBLE);
                quitButton.setVisibility(View.GONE);
            }

            setTxtView();
        }
    }

    class JoinActivity implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);

            status = object.getInteger("status");

        }
    }

    class QuitActivity implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);

            quitstatus = object.getInteger("status");

        }
    }


    /**
     *设置卡片标签
     */
    private void init_card_inner_layout() {
        //Create a Card
        card = new Card(this,R.layout.detail_custom_card);
        //Create a CardHeader
        header = new CardHeader(this);
        //Set the header title
        header.setTitle(exercise.getName());
        card.addCardHeader(header);
        //Set the card inner text
        card.setTitle(exercise.getDesc());
        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) this.findViewById(R.id.detail_title);
        cardView.setCard(card);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     if (item.getItemId() == android.R.id.home ){
            finish();
     }else if(item.getItemId() == R.id.detail_share){
         ShareUtil.shareToIntent(this,"web");
     }
        return true;
    }
}
