package com.sina.alarm.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sina.alarm.bean.AudioNewsItem;
import com.sina.alarm.ui.view.AudioItemView;

public class AudioListAdapter extends BaseAdapter {

    private List<AudioNewsItem> mItemList;

    public AudioListAdapter() {
        mItemList = new ArrayList<AudioNewsItem>();
    }

    public void setData(List<AudioNewsItem> data) {
        this.mItemList = data;
        notifyDataSetChanged();
    }

    public List<AudioNewsItem> getData() {
        if (mItemList == null) {
            mItemList = new ArrayList<AudioNewsItem>();
        }

        return mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public AudioNewsItem getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new AudioItemView(parent.getContext());
        }

        if (convertView instanceof AudioItemView) {
            ((AudioItemView) convertView).setData(getItem(position));
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mItemList.isEmpty();
    }

}
