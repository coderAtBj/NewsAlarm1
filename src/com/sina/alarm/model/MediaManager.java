package com.sina.alarm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class MediaManager {

    public static interface OnProgressChangeListener {
        public void onProgressChange(int currentPosition, int duration);
    }

    private static MediaManager instance;

    public static MediaManager getInstance() {
        if (instance == null) {
            synchronized (MediaManager.class) {
                if (instance == null) {
                    instance = new MediaManager();
                }
            }
        }

        return instance;
    }

    public final static int PROGRESS_CHANGE_INTERVAL = 500;
    public final static SimpleDateFormat PLAY_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    {
        PLAY_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    }

    public static String formatPlayTime(int time) {
        return PLAY_TIME_FORMAT.format(new Date(time));
    }

    private MediaPlayer player;
    private boolean isPausing;
    private boolean isPrepared = false;

    private OnProgressChangeListener progressListener;
    private Timer timer;
    private TimerTask timerTask;

    private MediaManager() {
        player = new MediaPlayer();
    }

    private void startTimer() {
        stopTimer();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (progressListener == null || !isPrepared()) {
                    return;
                }
                try {
                    progressListener.onProgressChange(player.getCurrentPosition(),
                            player.getDuration());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(timerTask, 0, PROGRESS_CHANGE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    public void setDataSource(Context context, int resId) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("android.resource");
        builder.authority(context.getPackageName());
        builder.appendPath(Integer.toString(resId));

        setDataSource(context, builder.build());
    }

    public MediaManager setDataSource(Context context, Uri uri) {
        try {
            if (isPrepared) {
                player.stop();
                player.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            player.setDataSource(context, uri);
            player.prepare();
            isPrepared = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        this.isPrepared = prepared;
    }

    public boolean isPlaying() {
        if (!isPrepared()) {
            return false;
        }

        return player.isPlaying();
    }

    public boolean isPausing() {
        return isPausing;
    }

    public MediaManager start() {
        if (player.isPlaying()) {
            player.stop();
        }

        player.start();
        isPausing = false;

        return this;
    }

    public MediaManager pause() {
        player.pause();
        isPausing = true;

        return this;
    }

    public MediaManager stop() {
        if (isPrepared()) {
            player.stop();
        }

        stopTimer();

        return this;
    }

    public MediaManager reset() {
        if (isPrepared()) {
            player.reset();
            setPrepared(false);
        }

        stopTimer();

        return this;
    }

    public MediaManager release() {
        reset();

        if (isPrepared()) {
            player.release();
            setPrepared(false);
        }

        return this;
    }

    public int getProgress() {
        if (!isPrepared()) {
            return 0;
        }

        return player.getCurrentPosition();
    }

    public String getProgressString() {
        return formatPlayTime(getProgress());
    }

    public int getDuration() {
        if (!isPrepared()) {
            return 0;
        }

        return player.getDuration();
    }

    public String getDurationString() {
        return formatPlayTime(getDuration());
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        if (listener != null) {
            startTimer();
        } else {
            stopTimer();
        }

        this.progressListener = listener;
    }
}
