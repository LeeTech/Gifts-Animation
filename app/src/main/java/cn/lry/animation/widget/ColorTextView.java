package cn.lry.animation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ClassName: ColorTextView
 * Description TODO 渐变文本颜色
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/9/19 17:12
 */
public class ColorTextView extends TextView {

    private LinearGradient mLinearGradient;

    private Paint mPaint;

    private static final int[] DEFAULT_SHAPE_COLORS = new int[]{
            Color.parseColor("#ff0099cc"), Color.parseColor("#ff0099cc")
    };

    private int mViewHeight = 0;

    private Rect mTextBound = new Rect();

    public ColorTextView(Context context) {
        super(context);
        init();
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = getPaint();
        resetShapeColors();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewHeight = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

    public void setTextShaderColors(int[] colors) {
        mLinearGradient = new LinearGradient(0, 0, 0, mViewHeight, colors, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        invalidate();
    }

    public void resetShapeColors() {
        mLinearGradient = new LinearGradient(0, 0, 0, mViewHeight, DEFAULT_SHAPE_COLORS, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        invalidate();
    }
}
