package com.qi.xiaohui.dingdang.model.menu;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu extends BaseObservable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("category")
    @Expose
    private List<String> category = new ArrayList<String>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     * The genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     * The category
     */
    public List<String> getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(List<String> category) {
        this.category = category;
    }

}