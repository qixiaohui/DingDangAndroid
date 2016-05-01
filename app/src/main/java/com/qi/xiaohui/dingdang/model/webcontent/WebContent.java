package com.qi.xiaohui.dingdang.model.webcontent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebContent {

    @SerializedName("content")
    @Expose
    private String content;

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
