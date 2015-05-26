package com.cometous.graduation.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Devilsen on 2015/5/3.
 */
public class Exercise {

    @JSONField(name="_id")
    private String id;
    private String addr_name;
    private String creator;
    @JSONField(name="_v")
    private String v;
    private String img_url;
    private String active;
    private String top;
    private String type_id;
    private String forkable;
    private String fork_count;
    private String unlike_count;
    private String like_count;
    private String visit_count;
    private String reply_count;
    private String addr_position_y;
    private String addr_position_x;
    private String desc;
    private long edit_date;
    private long end_date;
    private long start_date;
    private long create_date;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFork_count() {
        return fork_count;
    }

    public void setFork_count(String fork_count) {
        this.fork_count = fork_count;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



    public String getAddr_name() {
        return addr_name;
    }

    public void setAddr_name(String addr_name) {
        this.addr_name = addr_name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getForkable() {
        return forkable;
    }

    public void setForkable(String forkable) {
        this.forkable = forkable;
    }

    public String getUnlike_count() {
        return unlike_count;
    }

    public void setUnlike_count(String unlike_count) {
        this.unlike_count = unlike_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public String getAddr_position_y() {
        return addr_position_y;
    }

    public void setAddr_position_y(String addr_position_y) {
        this.addr_position_y = addr_position_y;
    }

    public String getAddr_position_x() {
        return addr_position_x;
    }

    public void setAddr_position_x(String addr_position_x) {
        this.addr_position_x = addr_position_x;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(long edit_date) {
        this.edit_date = edit_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
