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
import android.widget.Toast;

import com.cometous.graduation.R;
import com.cometous.graduation.model.Exercise;
import com.cometous.graduation.model.Notice;

import java.util.List;

/**
 * Created by lenovo on 2015/4/10.
 */
public class NoticeListAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;

    private Context mContext;
    private AssistListener assistListener;
    private List<Notice> list;
    private MyOnclickListener myOnclickListener;


    public NoticeListAdapter(Context context, List<Notice> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.notice_item_list, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.notice_title_txt);
            viewHolder.text = (TextView) convertView.findViewById(R.id.notice_text_txt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        cardView(viewHolder,position);

        return convertView;
    }

    private void cardView(ViewHolder holder,int position){
        myOnclickListener = new MyOnclickListener(position);
        holder.title.setOnClickListener(myOnclickListener);
        holder.text.setOnClickListener(myOnclickListener);


    }

    class MyOnclickListener implements View.OnClickListener{

        private int position;

        public MyOnclickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.notice_text_txt:
                    Toast.makeText(mContext,"dante的个人介绍",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

    class ViewHolder {
        TextView title;
        TextView text;
    }
}
