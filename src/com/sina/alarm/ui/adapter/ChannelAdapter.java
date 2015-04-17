package com.sina.alarm.ui.adapter;

import java.util.HashSet;
import java.util.Set;

import com.sina.alarm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter {
	private Context mCtx;
	private LayoutInflater mInflater; 
	private String[] mChannels;
	
	public ChannelAdapter(Context ctx) {
		mCtx = ctx.getApplicationContext();
		mInflater = LayoutInflater.from(ctx);
		mChannels = ctx.getResources().getStringArray(R.array.channels);
	}

	@Override
	public int getCount() {
		return mChannels.length;
	}

	@Override
	public Object getItem(int position) {
		return mChannels[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup rootView) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.channel_list_item, rootView, false);
			holder.channelImage = (ImageView)convertView.findViewById(R.id.imv_channel);
			holder.chooseImage = (ImageView)convertView.findViewById(R.id.imv_choose);
			holder.channelName = (TextView)convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		int resId = mCtx.getResources().getIdentifier(
				String.format("ic_channel_%d", position), "drawable",
				mCtx.getPackageName());
		holder.channelImage.setImageDrawable(mCtx.getResources().getDrawable(resId));
		holder.channelName.setText(mChannels[position]);
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView channelImage;
		ImageView chooseImage;
		TextView channelName;
	}
}
