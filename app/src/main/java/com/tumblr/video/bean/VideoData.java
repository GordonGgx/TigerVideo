package com.tumblr.video.bean;

import cn.ittiger.database.annotation.PrimaryKey;
import cn.ittiger.database.annotation.Table;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
@Table(name = "VideoTable")
public class VideoData {

    @PrimaryKey(isAutoGenerate = true)
    private long id;
    private String url;

    public VideoData() {

    }

    public VideoData(String url) {

        this.url = url;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
