package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.NoticeListAdapter;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.ParseError;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Notice;
import com.cometous.graduation.model.eventbus.NoticeEvent;
import com.cometous.graduation.util.CacheUtil;
import com.cometous.graduation.util.PreferenceUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Devilsen on 2015/5/13.
 */
public class NoticeActivity extends BaseActivity {

    private ListView noticeListvew;
    List<Notice> noticeList = new ArrayList<>();
    private NoticeListAdapter noticeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.notifycation_layout);

        actionBar.setTitle("Notice");

        initPullRefresh(R.id.notice_pull_to_refresh);


        noticeListvew = (ListView) findViewById(R.id.notici_listview);
        noticeListAdapter = new NoticeListAdapter(this,noticeList);
        noticeListvew.setAdapter(noticeListAdapter);


        //下拉监听
        mPullToRefresh.setOnRefreshListener(new mNoticeRefreshListener());


        //将查看通知的提醒改变，去掉通知小红点
        PreferenceUtil.saveNoticeStatus(true);
        EventBus.getDefault().post(new NoticeEvent());

        Intent intent = getIntent();
        boolean isFromChace = intent.getBooleanExtra("fromCahe",false);
        if (isFromChace){
            getFromChace();
        }else{
            getFromChace();
            mPullToRefresh.setRefreshing(true, true);
        }

    }

    /**
     * 下拉刷新监听
     */
    class mNoticeRefreshListener implements PullToRefreshView.OnRefreshListener{

        @Override
        public void onRefresh() {
            mPullToRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullToRefresh.setRefreshing(false);
                    Task.getNoticeList(new NoticeListener(), errorListener);
                }
            },2000);
        }
    }

    private class NoticeListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {
                PreferenceUtil.saveNoticeList(response);
                jsonToList(response);
            } catch (Exception e) {
                callback.onException(new ParseError());
            }

        }
    }

    /**
     * 缓存
     */
    private void getFromChace(){
        String response =  PreferenceUtil.getNoticeList();
        if (response == null){
            mPullToRefresh.setRefreshing(true,true);
        }else{
            jsonToList(response);
        }
    }

    private void jsonToList(String response){
        JSONObject object = JSON.parseObject(response);
        List<Notice> list = JSON.parseArray(object.getString("message"),Notice.class);

        noticeList.clear();
        noticeList.addAll(list);
        noticeListAdapter.notifyDataSetChanged();
        mPullToRefresh.setRefreshing(false);
    }




}
