package com.qi.xiaohui.dingdang.model.table;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("suggestions")
    @Expose
    private List<Object> suggestions = new ArrayList<Object>();
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The query
     */
    public String getQuery() {
        return query;
    }

    /**
     *
     * @param query
     * The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     *
     * @return
     * The suggestions
     */
    public List<Object> getSuggestions() {
        return suggestions;
    }

    /**
     *
     * @param suggestions
     * The suggestions
     */
    public void setSuggestions(List<Object> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The start
     */
    public Integer getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The length
     */
    public Integer getLength() {
        return length;
    }

    /**
     *
     * @param length
     * The length
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}
