package cn.lry.animation.guide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import cn.lry.animation.R;
import cn.lry.animation.control.GiftManage;
import cn.lry.animation.control.GiftModel;
import cn.lry.animation.utils.BusProvider;

public class GiftsActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private GiftDisPlayLayout floatingActionsMenu;

    private LeftGiftControlLayout giftLl;

    private MagicTextView magicTextView;

    private int count = 1;

    private ScaleAnimation giftNumAnim;//修改礼物数量的动画

    private RoundProgressBar mRoundPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        floatingActionsMenu = (GiftDisPlayLayout) findViewById(R.id.right_labels);
        giftLl = (LeftGiftControlLayout) findViewById(R.id.giftLl);
        magicTextView = (MagicTextView) findViewById(R.id.gift_num);
        giftNumAnim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.gift_num);
        mRoundPb = (RoundProgressBar) findViewById(R.id.roundPb);
        mRoundPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoundPb.resetProgress();
            }
        });

        GiftManage.init(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.loadGift(GiftManage.getGiftById("123"));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                floatingActionsMenu.loadGift(GiftManage.getGiftById("124"));
                giftLl.loadGift(GiftModel.create("125", "安卓机器人", 3, "http://join.com", "123", "Lee125", "http://join.com"));
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giftLl.loadGift(GiftModel.create("123", "安卓机器人", 1, "http://join.com", "123", "Lee123", "http://join.com"));
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;
//                magicTextView.setText("X" + count);
//                magicTextView.startAnimation(giftNumAnim);
                giftLl.loadGift(GiftModel.create("124", "安卓机器人", 1314, "http://join.com", "123", "Lee124", "http://join.com"));

            }
        });


        BusProvider.getInstance().register(this);
    }

    @Subscribe(
            tags = {@Tag("gifts_changed_action")}
    )
    public void giftDataChanged(Integer size) {
        Log.d(TAG, "gifts size : " + String.valueOf(size));
        floatingActionsMenu.showGift();
    }


    @Subscribe(
            tags = {@Tag("gift_by_user_action")}
    )
    public void onGiftClick(GiftModel gift) {
        Toast.makeText(GiftsActivity.this, "点击了礼物", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (floatingActionsMenu != null) {
            floatingActionsMenu.onPause();
        }

        if (giftLl != null) {
            giftLl.cleanAll();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

}
