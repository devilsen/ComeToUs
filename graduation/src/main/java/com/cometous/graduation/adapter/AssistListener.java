package com.cometous.graduation.adapter;

import com.cometous.graduation.model.Exercise;

/**
 * Created by Devilsen on 2015/5/3.
 */
public interface AssistListener {

    public abstract void gotoDetail(Exercise item,int position);
    public abstract void share(Exercise item,int position);
    public abstract void join(Exercise item,int position);
    public abstract void zan(Exercise item,int position);

}
