package com.qdqy.admin.sinaapp.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.keysona.SinaAPI;
import com.qdqy.admin.sinaapp.utils.SmartFragmentStatePagerAdapter;

public class MySmartPagerAdapter extends SmartFragmentStatePagerAdapter {

    private static int PAGER_NUM = 5;
    private String[] newstype = {"军事","国外","国内","社会","最新"};
    public  int[] newsType = {SinaAPI.MILITARY_NEWS,SinaAPI.EXTERNAL_NEWS,SinaAPI.INTERNAL_NEWS,
            SinaAPI.SOCIAL_NEWS,SinaAPI.TOP_NEWS};
    FragmentManager fragmentManager;


    public MySmartPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                if (null == fragmentManager.findFragmentById(position)) {
                    return (TitleFragment) TitleFragment.newInstance(newsType[0],1);
                }
                return (TitleFragment)fragmentManager.getFragments().get(0);
            case 1:
                if (null == fragmentManager.findFragmentById(position)) {
                    return (TitleFragment) TitleFragment.newInstance(newsType[1],1);
                }
                return (TitleFragment)fragmentManager.getFragments().get(1);
            case 2:
                if (null == fragmentManager.findFragmentById(position)) {
                    return (TitleFragment) TitleFragment.newInstance(newsType[2],1);
                }
                return (TitleFragment)fragmentManager.getFragments().get(2);
            case 3:
                if (null == fragmentManager.findFragmentById(position)) {
                    return (TitleFragment) TitleFragment.newInstance(newsType[3],1);
                }
                return (TitleFragment)fragmentManager.getFragments().get(3);
            case 4:
                if (null == fragmentManager.findFragmentById(position)) {
                    return (TitleFragment) TitleFragment.newInstance(newsType[4],1);
                }
                return (TitleFragment)fragmentManager.getFragments().get(4);
            default: return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return PAGER_NUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newstype[position];
    }


}
