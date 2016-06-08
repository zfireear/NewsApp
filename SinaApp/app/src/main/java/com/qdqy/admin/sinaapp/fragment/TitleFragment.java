package com.qdqy.admin.sinaapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qdqy.admin.sinaapp.R;
import com.qdqy.admin.sinaapp.comment.ContentWebView;
import com.qdqy.admin.sinaapp.newsdata.AsyncGetData;
import com.qdqy.admin.sinaapp.newsdata.MyAdapter;
import com.qdqy.admin.sinaapp.newsdata.News;

import java.util.List;

public class TitleFragment extends Fragment {

    public static List<News> list,newList;
    public ListView lv;
    public MyAdapter myAdapter,newAdapter;
    private String urlWeb;
    static AsyncGetData asyncGetData;
    public static int PAGE;
//    public static int NTYPE;

    //these definition
    private final int AUTOLOAD_THRESHOLD = 0;
    private View mFooterView;
    private boolean mIsLoading = false;
    public Runnable loadMoreListItems,returnRes;

    public static TitleFragment newInstance(int i,int k){
        TitleFragment fragment = new TitleFragment();

        //add Bundle
        PAGE = k;
//        NTYPE = i;
        Bundle args = new Bundle();
        args.putInt("PAGE", k);
        args.putInt("NTYPE", i);
        fragment.setArguments(args);
        Log.d("list", "list" + i);
        return fragment;
    }

    private static List<News> getNewList(int i,int k){

        asyncGetData = AsyncGetData.newInstance(i,k);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = asyncGetData.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SomeInt = getArguments().getInt("PAGE", 1);
        int someTitle = getArguments().getInt("NTYPE");
        list = getNewList(someTitle,SomeInt);

        myAdapter = new MyAdapter(getActivity(),list,R.layout.news_item);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ListView) view.findViewById(R.id.lv);

        //Runnable to load the items
        loadMoreListItems = new Runnable() {
            @Override
            public void run() {

                int someNewTitle = getArguments().getInt("NTYPE");
                newList = getNewList(someNewTitle, ++PAGE);
                Log.d("myListItems",String.valueOf(newList.size()));
                getActivity().runOnUiThread(returnRes);
            }
        };

        //Since we cant update our UI from a thread this Runnable takes care of that!
         returnRes = new Runnable() {
            @Override
            public void run() {

                if(newList != null && newList.size() > 0){
                   // int currentPosition = lv.getLastVisiblePosition();
                    newAdapter = new MyAdapter(getActivity(),newList,R.layout.news_item);
                    newAdapter.notifyDataSetChanged();
                    //remove the mFooterView
                    //lv.removeFooterView(mFooterView);
                    //mFooterView.setVisibility(View.GONE);
                    lv.setAdapter(newAdapter);
                   // lv.setSelectionFromTop(currentPosition, 0);
                    Log.d("myAdapter", "myAdapter.notifyDataSetChanged()");
                    mIsLoading = false;
                }
            }
        };


        /*
        * this code loads data and display in listview
        * */


        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.footer,null);
        lv.addFooterView(mFooterView, null, false);

        lv.setAdapter(myAdapter);
        //scroll to the bottem of list
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount && !(mIsLoading)){
                    Thread thread = new Thread(null,loadMoreListItems);
                    thread.start();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                urlWeb = list.get(position).getUrl();
                String newsId = list.get(position).id;
                ContentWebView.actionStart(getActivity(), urlWeb, newsId);
            }
        });
    }

}
