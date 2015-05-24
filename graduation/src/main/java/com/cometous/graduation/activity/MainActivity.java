package com.cometous.graduation.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.AssistListener;
import com.cometous.graduation.adapter.DrawerAdapter;
import com.cometous.graduation.adapter.MainListAdapter;
import com.cometous.graduation.exception.BusinessError;
import com.cometous.graduation.exception.NetCallback;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.NetworkError;
import com.cometous.graduation.http.volley.NoConnectionError;
import com.cometous.graduation.http.volley.ParseError;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.http.volley.ServerError;
import com.cometous.graduation.http.volley.TimeoutError;
import com.cometous.graduation.http.volley.VolleyError;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.util.CacheUtil;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yalantis.phoenix.PullToRefreshView;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AssistListener{

    public static final int REFRESH_DELAY = 2000;
    //actionBar
    private ActionBar actionBar;
    //返回箭头
    private DrawerArrowDrawable drawerArrow;
    //抽屉菜单开关
    private ActionBarDrawerToggle mDrawerToggle;
    //抽屉界面
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    //下拉刷新
    private PullToRefreshView mPullToRefreshView;
    //上拉加载次数
    private int upCount = 0;
    //列表
    private ListView listView;
    List<Exercise>  exerciseListlist = new ArrayList<Exercise>();
    private MainListAdapter mainListAdapter;

    protected Handler exph = new Handler();
    public NetCallback callback = new DefaultCallback(exph);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.drawer_close);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        //初始化抽屉菜单
        initDrawer();
        initPullToRefresh();

        getFromMenory();

    }

    /**
     * 初始化下拉刷新组件
     */
    private void initPullToRefresh() {

        listView = (ListView) findViewById(R.id.main_list_view);
        mainListAdapter = new MainListAdapter(this, exerciseListlist,this);
        listView.setAdapter(mainListAdapter);

        final ErrorResponse errorResponse = new ErrorResponse();

        /** 下拉刷新 */
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        Task.getActivityList(null, new ActivityList(), errorResponse);
                        upCount = 0;
                    }
                }, REFRESH_DELAY);
            }
        });
        lodingMore();
    }

    /** 底端自动加载 */
    private void lodingMore(){

        View footLayout;

        footLayout = this.getLayoutInflater().inflate(R.layout.lodemore_footview, null);

        listView.addFooterView(footLayout);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int lastItemIndex;//当前ListView中最后一个Item的索引

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemIndex == mainListAdapter.getCount() - 1) {
                    upCount++;
                    //加载数据
                    Task.getMoreActivityList(upCount * 10, new ActivityList(), new ErrorResponse());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemIndex = firstVisibleItem + visibleItemCount - 1 -1;
            }
        });


    }

    private void initDrawer(){

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                actionBar.setTitle(R.string.drawer_close);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                actionBar.setTitle(R.string.drawer_open);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        DrawerAdapter drawerAdapter = new DrawerAdapter(this);

        RelativeLayout headlayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.darwer_head_layout, null);
//        RelativeLayout bottomlayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.darwer_bottom_layout,null);
        ImageLoader.getInstance().displayImage("http://gexing.edujq.com/img/2013/04/19/04191009418291.jpg", (ImageView)headlayout.findViewById(R.id.head_img));
        mDrawerList.addHeaderView(headlayout);
//        mDrawerList.addFooterView(bottomlayout);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent userinfo = new Intent(MainActivity.this, UserInfoActivity.class);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        startActivity(userinfo);
                        break;
                    case 1:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 2:
                        Intent initiate = new Intent(MainActivity.this, InitiateActivity.class);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        startActivity(initiate);
                        break;
                    case 3:
                        Intent findInitiate = new Intent(MainActivity.this, FindActivity.class);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        startActivity(findInitiate);
                        break;
                    case 4:
                        Intent searchInitiate = new Intent(MainActivity.this, SearchActivity.class);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        startActivity(searchInitiate);
                        break;
                    case 5:
                        Intent settingInitiate = new Intent(MainActivity.this, SettingActivity.class);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        startActivity(settingInitiate);
                        break;


                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                break;
            case R.id.about_cometous:
                Intent aboutIntent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.exit_cometous:
                Intent exitIntent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(exitIntent);
                finish();
                break;
            case R.id.my_notice:
                Intent noticeIntent = new Intent(MainActivity.this,NoticeActivity.class);
                mDrawerLayout.closeDrawer(mDrawerList);
                startActivity(noticeIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 进入详情页
     * @param position
     */
    @Override
    public void gotoDetail(Exercise item, int position) {
        Intent detailIntent = new Intent(this,DetailActivity.class);
        detailIntent.putExtra("paramId", item.getId());
//        Log4Utils.i("paramid", item.get_id());
        startActivity(detailIntent);
    }

    /**
     * 从缓存中读取
     */
    private void getFromMenory(){
        String response = CacheUtil.getMemory("mainList");
        if (response != null){
            jsonToList(response);
        }else{
            mPullToRefreshView.setRefreshing(true, true);
        }

    }


    private class ActivityList implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try{
                if ( upCount == 0 ){
                    //放入缓存
                    CacheUtil.addMemory("mainList", response);
                }
                //解析json数据
                jsonToList(response);
            }catch (Exception e){
                callback.onException(new ParseError());
            }

        }
    }

    /**
     * 将json解析，并放入list中
     * @param response
     */
    private void jsonToList(String response){
        JSONObject object = JSON.parseObject(response);
        List<Exercise> list = JSON.parseArray(object.getString("actions"),Exercise.class);

        if (upCount == 0){
            exerciseListlist.clear();
        }
        exerciseListlist.addAll(list);
        mainListAdapter.notifyDataSetChanged();
        mPullToRefreshView.setRefreshing(false);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    public class DefaultCallback implements NetCallback{

        private Handler excptionHandler;
        private String errorString = "未知错误";

        public DefaultCallback(Handler excptionHandler) {
            super();
            this.excptionHandler = excptionHandler;
        }

        @Override
        public void onException(Exception e) {
            if (e instanceof ConnectTimeoutException){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof java.net.SocketException){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof IOException){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof NoConnectionError){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof TimeoutError){
                errorString = "网络好像不给力哦，超时了";
            }else if (e instanceof ServerError){
                errorString = "服务器出错了";
            }else if (e instanceof ParseError){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof NetworkError){
                errorString = "网络好像不给力哦，检查网络吧";
            }else if (e instanceof BusinessError){
//                JSONObject json = JSON.parseObject(e.getMessage());
//                errorString = json.getString("res_message");
                errorString = "网络好像不给力哦，检查网络吧";
            }
//            if( mPullToRefreshView.isActivated()){
//                mPullToRefreshView.setRefreshing(false);
//            }
        }
        @Override
        public void makeToast() {
            excptionHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),errorString,Toast.LENGTH_SHORT);
                }
            });
        }
    }

    /**
     * 错误回调接口
     */
    private class ErrorResponse implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            upCount--;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
