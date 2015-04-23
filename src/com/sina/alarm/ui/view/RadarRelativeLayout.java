package com.sina.alarm.ui.view;

import com.sina.alarm.R;
import com.sina.alarm.util.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RadarRelativeLayout extends RelativeLayout {

    private int startAngle = 0;
    private int currentAngle = 0;
    private Paint paint;

    public RadarRelativeLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public RadarRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public RadarRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
    }
    
    @Override
    public void draw(Canvas canvas) {
        Util.logd("draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Util.logd("ondraw");
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        width = height = Math.min(width, height);

        RectF rect = new RectF(0, 0, width, height);

        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.play_button_backing_color));
        }

        canvas.drawArc(rect, startAngle, currentAngle, true, paint);
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setAngle(int angle) {
        this.currentAngle = angle;
        invalidate();
    }

    public void setAngle(int fromAngle, int toAngle) {
        this.startAngle = fromAngle;
        this.currentAngle = toAngle;
        this.requestLayout();
    }

}
