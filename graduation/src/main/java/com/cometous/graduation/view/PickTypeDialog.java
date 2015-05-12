package com.cometous.graduation.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cometous.graduation.R;
import com.cometous.graduation.adapter.PickTypeDialogListener;

/**
 * Created by Devilsen on 2015/5/9.
 */
public class PickTypeDialog extends Dialog {

    private Context mContext;

    private static PickTypeDialogListener dialogListener;

    private static CheckBox box1;
    private static CheckBox box2;
    private static CheckBox box3;
    private static CheckBox box4;
    private static CheckBox box5;
    private static CheckBox box6;
    private static CheckBox box7;
    private static CheckBox box8;
    private static CheckBox box9;
    private static CheckBox box10;
    private static CheckBox box11;
    private static CheckBox box12;

    private static Button confirm;
    private static Button cancel;

    private myOnclickListeren mOnClickListener;

    public PickTypeDialog(Context context) {
        super(context);
        mContext = context;
    }

    public PickTypeDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    /**********通过这个方法，将回调函数的地址传入到MyDialog中*********/
    public void setDialogListener(PickTypeDialogListener listener){
        dialogListener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_type_dialog_layout);

        mOnClickListener = new myOnclickListeren();

        box1 = (CheckBox) findViewById(R.id.type1);
        box2 = (CheckBox) findViewById(R.id.type2);
        box3 = (CheckBox) findViewById(R.id.type3);
        box4 = (CheckBox) findViewById(R.id.type4);
        box5 = (CheckBox) findViewById(R.id.type5);
        box6 = (CheckBox) findViewById(R.id.type6);
        box7 = (CheckBox) findViewById(R.id.type7);
        box8 = (CheckBox) findViewById(R.id.type8);
        box9 = (CheckBox) findViewById(R.id.type9);
        box10 = (CheckBox) findViewById(R.id.type10);
        box11 = (CheckBox) findViewById(R.id.type11);
        box12 = (CheckBox) findViewById(R.id.type12);

        confirm = (Button) findViewById(R.id.pick_type_confirm);
        cancel = (Button) findViewById(R.id.pick_type_cancel);

        confirm.setOnClickListener(mOnClickListener);
        cancel.setOnClickListener(mOnClickListener);
    }

    class myOnclickListeren implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pick_type_confirm:
                    dialogListener.getTypeString(getPick());
                    dismiss();
                    break;
                case R.id.pick_type_cancel:
                    dismiss();
                    break;
            }
        }

    }

    private String getPick(){
        String pickString = "";

        if (box1.isChecked()){
            pickString = pickString + box1.getText().toString() + "@";
        }
        if (box2.isChecked()){
            pickString = pickString + box2.getText().toString() + "@";
        }
        if (box3.isChecked()){
            pickString = pickString + box3.getText().toString() + "@";
        }
        if (box4.isChecked()){
            pickString = pickString + box4.getText().toString() + "@";
        }
        if (box5.isChecked()){
            pickString = pickString + box5.getText().toString() + "@";
        }
        if (box6.isChecked()){
            pickString = pickString + box6.getText().toString() + "@";
        }
        if (box7.isChecked()){
            pickString = pickString + box7.getText().toString() + "@";
        }
        if (box8.isChecked()){
            pickString = pickString + box8.getText().toString() + "@";
        }
        if (box9.isChecked()){
            pickString = pickString + box9.getText().toString() + "@";
        }
        if (box10.isChecked()){
            pickString = pickString + box10.getText().toString() + "@";
        }
        if (box11.isChecked()){
            pickString = pickString + box11.getText().toString() + "@";
        }
        if (box12.isChecked()){
            pickString = pickString + box12.getText().toString() + "@";
        }

        return pickString;
    }


}
