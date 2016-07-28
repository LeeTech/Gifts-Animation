package cn.lry.animation.guide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

import cn.lry.animation.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ClassName: RoundProgressBar
 * Description TODO 圆角进度条
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/24 18:45
 */
public class RoundProgressBar extends LinearLayout {

    /**
     * 最大进度
     */
    private float mMax = 100;

    /**
     * 当前进度
     */
    private float mProgress = 0;

    /**
     * 控件总宽度
     */
    private int mTotalWidth;

    private LinearLayout mLayoutProgress;

    private ImageView mProIv;

    private RelativeLayout mContentRl;

    private AlphaAnimation mAlphaAnimation;

    private Subscription mSubscribe;

    public RoundProgressBar(Context context) {
        super(context);
        initLayout(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.progress_bar_layout, this);
        mLayoutProgress = (LinearLayout) findViewById(R.id.layout_progress);
        mProIv = (ImageView) findViewById(R.id.proIv);
        mContentRl = (RelativeLayout) findViewById(R.id.contentRl);
        mAlphaAnimation = new AlphaAnimation(1.0f, 0f);
        mAlphaAnimation.setDuration(1000);
    }

    /**
     * 绘制进度条
     * b
     *
     * @param layoutProgress
     * @param max
     * @param progress
     * @param totalWidth
     */
    private void drawProgress(LinearLayout layoutProgress, float max, float progress, int totalWidth) {
        float ratio = max / progress;
        int progressWidth = (int) (totalWidth / ratio);
        ViewGroup.LayoutParams progressParams = layoutProgress.getLayoutParams();
        progressParams.width = progressWidth;
        layoutProgress.setLayoutParams(progressParams);
    }

    private void drawTextProgressMargin(ImageView proIv, float max, float progress, int totalWidth) {
        float ratio = max / progress;
        int progressWidth = (int) (totalWidth / ratio);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) proIv.getLayoutParams();
        params.setMargins(progressWidth - DemoApp.screenWidth / 4, 0, 0, 0);
        proIv.setLayoutParams(params);
    }

    private void startProgress() {
        if (mSubscribe == null || mSubscribe.isUnsubscribed()) {
            mSubscribe = Observable.interval(50, TimeUnit.MILLISECONDS).map(new Func1<Long, Void>() {
                @Override
                public Void call(Long aLong) {
                    if (mProgress < 100) {
                        mProgress += 12;
                    }
                    return null;
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            drawProgress(mLayoutProgress, mMax, mProgress, mTotalWidth);
                            drawTextProgressMargin(mProIv, mMax, mProgress, mTotalWidth);
                            if (mProgress > 100) {
                                mContentRl.startAnimation(mAlphaAnimation);
                            }
                        }
                    });
        }
    }


    private void stopProgress() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isInEditMode()) {
            mTotalWidth = w;
            drawProgress(mLayoutProgress, mMax, 0, mTotalWidth);
            startProgress();
        }
    }

}
