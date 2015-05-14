package com.cometous.graduation.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.cometous.graduation.R;
import com.cometous.graduation.adapter.NoticeListAdapter;
import com.cometous.graduation.model.Notice;

import java.util.ArrayList;
import java.util.List;

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


        noticeListvew = (ListView) findViewById(R.id.notici_listview);
        noticeListAdapter = new NoticeListAdapter(this,noticeList);
        noticeListvew.setAdapter(noticeListAdapter);


    }
}
