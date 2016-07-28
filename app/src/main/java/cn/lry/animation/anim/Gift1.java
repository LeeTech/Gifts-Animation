package cn.lry.animation.anim;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import cn.lry.animation.R;

/**
 * ClassName: Gift1
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/20 17:09
 */
public class Gift1 implements GiftAnimationble {

    private Context mContext;

    private ImageView t1_icon1;

    private ImageView t1_icon2;

    private ImageView t1_fixed;

    private ImageView t1_next;

    private AnimationDrawable t1_icon1_animationDrawable;

    private Animation animationTop;
    private Animation animationBottom;

    private View view;

    public Gift1(Context context) {
        this.mContext = context;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_tutorial_1, null);
        t1_icon1 = (ImageView) view.findViewById(R.id.t1_icon1);
        t1_icon2 = (ImageView) view.findViewById(R.id.t1_icon2);
        t1_fixed = (ImageView) view.findViewById(R.id.t1_fixed);
        t1_next = (ImageView) view.findViewById(R.id.t1_next);
    }

    @Override
    public View getAnimationView() {
        return view;
    }

    @Override
    public void startAnimation() {
        t1_icon1.setImageResource(R.drawable.t1_frame_animation);
        t1_icon1_animationDrawable = (AnimationDrawable) t1_icon1.getDrawable();
        t1_icon1_animationDrawable.start();

        Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.tutorail_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        animation1.setInterpolator(lin);
        t1_icon2.setVisibility(View.VISIBLE);
        t1_icon2.startAnimation(animation1);

        animationTop = AnimationUtils.loadAnimation(mContext, R.anim.tutorail_scalate_top);
        animationBottom = AnimationUtils.loadAnimation(mContext, R.anim.tutorail_bottom);
        t1_fixed.startAnimation(animationTop);
        t1_next.startAnimation(animationBottom);
    }

    @Override
    public void stopAnimation() {
        if (t1_icon1_animationDrawable != null) {
            t1_icon1_animationDrawable.stop();
        }
        if (t1_icon2.getAnimation() != null) {
            t1_icon2.getAnimation().cancel();
        }
        if (t1_fixed.getAnimation() != null) {
            t1_fixed.getAnimation().cancel();
        }
        if (t1_next.getAnimation() != null) {
            t1_next.getAnimation().cancel();
        }
    }
}
