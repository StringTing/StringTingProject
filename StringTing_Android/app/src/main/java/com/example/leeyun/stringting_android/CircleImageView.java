package com.example.leeyun.stringting_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
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

