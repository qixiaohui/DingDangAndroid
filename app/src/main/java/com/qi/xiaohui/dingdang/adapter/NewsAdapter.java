package com.qi.xiaohui.dingdang.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.activity.ContentActivity;
import com.qi.xiaohui.dingdang.model.table.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by TQi on 4/30/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<Result> results;
    private Context mContext;
    private Activity fromActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView poster;
        public LinearLayout bigButton;
        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            poster = (ImageView) v.findViewById(R.id.poster);
            bigButton = (LinearLayout) v.findViewById(R.id.bigButton);
        }
    }

    public NewsAdapter(ArrayList<Result> results, Context context, Activity activity){
        this.results = results;
        mContext = context;
        fromActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(results.get(position).getTitle());
        if(results.get(position).getIurl() != null && !results.get(position).getIurl().equals("") ){
            Picasso.with(mContext).load(results.get(position).getIurl()).into(holder.poster);
        }else{
            holder.poster.setVisibility(View.GONE);
        }

        holder.bigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentActivity.launchActivity(fromActivity, results.get(position));
            }
        });
    }

    public void addResults(ArrayList<Result> moreResults){
        this.results.addAll(moreResults);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
