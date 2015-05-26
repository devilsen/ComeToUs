package com.cometous.graduation.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Devilsen on 2015/5/13.
 */
public class Notice {

    @JSONField(name="_id")
    private String id;
    private String hasRead;
    private String send_from;
    private String action_id;
    private String send_from_name;
    private String action_name;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHasRead() {
        return hasRead;
    }

    public void setHasRead(String hasRead) {
        this.hasRead = hasRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSend_from() {
        return send_from;
    }

    public void setSend_from(String send_from) {
        this.send_from = send_from;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getSend_from_name() {
        return send_from_name;
    }

    public void setSend_from_name(String send_from_name) {
        this.send_from_name = send_from_name;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }
}
