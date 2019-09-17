package com.hexg.guideview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.hexg.guideview.util.L;

public class GuideView extends FrameLayout {
    private RectF mRectF;
    private Path path;
    private Paint paint;

    public GuideView(@NonNull Context context, RectF rectF) {
        super(context);
        // ViewGroup默认是不绘制的，因此这里要绘制的话，需要将开关打开。
        setWillNotDraw(false);
        mRectF = rectF;
        init();
    }

    private void init() {
        path = new Path();

        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRectF == null) return;

        path.moveTo(mRectF.left, mRectF.top);
        path.addRect(mRectF, Path.Direction.CW);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
//        canvas.drawRect(0f, 0f, getRight(), getBottom(), paint);
        canvas.drawColor(L.INSTANCE.GetResources().getColor(R.color.light_black));
    }
}
