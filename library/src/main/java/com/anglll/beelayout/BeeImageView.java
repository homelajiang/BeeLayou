package com.anglll.beelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.util.ArrayList;

public class BeeImageView extends AppCompatImageView {
    private ArrayList<Point> centPoints = new ArrayList<>();
    private ArrayList<ArrayList<Point>> blockList = new ArrayList<>();
    private Paint paint = new Paint();
    private int width;
    private int height;
    private final Paint zonePaint = new Paint();
    private RectF roundRect = new RectF();

    public BeeImageView(Context context) {
        super(context);
        init(context, null);
    }

    public BeeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BeeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        //	        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(0xFFD0D0D0);

        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int parentTrueSize = Math.min(parentHeight, parentWidth);
        int parentSize = Math.min(MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom());
        int childMaxSize = (int) (parentSize / (2 * Math.sin(60 * Math.PI / 180) + 1));
        centPoints.clear();
        blockList.clear();
        int R = childMaxSize / 2;
        for (int i = 0; i < 7; i++) {
            int x;
            int y;
            if (i == 6) {
                centPoints.add(new Point(parentTrueSize / 2, parentTrueSize / 2));
                x = parentTrueSize / 2;
                y = parentTrueSize / 2;
            } else {
                centPoints.add(new Point(
                        (int) (parentTrueSize / 2 + childMaxSize * Math.sin(60 * Math.PI / 180) * Math.cos(i * 60 * Math.PI / 180)),
                        (int) (parentTrueSize / 2 - childMaxSize * Math.sin(60 * Math.PI / 180) * Math.sin(i * 60 * Math.PI / 180))
                ));
                x = (int) (parentTrueSize / 2 + childMaxSize * Math.sin(60 * Math.PI / 180) * Math.cos(i * 60 * Math.PI / 180));
                y = (int) (parentTrueSize / 2 - childMaxSize * Math.sin(60 * Math.PI / 180) * Math.sin(i * 60 * Math.PI / 180));
            }
            ArrayList<Point> temp = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                temp.add(new Point((int) (R * Math.cos((30 + j * 60) * Math.PI / 180) + x),
                        (int) (R * Math.sin((30 + j * 60) * Math.PI / 180) + y)));
            }
            blockList.add(temp);
        }
        setMeasuredDimension(parentTrueSize, parentTrueSize);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Path p = new Path();
/*        for (int i = 0; i < blockList.size(); i++) {
            Path path = new Path();
            for (int j = 0; j < blockList.get(i).size(); j++) {
                if (j == 0) {
                    path.moveTo(blockList.get(i).get(j).x,
                            blockList.get(i).get(j).y);
                } else {
                    path.lineTo(blockList.get(i).get(j).x,
                            blockList.get(i).get(j).y);
                }
            }
            path.close();
            p.addPath(path);
        }*/
        int save = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight()
                , null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);


        p.moveTo(0, 0);
        p.lineTo(400, 0);
        p.lineTo(300, 400);
        p.close();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(p, paint);
//        super.dispatchDraw(canvas);

        paint.setXfermode(null);
        canvas.restoreToCount(save);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        roundRect.set(0, 0, width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        Path p = new Path();
        for (int i = 0; i < blockList.size(); i++) {
            Path path = new Path();
            for (int j = 0; j < blockList.get(i).size(); j++) {
                if (j == 0) {
                    path.moveTo(blockList.get(i).get(j).x,
                            blockList.get(i).get(j).y);
                } else {
                    path.lineTo(blockList.get(i).get(j).x,
                            blockList.get(i).get(j).y);
                }
            }
            path.close();
            p.addPath(path);
        }
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawPath(p,zonePaint);
        canvas.saveLayer(roundRect,paint,Canvas.ALL_SAVE_FLAG);
//        canvas.saveLayer()
        super.draw(canvas);
        canvas.restore();
    }
}
