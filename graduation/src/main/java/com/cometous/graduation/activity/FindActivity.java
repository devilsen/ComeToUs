package com.cometous.graduation.activity;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.R;
import com.cometous.graduation.adapter.AssistListener;
import com.cometous.graduation.adapter.MainListAdapter;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.http.volley.ParseError;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends BaseActivity implements AssistListener {

    private ViewPager mPager;

    private List<View> listViews; // Tab页面列表
    private ImageView cursor;// 动画图片
    private TextView t1, t2, t3;// 页卡头标
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号

    //列表
    List<Exercise> exerciseListlist = new ArrayList<>();
    private MainListAdapter recommedListAdapter;
    private MainListAdapter ficationdListAdapter;
    private MainListAdapter nearbyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.find_layout);

        actionBar.setTitle("Find");

        initText();
        initViewPager();
        InitImageView();

    }

    private void initText() {
        t1 = (TextView) findViewById(R.id.recommend_txt);
        t2 = (TextView) findViewById(R.id.fication_txt);
        t3 = (TextView) findViewById(R.id.nearby_txt);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }


    private void initViewPager() {

        Task.getActivityList(null, new ActivityList(), errorListener);

        mPager = (ViewPager) findViewById(R.id.find_viewpager);
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        FrameLayout recommedLayout = (FrameLayout) mInflater.inflate(R.layout.recommed_layout, null);
        ListView recommedListView = (ListView) recommedLayout.findViewById(R.id.list_view);
        recommedListAdapter = new MainListAdapter(this, exerciseListlist, this);
        recommedListView.setAdapter(recommedListAdapter);

        FrameLayout ficationdLayout = (FrameLayout) mInflater.inflate(R.layout.fication_layout, null);
        ListView ficationdListView = (ListView) ficationdLayout.findViewById(R.id.list_view);
        ficationdListAdapter = new MainListAdapter(this, exerciseListlist, this);
        ficationdListView.setAdapter(ficationdListAdapter);

        FrameLayout nearbyLayout = (FrameLayout) mInflater.inflate(R.layout.nearby_layout, null);
        ListView nearbyListView = (ListView) nearbyLayout.findViewById(R.id.list_view);
        nearbyListAdapter = new MainListAdapter(this, exerciseListlist, this);
        nearbyListView.setAdapter(nearbyListAdapter);

        listViews.add(recommedLayout);
        listViews.add(ficationdLayout);
        listViews.add(nearbyLayout);
        mPager.setAdapter(new MyPagerAdapter(listViews));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = screenW / 3;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(0, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    public void gotoDetail(Exercise item, int position) {

    }


    /**
     * ViewPager适配器
     */
    @SuppressWarnings("deprecation")
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }


    }


    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            int one = offset;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量

            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    t1.setTextColor(FindActivity.this.getResources().getColor(R.color.text_blue));
                    t2.setTextColor(Color.WHITE);
                    t3.setTextColor(Color.WHITE);
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    t2.setTextColor(FindActivity.this.getResources().getColor(R.color.text_blue));
                    t1.setTextColor(Color.WHITE);
                    t3.setTextColor(Color.WHITE);
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    t3.setTextColor(FindActivity.this.getResources().getColor(R.color.text_blue));
                    t1.setTextColor(Color.WHITE);
                    t2.setTextColor(Color.WHITE);
                    break;
            }
            currIndex = arg0;
            assert animation != null;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    private class ActivityList implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = JSON.parseObject(response);
                List<Exercise> list = JSON.parseArray(object.getString("actions"), Exercise.class);

                exerciseListlist.clear();
                exerciseListlist.addAll(list);
                recommedListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                callback.onException(new ParseError());
            }

        }
    }

}
