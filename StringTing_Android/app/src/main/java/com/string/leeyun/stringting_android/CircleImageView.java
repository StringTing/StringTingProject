package com.string.leeyun.stringting_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by sg970 on 2017-11-14.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void Update(){
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

/*그라데이션 테두링

        Paint p = new Paint();

        LinearGradient s = new LinearGradient("ff2577","f433958");
        p.setColor(0xffff0000);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        Rect r = canvas.getClipBounds();
        canvas.drawRect(r,p);
*/



        Path clipPath = new Path();

        final float halfWidth = canvas.getWidth() / 2;
        final float halfHeight = canvas.getHeight() / 2;
        float radius = Math.max(halfWidth, halfHeight);

        clipPath.addCircle(halfWidth, halfHeight, radius, Path.Direction.CW);

        canvas.clipPath(clipPath);

        //Log.w("onDraw", "call~~~~~~~~~~~~~~");

        super.onDraw(canvas);
    }
}

