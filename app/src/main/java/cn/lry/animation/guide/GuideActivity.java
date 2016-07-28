package cn.lry.animation.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import cn.lry.animation.R;
import cn.lry.animation.arcanimator.ArcAnimator;
import cn.lry.animation.arcanimator.Side;
import cn.lry.animation.utils.DensityUtil;
import cn.lry.animation.viewpager.CirclePageIndicator;
import cn.lry.animation.viewpager.PagerAdapter;

/**
 * Created by liruiyuan on 2015/12/22.
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager vp;
    private List<View> views;
    private ViewPagerAdapter vpAdapter;
    private View view1, view2, view3;
    private int preIndex = 0;
    private boolean isPause;

    private ImageView page_one_start, page_one_center_tj, page_one_planeline, page_one_rocket, page_one_center_bg, page_one_text,
            page_one_leftcloud, page_one_rightcloud;
    private Animation animationTop, animationCenterTJ, animationPlaneline, animationText, animationLeftcloud, animationRightcloud;
    private ArcAnimator animationRocket;
    private View center;
    private PageOneAnimationListener pageOneAnimationListener;
    private PageTwoAnimationListener pageTwoAnimationListener;
    private PageThreeAnimationListener pageThreeAnimationListener;
    private float rocketX, rocketY, centerX, centerY;

    private ImageView page_two_new_feature, page_two_bu1, page_two_bu2,page_two_bu3,page_two_bu4,page_two_text;
    private Animation animationCenter, animationBu1, animationBu2,animationBu3,animationBu4, animationText2;

    private ImageView page_three_center_hand, page_three_redpkg1, page_three_redpkg2, page_three_redpkg3, page_three_text, page_three_go;
    private Animation animationCenterHand, animationRedP1, animationRedP2, animationRedP3, animationText3, animationGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        isPause = false;
        initPageOne();
        initPageTwo();
        pageThreeInit();
    }

    public void init() {
        setContentView(R.layout.activity_guide);
        views = new ArrayList<>();
        view1 = LayoutInflater.from(this).inflate(R.layout.layout_guide_one, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.layout_guide_two, null);
        view3 = LayoutInflater.from(this).inflate(R.layout.layout_guide_three, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        vpAdapter = new ViewPagerAdapter(views);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(this);
        CirclePageIndicator indy_guid = (CirclePageIndicator) findViewById(R.id.indy_guid);
        indy_guid.setViewPager(vp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    private void initPageOne(){
        pageOneAnimationListener = new PageOneAnimationListener();
        center = view1.findViewById(R.id.center);
        page_one_start = (ImageView) view1.findViewById(R.id.page_one_start);

        page_one_center_tj = (ImageView) view1.findViewById(R.id.page_one_center_tj);
        page_one_planeline = (ImageView) view1.findViewById(R.id.page_one_planeline);
        page_one_center_bg = (ImageView) view1.findViewById(R.id.page_one_center_bg);
        page_one_text = (ImageView) view1.findViewById(R.id.page_one_text);
        page_one_rocket = (ImageView) view1.findViewById(R.id.page_one_rocket);
        page_one_leftcloud = (ImageView) view1.findViewById(R.id.page_one_leftcloud);
        page_one_rightcloud = (ImageView) view1.findViewById(R.id.page_one_rightcloud);

        animationTop = AnimationUtils.loadAnimation(this, R.anim.tutorail_scalate_top);
        animationCenterTJ = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationCenterTJ.setAnimationListener(pageOneAnimationListener);
        animationPlaneline = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationPlaneline.setAnimationListener(pageOneAnimationListener);

        int[] location = new int[2];
        DensityUtil densityUtil = new DensityUtil(this);
        page_one_text.getLocationOnScreen(location);
        float fromXDelta = location[0];
        float toYDelta = location[1];
        animationText = new TranslateAnimation(fromXDelta,fromXDelta,densityUtil.getScreenHeight(),toYDelta);
        animationText.setDuration(1000);
        animationText.setFillAfter(true);

        page_one_text.getLocationOnScreen(location);
        fromXDelta = location[0];
        float fromYDelta = location[1];
        animationLeftcloud = new TranslateAnimation(fromXDelta,fromXDelta + 120,fromYDelta,fromYDelta);
        animationLeftcloud.setDuration(800);
        animationLeftcloud.setRepeatCount(-1);
        animationLeftcloud.setRepeatMode(Animation.REVERSE);

        animationRightcloud = new TranslateAnimation(fromXDelta,fromXDelta + 60,fromYDelta,fromYDelta);
        animationRightcloud.setDuration(1200);
        animationRightcloud.setRepeatCount(-1);
        animationRightcloud.setRepeatMode(Animation.REVERSE);

    }

    private void initPageTwo(){
        pageTwoAnimationListener = new PageTwoAnimationListener();
        page_two_new_feature = (ImageView) view2.findViewById(R.id.page_two_new_feature);
        page_two_bu1 = (ImageView) view2.findViewById(R.id.page_two_bu1);
        page_two_bu2 = (ImageView) view2.findViewById(R.id.page_two_bu2);
        page_two_bu3 = (ImageView) view2.findViewById(R.id.page_two_bu3);
        page_two_bu4 = (ImageView) view2.findViewById(R.id.page_two_bu4);
        page_two_text = (ImageView) view2.findViewById(R.id.page_two_text);

        animationCenter = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationCenter.setAnimationListener(pageTwoAnimationListener);

        animationBu1 = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationBu1.setAnimationListener(pageTwoAnimationListener);
        animationBu2 = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationBu2.setAnimationListener(pageTwoAnimationListener);
        animationBu3 = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationBu3.setAnimationListener(pageTwoAnimationListener);
        animationBu4 = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationBu4.setAnimationListener(pageTwoAnimationListener);

        int[] location = new int[2];
        DensityUtil densityUtil = new DensityUtil(this);
        page_two_text.getLocationOnScreen(location);
        float fromXDelta = location[0];
        float toYDelta = location[1];
        animationText2 = new TranslateAnimation(fromXDelta,fromXDelta,densityUtil.getScreenHeight(),toYDelta);
        animationText2.setDuration(1000);
        animationText2.setFillAfter(true);
    }

    private void pageThreeInit(){
        pageThreeAnimationListener = new PageThreeAnimationListener();
        page_three_center_hand = (ImageView) view3.findViewById(R.id.page_three_center_hand);
        page_three_redpkg1 = (ImageView) view3.findViewById(R.id.page_three_redpkg1);
        page_three_redpkg2 = (ImageView) view3.findViewById(R.id.page_three_redpkg2);
        page_three_redpkg3 = (ImageView) view3.findViewById(R.id.page_three_redpkg3);
        page_three_text = (ImageView) view3.findViewById(R.id.page_three_text);
        page_three_go = (ImageView) view3.findViewById(R.id.page_three_go);

        animationGo = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_common);
        animationGo.setAnimationListener(pageThreeAnimationListener);
        animationRedP1 = AnimationUtils.loadAnimation(this, R.anim.tutorail_rotate_redp1);
        animationRedP2 = AnimationUtils.loadAnimation(this, R.anim.tutorail_alpha_redp2);
        animationCenterHand = AnimationUtils.loadAnimation(this, R.anim.tutorail_rotate_hand);

        int[] location = new int[2];
        DensityUtil densityUtil = new DensityUtil(this);
        page_three_text.getLocationOnScreen(location);
        float fromXDelta = location[0];
        float toYDelta = location[1];
        animationText3 = new TranslateAnimation(fromXDelta,fromXDelta,densityUtil.getScreenHeight(),toYDelta);
        animationText3.setDuration(1000);
        animationText3.setFillAfter(true);
        animationText3.setAnimationListener(pageThreeAnimationListener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isPause){
            isPause = false;
        } else {
            if (0 == preIndex){
                if (0 == rocketX){
                    int[] location = new int[2];
                    page_one_rocket.getLocationInWindow(location);
                    rocketX = location[0];
                    rocketY = location[1];

                    center.getLocationOnScreen(location);
                    centerX = location[0];
                    centerY = location[1];

                    pageOneStart();
                }
            }
        }

    }

    private class PageOneAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == animationCenterTJ){
                page_one_center_tj.setVisibility(View.VISIBLE);
                page_one_planeline.startAnimation(animationPlaneline);
            } else if(animation == animationPlaneline){
                page_one_planeline.setVisibility(View.VISIBLE);
            } else if(animation == animationText){
                page_one_text.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class PageTwoAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == animationCenter){
                page_two_new_feature.setVisibility(View.VISIBLE);
                page_two_bu1.startAnimation(animationBu1);
            }else if (animation == animationBu1){
                page_two_bu1.setVisibility(View.VISIBLE);
                page_two_bu2.startAnimation(animationBu2);
            }else if (animation == animationBu2){
                page_two_bu2.setVisibility(View.VISIBLE);
                page_two_bu3.startAnimation(animationBu3);
            }else if (animation == animationBu3){
                page_two_bu3.setVisibility(View.VISIBLE);
                page_two_bu4.startAnimation(animationBu4);
            }else if (animation == animationBu4){
                page_two_bu4.setVisibility(View.VISIBLE);
            }else if (animation == animationText2){
                page_two_text.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class PageThreeAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == animationText3){
                page_three_text.setVisibility(View.VISIBLE);
                page_three_go.startAnimation(animationGo);
            }else if (animation == animationGo){
                page_three_go.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private void pageOneStart(){

        page_one_start.startAnimation(animationTop);
        page_one_text.startAnimation(animationText);
        page_one_leftcloud.startAnimation(animationLeftcloud);
        page_one_rightcloud.startAnimation(animationRightcloud);

        float r = page_one_center_bg.getHeight();
        float x = page_one_rocket.getHeight();

        animationRocket = ArcAnimator.createArcAnimator(page_one_rocket, centerX + r - x*3/2 + 20, centerY - r + 3*x/4 - 20 , 90f, Side.RIGHT);
        animationRocket.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                page_one_center_tj.startAnimation(animationCenterTJ);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animationRocket.setDuration(1500);
        animationRocket.start();
    }

    private void pageOneStop(){
        page_one_center_tj.setVisibility(View.INVISIBLE);
        page_one_planeline.setVisibility(View.INVISIBLE);
        page_one_text.setVisibility(View.INVISIBLE);

        animationTop.cancel();
        animationLeftcloud.cancel();
        animationRightcloud.cancel();
        if (null != animationRocket)
            animationRocket.cancel();

        page_one_rocket.setX(rocketX);
        page_one_rocket.setY(rocketY);
    }

    private void pageOneDestroy(){
        animationTop.cancel();
        animationCenterTJ.cancel();
        animationPlaneline.cancel();
        animationText.cancel();
        animationLeftcloud.cancel();
        animationRightcloud.cancel();
    }

    private void pageTwoStart(){
        page_two_new_feature.setVisibility(View.INVISIBLE);
        page_two_bu1.setVisibility(View.INVISIBLE);
        page_two_bu2.setVisibility(View.INVISIBLE);
        page_two_bu3.setVisibility(View.INVISIBLE);
        page_two_bu4.setVisibility(View.INVISIBLE);
        page_two_text.setVisibility(View.INVISIBLE);

        page_two_text.startAnimation(animationText2);
        page_two_new_feature.startAnimation(animationCenter);
    }

    private void pageThreeStart(){
        page_three_text.setVisibility(View.INVISIBLE);
        page_three_go.setVisibility(View.INVISIBLE);

        page_three_text.startAnimation(animationText3);
        page_three_center_hand.startAnimation(animationCenterHand);
        page_three_redpkg1.startAnimation(animationRedP1);
        page_three_redpkg2.startAnimation(animationRedP2);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int arg1, Object arg2) {
            viewGroup.removeView(views.get(arg1));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int arg1) {
            viewGroup.addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int page) {
        switch (page){
            case 0:
                pageOneStart();
                break;
            case 1:
                if (preIndex < page){
                    pageOneStop();
                }
                pageTwoStart();
                break;
            case 2:
                pageThreeStart();
                break;
        }
        preIndex = page;
    }

    @Override
    protected void onDestroy() {
        if (0 == preIndex){
            pageOneDestroy();
        }
        super.onDestroy();
    }

}
