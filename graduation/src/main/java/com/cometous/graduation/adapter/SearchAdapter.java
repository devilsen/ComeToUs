package com.cometous.graduation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cometous.graduation.MyApplication;
import com.cometous.graduation.R;
import com.cometous.graduation.activity.DetailActivity;
import com.cometous.graduation.http.Task;
import com.cometous.graduation.model.Exercise;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Devilsen on 2015/5/26.
 */
public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<Exercise> list;

    private MyOnClickListener myOnClickListener;
    private LayoutInflater inflater;


    public SearchAdapter(Context mContext, List<Exercise> list) {
        this.mContext = mContext;
        this.list = list;

        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null){
            viewHoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.list_join_init_item,null);
            viewHoder.layout = (LinearLayout) convertView.findViewById(R.id.join_or_init_layout);
            viewHoder.imageView = (ImageView) convertView.findViewById(R.id.activity_img);
            viewHoder.title = (TextView) convertView.findViewById(R.id.activity_title_txt);
            viewHoder.introduce = (TextView) convertView.findViewById(R.id.activity_introduce_txt);

            convertView.setTag(viewHoder);
        }else{
            viewHoder = (ViewHoder) convertView.getTag();
        }

        viewHoder.title.setText(list.get(position).getName());
        viewHoder.introduce.setText(list.get(position).getDesc());

        if ( list.get(position).getImg_url() != null && !list.get(position).getImg_url().isEmpty()){
            ImageLoader.getInstance().displayImage(Task.HOST + list.get(position).getImg_url(), viewHoder.imageView, MyApplication.options);
        }else if(position % 2 ==1){
            viewHoder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_picture_1));
        }else{
            viewHoder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_picture_2));
        }

        myOnClickListener = new MyOnClickListener(list.get(position).getId());
        viewHoder.layout.setOnClickListener(myOnClickListener);
        viewHoder.imageView.setOnClickListener(myOnClickListener);

        return convertView;
    }

    class MyOnClickListener implements View.OnClickListener{

        String id;

        public MyOnClickListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Intent detailIntent = new Intent(mContext,DetailActivity.class);
            detailIntent.putExtra("paramId", id);
            mContext.startActivity(detailIntent);
        }
    }

    class ViewHoder{
        ImageView imageView;
        TextView title;
        TextView introduce;
        LinearLayout layout;
    }
}
