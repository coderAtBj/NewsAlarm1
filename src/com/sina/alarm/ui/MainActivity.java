package com.sina.alarm.ui;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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

import com.sina.alarm.R;
import com.sina.alarm.app.AppLauncher;
import com.sina.alarm.app.Constants;
import com.sina.alarm.bean.AudioNewsItem;
import com.sina.alarm.model.MediaManager;
import com.sina.alarm.model.MediaManager.OnProgressChangeListener;
import com.sina.alarm.sensor.ShakeListener;
import com.sina.alarm.sensor.ShakeListener.OnShakeListener;
import com.sina.alarm.ui.adapter.AudioListAdapter;
import com.sina.alarm.util.Util;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener,
        OnCompletionListener, OnProgressChangeListener, OnShakeListener {

    private Map<Class<?>, State> mStatePool = new HashMap<Class<?>, State>();

    abstract public class State implements OnShakeListener {

        public State() {
            onActivate();
        }

        /**
         * 每次进入该状态时调用
         */
        public void onActivate() {
        }

        /**
         * 播放按钮被点击事件
         */
        public void onClickPlayButton() {
            Util.logd(this.getClass().getSimpleName() + "." + "onClickPlayButton");
        }

        /**
         * 播放进度变化事件
         */
        public void onProgressChange() {
            // Util.logd(this.getClass().getSimpleName() + "." + "onProgressChange");
        }

        /**
         * 播放列表中选择点击事件
         * 
         * @param item
         */
        public void onSelectItem(AudioNewsItem item) {
            Util.logd(this.getClass().getSimpleName() + "." + "onSelectItem");

            mCurrentItem = item;
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
        }

        @Override
        public void onShake() {
        }

        @Override
        public void onFaceUp() {
        }

        @Override
        public void onFaceDown() {
        }

        /**
         * Activity销毁事件
         */
        public void onActivityDestroy() {
            Util.logd(this.getClass().getSimpleName() + "." + "onActivityDestroy");

            mPlayer.stop();
            mPlayer.release();
        }

        /**
         * 切换状态
         * 
         * @param stateClass
         */
        final protected void nextState(Class<? extends State> stateClass) {
            if (mStatePool.containsKey(stateClass)) {
                mState = mStatePool.get(stateClass);
                mState.onActivate();
                return;
            }

            try {
                Constructor<? extends State> constructor = stateClass
                        .getConstructor(MainActivity.class);
                mState = constructor.newInstance(MainActivity.this);
                mStatePool.put(stateClass, mState);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 更新播放进度显示
         */
        protected void updateProgress() {
            mPlayProgress.setText(mPlayer.getProgressString());
            mPlayDuration.setText(mPlayer.getDurationString());
        }
    }

    public class ReadyState extends State {

        @Override
        public void onActivate() {
            mPlayButton.setImageResource(R.drawable.ic_play_big);
            // TODO: play ring
        }

        @Override
        public void onSelectItem(AudioNewsItem item) {
            super.onSelectItem(item);
            mPlayer.setDataSource(MainActivity.this, item.getAudioResource());
            updateProgress();
            nextState(PausingState.class);
        }
    }

    public class PlayingState extends State {
        public void onActivate() {
            mPlayButton.setImageResource(R.drawable.ic_pause_big);
        }

        @Override
        public void onClickPlayButton() {
            super.onClickPlayButton();

            mPlayer.pause();
            nextState(PausingState.class);
        }

        @Override
        public void onProgressChange() {
            super.onProgressChange();
            updateProgress();
        }

        @Override
        public void onSelectItem(AudioNewsItem item) {
            if (item == mCurrentItem) {
                mPlayer.pause();
                nextState(PausingState.class);
            } else {
                super.onSelectItem(item);
                mPlayer.setDataSource(MainActivity.this, item.getAudioResource());
                mPlayer.start();
            }
        }

        @Override
        public void onShake() {
            super.onShake();
            playNext();
        }

        @Override
        public void onFaceDown() {
            super.onFaceDown();
            mPlayer.pause();
            nextState(FaceDownPausingState.class);
        }
    }

    public class PausingState extends State {
        public void onActivate() {
            mPlayButton.setImageResource(R.drawable.ic_play_big);
        }

        @Override
        public void onClickPlayButton() {
            super.onClickPlayButton();
            mPlayer.start();
            nextState(PlayingState.class);
        }

        @Override
        public void onSelectItem(AudioNewsItem item) {
            if (item != mCurrentItem) {
                // 只有不一样时才需要重新加载文件
                mPlayer.setDataSource(MainActivity.this, item.getAudioResource());
            }

            super.onSelectItem(item);
            mPlayer.start();
            nextState(PlayingState.class);
        }
    }

    public class FaceDownPausingState extends PausingState {
        @Override
        public void onFaceUp() {
            super.onFaceUp();
            mPlayer.start();
            nextState(PlayingState.class);
        }
    }

    private State mState;
    private List<AudioNewsItem> mItems;
    private AudioNewsItem mCurrentItem;
    private MediaManager mPlayer;
    private ShakeListener mShakeListener;

    private TextView mTitleView;
    private TextView mDescriptionView;
    private ImageView mPlayButton;
    private TextView mPlayProgress;
    private TextView mPlayDuration;
    private ListView mAudioList;
    private AudioListAdapter mAudioListAdapter;

    private Handler mHandler;

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

        mState = new ReadyState();
        if (mItems.size() > 0) {
            mState.onSelectItem(mItems.get(0));
        }
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
    }

    private void initData() {
        mHandler = new Handler();

        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(this);

        mPlayer = MediaManager.getInstance();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnProgressChangeListener(this);

        mItems = new ArrayList<AudioNewsItem>(AppLauncher.getNewsItems());
    }

    public void playNext() {
        playNext(true);
    }

    /**
     * 播放下一首
     * 
     * @param loop 播放到最后一道时是否循环
     */
    public void playNext(boolean loop) {
        int nextIndex = mItems.indexOf(mCurrentItem) + 1;
        if (loop) {
            nextIndex %= mItems.size();
        }

        if (nextIndex < mItems.size()) {
            mState.onSelectItem(mItems.get(nextIndex));
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mState.onSelectItem((AudioNewsItem) view.getTag());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeListener.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeListener.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mState.onActivityDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_alarm:
                AlarmListActivity.startActvity(this);
                break;
            case R.id.imv_settings:
                SettingsActivity.startActivity(this);
                break;
            default:
                break;
        }

        if (view == mPlayButton) {
            mState.onClickPlayButton();
        }
    }

    @Override
    public void onProgressChange(int currentPosition, int duration) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mState.onProgressChange();
            }
        });
    }

    @Override
    public void onShake() {
        mState.onShake();
    }

    @Override
    public void onFaceUp() {
        mState.onFaceUp();
    }

    @Override
    public void onFaceDown() {
        mState.onFaceDown();
    }
}
