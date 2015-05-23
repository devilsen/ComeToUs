package com.cometous.graduation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.JoinOrInitAdapter;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devilsen on 2015/5/17.
 */
public class JoinOrInitActivity extends BaseActivity {

    private ListView listView;
    private JoinOrInitAdapter adapter;
    private LayoutInflater inflater;
    private String JoinOrInit;

    private List<Exercise> exerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.list_join_or_init);

        lodingView.setVisibility(View.VISIBLE);
        inflater = LayoutInflater.from(this);

        listView = (ListView) findViewById(R.id.join_or_init_listview);
        adapter = new JoinOrInitAdapter(exerciseList,this);
        listView.setAdapter(adapter);

        Intent StringIntent = getIntent();
        JoinOrInit = StringIntent.getStringExtra("joinorinit");
        if (JoinOrInit.equals("join")){
            Task.listAllMyjoin(new JoinOrInitListener(),errorListener);
            actionBar.setTitle("MyJoin");
        }else{
            Task.listAllMyInit(new JoinOrInitListener(),errorListener);
            actionBar.setTitle("MyInitiate");
        }

    }

    class JoinOrInitListener implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);
            List<Exercise> list = JSON.parseArray(object.getString("actions"),Exercise.class);

            if (list == null || list.size() == 0){
                LinearLayout nodataLayout = (LinearLayout) inflater.inflate(R.layout.list_no_data, null);
                TextView textView = (TextView) nodataLayout.findViewById(R.id.no_data_txt);
                if (JoinOrInit.equals("join")){
                    textView.setText("您还没有参加活动");
                }else{
                    textView.setText("您还没有发起过活动");
                }
                listView.addHeaderView(nodataLayout);
            }


            exerciseList.addAll(list);
            adapter.notifyDataSetChanged();
            lodingView.setVisibility(View.INVISIBLE);

        }
    }




}
