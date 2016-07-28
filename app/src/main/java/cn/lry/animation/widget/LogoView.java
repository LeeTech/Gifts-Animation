package cn.lry.animation.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import cn.lry.animation.R;

/**
 * ClassName: LogoView
 * Description TODO logo view
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/29 11:29
 */
public class LogoView extends View {

    protected static final ArgbEvaluator ARGB_EVAL = new ArgbEvaluator();

    /**
     * logo 背景画布
     */
    private Canvas mBackgroundCanvas;

    /**
     * 背景图
     */
    private Bitmap mBackground;

    /**
     * 参照图
     */
    private Bitmap mMask;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 绘制形状
     */
    protected Rect mDrawingRect;

    /**
     * 是否转换
     */
    protected boolean mDirection;

    protected long mLastChange;

    /**
     * 颜色转换时间
     */
    protected int mDuration;


    public LogoView(Context context) {
        super(context);
        init();
    }

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDuration = 2000;

        mMask = BitmapFactory.decodeResource(getResources(),
                R.mipmap.githublogo);

        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);

        mBackgroundCanvas = new Canvas();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawingRect = new Rect(0, 0, w, h);

        final Bitmap mask = convertToAlphaMask(Bitmap.createScaledBitmap(mMask,
                w, h, false));
        mPaint.setShader(new BitmapShader(mask, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP));

        mBackground = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mBackgroundCanvas.setBitmap(mBackground);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final long time = SystemClock.uptimeMillis();
        final int diff = (int) (time - mLastChange);
        if (time - mLastChange > mDuration) {
            mDirection = !mDirection;
            mLastChange = time;
        }

        final float percent = (mDirection ? diff % mDuration : mDuration
                - (diff % mDuration))
                / (float) mDuration;

        mPaint.setColor((Integer) ARGB_EVAL.evaluate(percent, 0xffffffff,
                0xffff8800));

        // Draw the masked logo in the new color
        mBackgroundCanvas.drawRect(mDrawingRect, mPaint);

        // Draw the new logo
        canvas.drawBitmap(mBackground, 0, 0, mPaint);

        //不断去绘制
        invalidate();
    }

    protected static Bitmap convertToAlphaMask(Bitmap b) {
        final Bitmap a = Bitmap.createBitmap(b.getWidth(), b.getHeight(),
                Bitmap.Config.ALPHA_8);
        final Canvas c = new Canvas(a);
        c.drawBitmap(b, 0.0f, 0.0f, null);
        return a;
    }
}
