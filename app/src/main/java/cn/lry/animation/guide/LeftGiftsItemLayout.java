package cn.lry.animation.guide;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cn.lry.animation.R;
import cn.lry.animation.control.GiftModel;
import cn.lry.animation.utils.BusProvider;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ClassName: LeftGiftsLayout
 * Description TODO 左侧礼物子布局
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/23 10:23
 */
public class LeftGiftsItemLayout extends LinearLayout implements View.OnClickListener, Handler.Callback {

    private static final String TAG = "LeftGiftsItem";

    private ImageView mHeadIv;

    private TextView mNickNameTv;

    private TextView mInfoTv;

    private ImageView mGiftsIv;

    private volatile GiftModel mGift;

    private MagicTextView mCountTv;

    /**
     * 等待中
     */
    public static final int WAITING = 0;

    /**
     * 正在显示
     */
    public static final int SHOWING = 1;

    /**
     * 显示结束或者未显示
     */
    public static final int SHOWEND = 2;

    /**
     * 礼物展示时间
     */
    public static final int GIFT_DISMISS_TIME = 5000;

    /**
     * 当前该layout显示状态
     */
    private int mCurrentShowStatus = WAITING;

    /**
     * item 显示位置
     */
    private int mIndex = 1;

    /**
     * 礼物平移动画
     */
    private TranslateAnimation mGiftAnimation;

    /**
     * 连击动画
     */
    private ScaleAnimation mGiftNumAnim;//修改礼物数量的动画

    private LeftGiftAnimationStatusListener mGiftAnimationListener;

    private Handler mHandler = new Handler(this);

    /**
     * 礼物连击数
     */
    private int mGiftCount;

    /**
     * 当前动画runnable
     */
    private Runnable mCurrentAnimRunnable;

    /**
     * 当前播放连击数
     */
    private int mNum = 0;

    /**
     * 实时监测礼物数量
     */
    private Subscription mSubscribe;

    public LeftGiftsItemLayout(Context context) {
        super(context);
        initLayout(context, null);
    }

    public LeftGiftsItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.left_gift_item_layout, null);
        mHeadIv = (ImageView) contentView.findViewById(R.id.headIv);
        mNickNameTv = (TextView) contentView.findViewById(R.id.nickNameTv);
        mInfoTv = (TextView) contentView.findViewById(R.id.infoTv);
        mGiftsIv = (ImageView) contentView.findViewById(R.id.giftIv);
        mCountTv = (MagicTextView) contentView.findViewById(R.id.numberTv);

        mGiftAnimation = new TranslateAnimation(-300, 0, 0, 0);
        mGiftAnimation.setDuration(800);
        mGiftAnimation.setFillAfter(true);

        mGiftNumAnim = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.gift_num);
        mGiftNumAnim.setAnimationListener(new GiftAnimationListener());

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(
                    attrs, R.styleable.LeftGiftsItemLayout, 0, 0);
            mIndex = typedArray.getInteger(R.styleable.LeftGiftsItemLayout_left_gift_layout_index, 1);
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(contentView, lp);
        mHeadIv.setOnClickListener(this);

        mGiftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCountTv.startAnimation(mGiftNumAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(GiftModel data) {
        this.mGift = data;
        mNum = 1;
        mGiftCount = this.mGift.getGiftCuont();
        mNickNameTv.setText(this.mGift.getSendUserName());
        mInfoTv.setText("送了一个" + this.mGift.getGiftName());
        mCountTv.setText("X" + mNum);
    }

    /**
     * 开始动画
     */
    public void startGiftAnimation() {
        mGiftsIv.startAnimation(mGiftAnimation);
        mCurrentShowStatus = SHOWING;
    }

    private class GiftNumAnimaRunnable implements Runnable {

        @Override
        public void run() {
            dismissGiftLayout();
        }
    }

    /**
     * 显示完连击数与动画时,关闭此Item Layout,并通知外部隐藏自身(供内部调用)
     */
    private void dismissGiftLayout() {
        mCurrentShowStatus = SHOWEND;
        mGiftsIv.setVisibility(View.INVISIBLE);
        if (mGiftAnimationListener != null) {
            mGiftAnimationListener.dismiss(mIndex);
        }
        removeDismissGiftCallback();
    }

    private void removeDismissGiftCallback() {
        if (mCurrentAnimRunnable != null) {
            mHandler.removeCallbacks(mCurrentAnimRunnable);
            mCurrentAnimRunnable = null;
            stopCheckGiftCount();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case RESTARTGIFTANIMATION_CODE:
                mNum++;
                mCountTv.setText("X" + mNum);
                mCountTv.startAnimation(mGiftNumAnim);
                stopCheckGiftCount();
                removeDismissGiftCallback();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 连击数动画监听
     */
    private class GiftAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            mGiftsIv.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mGiftCount > mNum) {
                mHandler.sendEmptyMessage(RESTARTGIFTANIMATION_CODE);
            } else {
                checkGiftCountSubscribe();
                mCurrentAnimRunnable = new GiftNumAnimaRunnable();
                mHandler.postDelayed(mCurrentAnimRunnable, GIFT_DISMISS_TIME);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    public interface LeftGiftAnimationStatusListener {
        void dismiss(int index);
    }

    private static final int RESTARTGIFTANIMATION_CODE = 1002;


    private void checkGiftCountSubscribe() {
        if (mSubscribe == null || mSubscribe.isUnsubscribed()) {
            mSubscribe = Observable.interval(300, TimeUnit.MILLISECONDS).map(new Func1<Long, Void>() {
                @Override
                public Void call(Long aLong) {
                    return null;
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (mGiftCount > mNum) {
                                mHandler.sendEmptyMessage(RESTARTGIFTANIMATION_CODE);
                            }
                        }
                    });

        }
    }

    private void stopCheckGiftCount() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    /**
     * 获取当前显示礼物发送人id
     *
     * @return
     */
    public String getCurrentSendUserId() {
        if (mGift != null) {
            return mGift.getSendUserId();
        }
        return null;
    }

    /**
     * 获取当前显示礼物id
     *
     * @return
     */
    public String getCurrentGiftId() {
        if (mGift != null) {
            return mGift.getGiftId();
        }
        return null;
    }

    /**
     * 增加礼物数量,用于连击效果
     *
     * @param count
     */
    public synchronized void setGiftCount(int count) {
        mGiftCount += count;
    }

    /**
     * 设置item显示位置
     *
     * @param mIndex
     */
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    /**
     * 获取ite显示位置
     *
     * @return
     */
    public int getIndex() {
        Log.i(TAG, "index : " + mIndex);
        return mIndex;
    }

    /**
     * 获取该layout当前显示状态
     *
     * @return
     */
    public int getCurrentShowStatus() {
        return mCurrentShowStatus;
    }

    public void setCurrentShowStatus(int status) {
        this.mCurrentShowStatus = status;
    }

    public void setGiftAnimationListener(LeftGiftAnimationStatusListener giftAnimationListener) {
        this.mGiftAnimationListener = giftAnimationListener;
    }

    @Override
    public void onClick(View v) {
        BusProvider.getInstance().post("gift_by_user_action", this.mGift);
    }
}
