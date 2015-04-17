package com.sina.alarm.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import com.sina.alarm.R;
import com.sina.alarm.app.Constants;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sina.alarm.bean.AudioNewsItem;
import com.sina.alarm.ui.adapter.AudioListAdapter;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener,
        OnCompletionListener {

    public final static SimpleDateFormat PLAY_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    {
        PLAY_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    }

    private List<AudioNewsItem> mItems;
    private MediaPlayer mPlayer;
    private boolean mIsPlayerPausedByActivity = false;

    private TextView mTitleView;
    private TextView mDescriptionView;
    private ImageView mPlayButton;
    private TextView mPlayProgress;
    private TextView mPlayDuration;
    private ListView mAudioList;
    private AudioListAdapter mAudioListAdapter;

    private Handler mHandler;
    private Runnable mPlayRuntime;

    public static void startActivity(Context ctx, long newsId) {
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.putExtra(Constants.sNewsIdKey, newsId);
        if (!(ctx instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setupActionBar();
        initView();
    }

    private void setupActionBar() {
        ImageView imv = (ImageView) this.findViewById(R.id.imv_back);
        imv.setVisibility(View.GONE);

        imv = (ImageView) this.findViewById(R.id.imv_alarm);
        imv.setOnClickListener(this);

        imv = (ImageView) this.findViewById(R.id.imv_settings);
        imv.setOnClickListener(this);
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.tv_item_title);
        mDescriptionView = (TextView) findViewById(R.id.tv_item_description);
        mPlayButton = (ImageView) findViewById(R.id.iv_play_button);
        mPlayButton.setOnClickListener(this);

        mPlayProgress = (TextView) findViewById(R.id.tv_play_progress);
        mPlayDuration = (TextView) findViewById(R.id.tv_play_duration);

        mAudioList = (ListView) findViewById(R.id.lv_play_list);
        mAudioList.setOnItemClickListener(this);
        mAudioListAdapter = new AudioListAdapter();
        mAudioList.setAdapter(mAudioListAdapter);
        mAudioListAdapter.setData(mItems);

        if (mItems.size() > 0) {
            selectItem(mItems.get(0));
        }

    }

    private void initData() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);

        mHandler = new Handler();
        mPlayRuntime = new Runnable() {
            @Override
            public void run() {
                updatePlayProgress();
                mHandler.postDelayed(this, 1000);
            }
        };

        mHandler.post(mPlayRuntime);

        mItems = new ArrayList<AudioNewsItem>();

        mItems.add(new AudioNewsItem(1, getUri(R.raw.audio_1), "李嘉诚15亿捐建寺庙曝光 每天公供400人参观",
                "李嘉诚15亿捐建寺庙曝光，每天公供400人参观...", ""));
        mItems.add(new AudioNewsItem(2, getUri(R.raw.audio_2), "中日韩将提出共同观测PM2.5",
                "中日韩将提出共同观测PM2.5...", ""));
        mItems.add(new AudioNewsItem(3, getUri(R.raw.audio_3), "浙江省温州市副市长孔海龙接受组织调查",
                "浙江省温州市副市长孔海龙接受组织调查...", ""));
        mItems.add(new AudioNewsItem(4, getUri(R.raw.audio_4), "菲总统称中国对南海主权声索引发恐惧 中方回应",
                "菲总统称中国对南海主权声索引发恐惧，中方回应...", ""));
    }

    private void updatePlayProgress() {
        int time = 0;
        int duration = 0;

        try {
            duration = mPlayer.getDuration();
            time = mPlayer.getCurrentPosition();
        } catch (Exception e) {
            time = 0;
            duration = 0;
        }

        mPlayProgress.setText(formatPlayTime(time));
        mPlayDuration.setText(formatPlayTime(duration));
    }

    public Uri getUri(int resId) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("android.resource");
        builder.authority(getPackageName());
        builder.appendPath(Integer.toString(resId));

        return builder.build();
    }

    public void selectItem(AudioNewsItem item) {
        boolean isPlaying = mPlayer.isPlaying();
        mPlayer.stop();
        mPlayer.reset();

        mTitleView.setText(item.getTitle());

        final CharSequence orginalText = item.getDescription();
        final CharSequence moreText = "More";

        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(orginalText);
        spannable.append(moreText);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.link_color)),
                orginalText.length(), orginalText.length() + moreText.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        mDescriptionView.setText(spannable);

        try {
            mPlayer.setDataSource(this, item.getAudioUri());
            mPlayer.prepare();
            if (isPlaying) {
                mPlayer.start();
                updatePlayProgress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int pos = mAudioList.getSelectedItemPosition();
        int next = (pos + 1) % mAudioList.getCount();

        mAudioList.setSelection(next);
        selectItem((AudioNewsItem) mAudioList.getSelectedItem());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAudioList.setSelection(position);
        selectItem((AudioNewsItem) view.getTag());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsPlayerPausedByActivity && !mPlayer.isPlaying()) {
            mPlayer.start();
            updatePlayProgress();
            mIsPlayerPausedByActivity = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            mIsPlayerPausedByActivity = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        mPlayer.release();
    }

    public static String formatPlayTime(int time) {
        return PLAY_TIME_FORMAT.format(new Date(time));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_alarm:
                AlarmListActivity.startActvity(this);
                break;
            case R.id.imv_settings:
                // TODO: tempory here.
                NewsContentActivity.startActivity(this, 1);
                break;
            default:
                break;
        }

        if (view == mPlayButton) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            } else {
                mPlayer.start();
            }

            updatePlayProgress();
        }
    }
}
