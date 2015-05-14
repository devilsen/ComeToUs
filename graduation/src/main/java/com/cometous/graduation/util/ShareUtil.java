package com.cometous.graduation.util;

import android.content.Context;
import android.content.Intent;

import com.cometous.graduation.R;

/**
 * Created by Devilsen on 2015/5/15.
 */
public class ShareUtil {

    public static void shareToIntent(Context context,String text){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.app_description) + "\n" +
                "baidu Page :  https://www.baidu.com\n" +
                "Sample App :  http://45.56.82.203/" );
        context.startActivity(Intent.createChooser(share, context.getString(R.string.app_name)));
    }


}
