package cn.lry.animation.guide;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import cn.lry.animation.R;
import cn.lry.animation.anim.GiftAnimationble;
import cn.lry.animation.control.GiftsControl;

public class GiftDisPlayLayout extends RelativeLayout implements OnClickListener, Handler.Callback {

    private GiftsControl giftsControl;

    Context c;
    LayoutParams lp;
    Handler handler;

    private static final int SHOWING = 1;

    private static final int SHOWEND = 2;

    private int showStatus = SHOWEND;

    public GiftDisPlayLayout(Context context) {
        super(context);
        c = context;
        initAfterConstructor();
        init();
    }

    public GiftDisPlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        parserAttrs(context, attrs, 0, 0);
        initAfterConstructor();
        init();
    }

    public GiftDisPlayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        c = context;
        parserAttrs(context, attrs, defStyleAttr, 0);
        initAfterConstructor();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GiftDisPlayLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        c = context;
        parserAttrs(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
        init();
    }

    private void parserAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.GiftDisPlayLayout, defStyleAttr, defStyleRes);
        frameColor = a.getColor(R.styleable.GiftDisPlayLayout_rfal_frame_color, getContext().getResources().getColor(R.color.rfab__color_frame));
        frameAlpha = a.getFloat(R.styleable.GiftDisPlayLayout_rfal_frame_alpha,
                Float.valueOf(getResources().getString(R.string.rfab_rfal__float_convert_color_alpha))
        );

        frameAlpha = frameAlpha > 1f ? 1f : (frameAlpha < 0f ? 0f : frameAlpha);

        a.recycle();

    }

    public static final long ANIMATION_DURATION = 150/*ms*/;

    private void initAfterConstructor() {

    }

    private View fillFrameView;
    private int frameColor;
    private float frameAlpha;

    private GiftAnimationble currentShowingGit;

    private Runnable mCrrentShowRunnable;

    public GiftDisPlayLayout init() {
        giftsControl = GiftsControl.getInstance();
        // 添加背景覆盖层
        fillFrameView = new View(getContext());
        fillFrameView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fillFrameView.setBackgroundColor(frameColor);
        fillFrameView.setVisibility(GONE);
        fillFrameView.setOnClickListener(this);
        this.addView(fillFrameView, Math.max(this.getChildCount() - 1, 0));
        handler = new Handler(this);

        // 添加内容
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (fillFrameView == v) {
            collapseContent();
        }
    }

    public void onPause() {
        giftsControl.cleanAll();
        if (currentShowingGit != null) {
            currentShowingGit.stopAnimation();
            currentShowingGit = null;
        }
        if (mCrrentShowRunnable != null) {
            handler.removeCallbacks(mCrrentShowRunnable);
            mCrrentShowRunnable = null;
        }
        collapseContent();
        removeShowGiftView();
        showStatus = SHOWEND;
    }

    @Override
    public boolean handleMessage(Message msg) {
        currentShowingGit = (GiftAnimationble) msg.obj;
        if (currentShowingGit != null) {
            currentShowingGit.stopAnimation();
            currentShowingGit = null;
        }
        if (mCrrentShowRunnable != null) {
            handler.removeCallbacks(mCrrentShowRunnable);
            mCrrentShowRunnable = null;
        }
        collapseContent();
        removeShowGiftView();
        showStatus = SHOWEND;
        showGift();
        return true;
    }

    private void removeShowGiftView() {
        View giftView = getChildAt(1);
        if (giftView != null) {
            removeViewAt(1);
        }
    }


    public void loadGift(GiftAnimationble.Factory<GiftAnimationble> factory) {
        if (factory != null) {
            giftsControl.loadGift(factory);
        }
    }

    public void showGift() {

        if (giftsControl.isEmpty()) {
            showStatus = SHOWEND;
            return;
        }

        if (showStatus == SHOWEND) {
            expandContent();
            GiftAnimationble.Factory<GiftAnimationble> factory = giftsControl.get();
            GiftAnimationble git = factory.getGiftAnimationView();
            addView(git.getAnimationView(), 1, lp);
            git.startAnimation();
            mCrrentShowRunnable = new ShowGiftsRunnable(git);
            handler.postDelayed(mCrrentShowRunnable, factory.getGiftShowingTime());
            showStatus = SHOWING;
        }
    }

    /**
     * 显示礼物 Runnable
     */
    private class ShowGiftsRunnable implements Runnable {

        private GiftAnimationble git;

        public ShowGiftsRunnable(GiftAnimationble git) {
            this.git = git;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.obj = git;
            handler.sendMessage(message);
        }
    }

    public void setFrameColor(int frameColor) {
        this.frameColor = frameColor;
        if (null != fillFrameView) {
            fillFrameView.setBackgroundColor(frameColor);
        }
    }

    public void setFrameAlpha(float frameAlpha) {
        this.frameAlpha = frameAlpha;
    }

    private boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void toggleContent() {
        if (isExpanded) {
            collapseContent();
        } else {
            expandContent();
        }
    }

    private AnimatorSet animatorSet;

    public void expandContent() {
        if (isExpanded) {
            return;
        }
        endAnimatorSet();
        isExpanded = true;

        fillFrameAnimator.setTarget(this.fillFrameView);
        fillFrameAnimator.setFloatValues(0.0f, frameAlpha);
        fillFrameAnimator.setPropertyName("alpha");

        animatorSet = new AnimatorSet();
        contentAnimator.setTarget(this.fillFrameView);
        contentAnimator.setFloatValues(0.0f, 1.0f);
        contentAnimator.setPropertyName("alpha");
        animatorSet.playTogether(contentAnimator, fillFrameAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(mAccelerateInterpolator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fillFrameView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isExpanded = true;
            }
        });

        animatorSet.start();
    }

    public void collapseContent() {
        if (!isExpanded) {
            return;
        }
        endAnimatorSet();
        isExpanded = false;

        fillFrameAnimator.setTarget(this.fillFrameView);
        fillFrameAnimator.setFloatValues(frameAlpha, 0.0f);
        fillFrameAnimator.setPropertyName("alpha");

        animatorSet = new AnimatorSet();
        contentAnimator.setTarget(this.fillFrameView);
        contentAnimator.setFloatValues(1.0f, 0.0f);
        contentAnimator.setPropertyName("alpha");
        animatorSet.playTogether(contentAnimator, fillFrameAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(mAccelerateInterpolator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fillFrameView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fillFrameView.setVisibility(GONE);
                isExpanded = false;
            }
        });
        animatorSet.start();

    }

    private void endAnimatorSet() {
        if (null != animatorSet) {
            animatorSet.end();
        }
    }

    private ObjectAnimator contentAnimator = new ObjectAnimator();
    private ObjectAnimator fillFrameAnimator = new ObjectAnimator();
    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();

}
