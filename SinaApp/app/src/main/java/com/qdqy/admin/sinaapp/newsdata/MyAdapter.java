package com.qdqy.admin.sinaapp.newsdata;

import android.content.Context;

import com.qdqy.admin.sinaapp.R;
import com.qdqy.admin.sinaapp.utils.CommonAdapter;
import com.qdqy.admin.sinaapp.utils.ViewHolder;

import java.util.List;

public class MyAdapter extends CommonAdapter<News>{

    public MyAdapter(Context context, List<News> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, News news) {
        holder.setText(R.id.news_title,news.getTitle())
                .setText(R.id.news_summary, news.getSummary())
                .setText(R.id.news_from,news.getSource())
                .setText(R.id.news_data,news.getDate())
                .setText(R.id.news_comment, news.getComment() + "评论")
                .setImageBitmap(R.id.image, news.getPhoto());
    }

}
