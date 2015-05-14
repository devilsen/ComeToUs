package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.util.CacheUtil;
import com.cometous.graduation.util.Log4Utils;
import com.cometous.graduation.util.ShareUtil;
import com.cometous.graduation.view.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yalantis.phoenix.PullToRefreshView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by lenovo on 2015/4/11.
 */
public class DetailActivity extends BaseActivity implements ProgressGenerator.OnCompleteListener{

    private SimpleDateFormat mFormatter = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat weekFormatter = new SimpleDateFormat("EE");
    private SimpleDateFormat monthFormatter = new SimpleDateFormat("MM月dd日");


    private ActionProcessButton joinButton;
    private ProgressGenerator progressGenerator;

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
    /** 报名返回状态 */
    private int status = 0;


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

    private void init(){
        lodingView.setVisibility(View.VISIBLE);
        progressGenerator = new ProgressGenerator(this);
        joinButton = (ActionProcessButton) findViewById(R.id.join_btn);

        joinButton.setMode(ActionProcessButton.Mode.ENDLESS);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(joinButton);
                Task.joinActivity(exercise.getId(),new JoinActivity(),errorListener);
            }
        });

        //下拉监听
        mPullToRefresh.setOnRefreshListener(new mDetailRefreshListener());
    }

    /**
     * 按钮回调
     */
    @Override
    public void onComplete() {
        if (status == 0){
            String num = personNumTxt.getText().toString().trim();
            if (!num.isEmpty()){
                int count = Integer.parseInt(num) + 1;
                personNumTxt.setText(count+"");
                joinButton.setEnabled(false);
            }
        }else{
            Toast.makeText(DetailActivity.this,"报名失败了,再试一试吧",Toast.LENGTH_SHORT).show();
            joinButton.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
            joinButton.setText("我要参加");

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

    }
    /**
     * 设置标签内容
     */
    private void setTxtView(){
        String startTime = mFormatter.format(exercise.getStart_date());
        String endTime = mFormatter.format(exercise.getEnd_date());
        String startEndTime = mFormatter.format(exercise.getEnd_date().getHours()-exercise.getStart_date().getHours());
        startEndTxt.setText(startTime + "~" + endTime);
        continuedTxt.setText(startEndTime+"个小时");
        weekTxt.setText(weekFormatter.format(exercise.getStart_date()));
        dateTxt.setText(monthFormatter.format(exercise.getStart_date()));

        detailLocationTxt.setText(exercise.getAddr_name());

        chargePersonTxt.setText(exercise.getName());

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
