package com.sina.alarm.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.alarm.R;
import com.sina.alarm.bean.AudioNewsItem;

public class AudioItemView extends RelativeLayout {

    private AudioNewsItem mItem;

    private View mParentView;
    private TextView mTitleView;
    private TextView mDescriptionView;

    public AudioItemView(Context context) {
        super(context);
        mParentView = LayoutInflater.from(context).inflate(R.layout.audio_item_view, this);
        mTitleView = (TextView) mParentView.findViewById(R.id.tv_title);
        mDescriptionView = (TextView) mParentView.findViewById(R.id.tv_description);
    }

    public void setData(AudioNewsItem item) {
        this.mItem = item;
        setTag(item);
        notifyDataChange();
    }

    public AudioNewsItem getData() {
        return mItem;
    }

    public void notifyDataChange() {
        mTitleView.setText(mItem.getTitle());
        mDescriptionView.setText(mItem.getDescription());
    }

}
