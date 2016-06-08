package com.qdqy.admin.sinaapp.newsdata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.keysona.SinaAPI;
import com.keysona.WordNews;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AsyncGetData extends AsyncTask<Void,Void,List<News>>{

    public Bitmap bitmaps;
    final static OkHttpClient client = new OkHttpClient();
    public int newsType;
    public int order;
    public static SinaAPI sinaData = null;
    static String tempUrl = "http://pic2.ooopic.com/01/03/51/25b1OOOPIC19.jpg";
    News newsdata;

    public static AsyncGetData newInstance(int newsType,int order){
        AsyncGetData asyncGetData = new AsyncGetData();
        asyncGetData.order = order;
        asyncGetData.newsType = newsType;
        return asyncGetData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<News> doInBackground(Void... params) {
        sinaData = new SinaAPI();
        List<News> list = new ArrayList<>();

        WordNews[] news = sinaData.getNews(newsType, order);

        for(int i=0;i<news.length;i++){
            newsdata = new News();
            String urli = news[i].getImageUrl() == ""? tempUrl : news[i].getImageUrl();
            Thread threads = new Thread(new InnerPhotoThread(urli, i));
            threads.start();
            try {
                threads.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newsdata.id = news[i].getId();
            newsdata.url = news[i].getUrl();
            newsdata.title = news[i].getTitle();
            newsdata.summary = news[i].getSummary();
            newsdata.date = news[i].getDate();
            newsdata.comment = Integer.decode(news[i].getComment());
            newsdata.photo = bitmaps;
            newsdata.source = news[i].getSource();
            list.add(newsdata);
        }

        return list;
    }


    public class InnerPhotoThread implements Runnable{

        String url ;
        int i;

        public InnerPhotoThread(String url,int i) {
            this.url = url;
            this.i = i;
        }

        @Override
        public void run() {
            getPhoto(url);
        }

        protected void getPhoto(String url) {


            Request request = new Request.Builder().url(url).build();
            try{
                //get bitmap
                Response response = client.newCall(request).execute();
                InputStream inputStream = response.body().byteStream();
                bitmaps = BitmapFactory.decodeStream(inputStream);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

