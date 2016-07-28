package cn.lry.animation.anim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import cn.lry.animation.R;

/**
 * ClassName: Gift2
 * Description TODO 礼物2
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/21 11:04
 */
public class Gift2 implements GiftAnimationble {

    private ImageView t2_icon1, t2_fixed, t2_next;

    private Animation animationTop;
    private Animation animationBottom;

    private Context mContext;

    private View view2;

    public Gift2(Context context) {
        this.mContext = context;
        this.view2 = LayoutInflater.from(this.mContext).inflate(R.layout.layout_tutorial_2, null);
        t2_icon1 = (ImageView) view2.findViewById(R.id.t2_icon1);
        t2_fixed = (ImageView) view2.findViewById(R.id.t2_fixed);
        t2_next = (ImageView) view2.findViewById(R.id.t2_next);
    }

    @Override
    public View getAnimationView() {
        return this.view2;
    }

    @Override
    public void startAnimation() {
        Animation animation2 = AnimationUtils.loadAnimation(this.mContext, R.anim.tutorail_scalate);
        animationTop = AnimationUtils.loadAnimation(this.mContext, R.anim.tutorail_scalate_top);
        animationBottom = AnimationUtils.loadAnimation(this.mContext, R.anim.tutorail_bottom);
        t2_icon1.setVisibility(View.VISIBLE);
        t2_icon1.startAnimation(animation2);

        t2_fixed.startAnimation(animationTop);
        t2_next.startAnimation(animationBottom);
    }

    @Override
    public void stopAnimation() {
        if (t2_icon1.getAnimation() != null) {
            t2_icon1.getAnimation().cancel();
        }
        if (t2_fixed.getAnimation() != null) {
            t2_fixed.getAnimation().cancel();
        }
        if (t2_next.getAnimation() != null) {
            t2_next.getAnimation().cancel();
        }
    }
}
