package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.cometous.graduation.util.Log4Utils;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.detials_layout);


        initSwipeBackLayout();
        initPullRefresh(R.id.base_pull_to_refresh);

        init();
        initView();

        Intent intent = getIntent();
        paramId = intent.getStringExtra("paramId");

        Task.getActivityDetail(paramId, new ActivityDetail(), errorListener);

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

            }
        });

        //下拉监听
        mPullToRefresh.setOnRefreshListener(new mDetailRefreshListener());
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

    /**
     * 网络回调
     */
    class ActivityDetail implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);

            exercise = JSON.parseObject(object.getString("message"),Exercise.class);

            setTxtView();
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
    public void onComplete() {
//        Toast.makeText(DetailActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     if (item.getItemId() == android.R.id.home ){
            finish();
        }
        return true;
    }
}
