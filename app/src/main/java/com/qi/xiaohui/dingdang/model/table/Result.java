package com.qi.xiaohui.dingdang.model.table;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result extends BaseObservable{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("kwic")
    @Expose
    private String kwic;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("iurl")
    @Expose
    private String iurl;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("news")
    @Expose
    private Boolean news;
    @SerializedName("votes")
    @Expose
    private String votes;
    @SerializedName("date")
    @Expose
    private Double date;
    @SerializedName("related")
    @Expose
    private List<Object> related = new ArrayList<Object>();

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The kwic
     */
    public String getKwic() {
        return kwic;
    }

    /**
     *
     * @param kwic
     * The kwic
     */
    public void setKwic(String kwic) {
        this.kwic = kwic;
    }

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

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The iurl
     */
    public String getIurl() {
        return iurl;
    }

    /**
     *
     * @param iurl
     * The iurl
     */
    public void setIurl(String iurl) {
        this.iurl = iurl;
    }

    /**
     *
     * @return
     * The domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     *
     * @param domain
     * The domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The news
     */
    public Boolean getNews() {
        return news;
    }

    /**
     *
     * @param news
     * The news
     */
    public void setNews(Boolean news) {
        this.news = news;
    }

    /**
     *
     * @return
     * The votes
     */
    public String getVotes() {
        return votes;
    }

    /**
     *
     * @param votes
     * The votes
     */
    public void setVotes(String votes) {
        this.votes = votes;
    }

    /**
     *
     * @return
     * The date
     */
    public Double getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(Double date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The related
     */
    public List<Object> getRelated() {
        return related;
    }

    /**
     *
     * @param related
     * The related
     */
    public void setRelated(List<Object> related) {
        this.related = related;
    }

}


