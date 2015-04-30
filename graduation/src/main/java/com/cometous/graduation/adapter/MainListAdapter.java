package com.cometous.graduation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.cometous.graduation.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by lenovo on 2015/4/10.
 */
public class MainListAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;

    private Context mContext;
    private List<String> list;
    private MyOnclickListener myOnclickListener;

    public MainListAdapter(Context context, List<String> list) {
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
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.main_list_item, parent, false);
            viewHolder.cardLayout = (LinearLayout) convertView.findViewById(R.id.main_item_layout);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.main_item_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.main_item_title_txt);
            viewHolder.introduce = (TextView) convertView.findViewById(R.id.main_item_introduce_txt);
            viewHolder.share = (TextView) convertView.findViewById(R.id.main_share_txt);
            viewHolder.join = (TextView) convertView.findViewById(R.id.main_join_txt);
            viewHolder.peopleNum = (TextView) convertView.findViewById(R.id.main_people_num_txt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        cardView(viewHolder);

        return convertView;
    }

    private void cardView(ViewHolder holder){
        myOnclickListener = new MyOnclickListener();
        holder.cardLayout.setOnClickListener(myOnclickListener);
        holder.imageView.setOnClickListener(myOnclickListener);
        holder.title.setOnClickListener(myOnclickListener);
        holder.introduce.setOnClickListener(myOnclickListener);
        holder.share.setOnClickListener(myOnclickListener);
        holder.join.setOnClickListener(myOnclickListener);
        holder.peopleNum.setOnClickListener(myOnclickListener);


    }

    class MyOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.layout.main_list_item:
                case R.id.main_item_img:
                case R.id.main_item_title_txt:
                case R.id.main_item_introduce_txt:
                    Intent detailIntent = new Intent(mContext,DetailActivity.class);
                    mContext.startActivity(detailIntent);
                    break;
                case R.id.main_share_txt:
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "come to us");
                    mContext.startActivity(Intent.createChooser(shareIntent,"app"));
                    break;
                case R.id.main_join_txt:
                    Toast.makeText(mContext,"正在加入...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.main_people_num_txt:
                    Toast.makeText(mContext,"参加活动人的列表.",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    class ViewHolder {
        LinearLayout cardLayout;
        ImageView imageView;
        TextView title;
        TextView introduce;
        TextView share;
        TextView join;
        TextView peopleNum;
    }
}
