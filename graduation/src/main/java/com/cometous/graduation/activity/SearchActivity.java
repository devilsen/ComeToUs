package com.cometous.graduation.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.SearchAdapter;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Devilsen on 2015/5/13.
 */
public class SearchActivity extends BaseActivity {

    private List<Exercise> searchList;
    private SearchAdapter searchAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.search_layout);

        actionBar.setTitle("Search");

        init();

        handleIntent(getIntent());

    }

    private void init() {
        listView = (ListView) findViewById(R.id.search_list_view);
        searchList = new ArrayList<>();
        searchAdapter = new SearchAdapter(this,searchList);
        listView.setAdapter(searchAdapter);
    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            lodingView.setVisibility(View.VISIBLE);
            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
            //use the query to search your data somehow
            Task.search(query,new searchListener(),errorListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    class searchListener implements Response.Listener<String>{

        @Override
        public void onResponse(String response) {
            JSONObject object = JSON.parseObject(response);
            List<Exercise> list = JSON.parseArray(object.getString("message"),Exercise.class);

            searchList.addAll(list);
            searchAdapter.notifyDataSetChanged();
            lodingView.setVisibility(View.INVISIBLE);
        }
    }


}
