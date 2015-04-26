package com.cometous.graduation.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cometous.graduation.R;

/**
 * Created by Devilsen on 2015/4/26.
 */
public class LodingDialog extends Dialog {

    public LodingDialog(Context context, int theme) {
        super(context,theme);
    }

    public static class Builder{

        private Context mContext;
        private LinearLayout contentView;
        /**显示信息 */
        private TextView messageTxt;
        private String messgae;
        /** 是否可以点击返回取消 */
        private boolean cancelAble = true;

        public Builder(Context context){
            mContext = context;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            contentView = (LinearLayout) inflater.inflate(R.layout.dialog_loding_layout,null);
            messageTxt = (TextView) contentView.findViewById(R.id.dialog_loding_txt);
        }

        public Builder setMessage(String text){
            messageTxt.setText(text);
            return this;
        }

        public Builder isCancelable(boolean able){
            cancelAble = able;
            return this;
        }

        public LodingDialog create(){
            LodingDialog dialog = new LodingDialog(mContext,R.style.BaseDialog);
            dialog.setContentView(contentView);
            dialog.setCancelable(cancelAble);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }

    }





}
