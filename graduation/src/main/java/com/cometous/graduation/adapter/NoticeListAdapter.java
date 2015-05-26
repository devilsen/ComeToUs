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
import com.cometous.graduation.activity.DetailActivity;
import com.cometous.graduation.activity.UserInfoActivity;
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
            viewHolder.username = (TextView) convertView.findViewById(R.id.notice_username);
            viewHolder.text = (TextView) convertView.findViewById(R.id.notice_txt);
            viewHolder.activty = (TextView) convertView.findViewById(R.id.notice_activity);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).getContent() != null && !list.get(position).getContent().isEmpty()){
            if (list.get(position).getContent().contains("参加")){
                viewHolder.title.setText("参与通知");
                viewHolder.text.setText("参加了你发起的");
            }else if (list.get(position).getContent().contains("退出")){
                viewHolder.title.setText("退出通知");
                viewHolder.text.setText("退出了你发起的");
            }
        }

        if (list.get(position).getSend_from_name() != null && !list.get(position).getSend_from_name().isEmpty()){
            viewHolder.username.setText(list.get(position).getSend_from_name());
        }

        if (list.get(position).getAction_name() != null && !list.get(position).getAction_name().isEmpty()){
            viewHolder.activty.setText(list.get(position).getAction_name());
        }else {
            viewHolder.activty.setText("未知活动");
        }

        cardView(viewHolder,position);

        return convertView;
    }

    private void cardView(ViewHolder holder,int position){
        myOnclickListener = new MyOnclickListener(position);
        holder.username.setOnClickListener(myOnclickListener);
        holder.activty.setOnClickListener(myOnclickListener);

    }

    class MyOnclickListener implements View.OnClickListener{

        private int position;

        public MyOnclickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.notice_username:
                    Intent userinfoIntent = new Intent(mContext, UserInfoActivity.class);
                    userinfoIntent.putExtra("userid", list.get(position).getSend_from());
                    mContext.startActivity(userinfoIntent);
                    break;
                case R.id.notice_activity:
                    if (list.get(position).getAction_id() != null){
                        Intent detailIntent = new Intent(mContext, DetailActivity.class);
                        detailIntent.putExtra("paramId", list.get(position).getAction_id());
                        mContext.startActivity(detailIntent);
                    }else{
                        Toast.makeText(mContext,"活动已过期",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    }

    class ViewHolder {
        TextView title;
        TextView username;
        TextView text;
        TextView activty;
    }
}
