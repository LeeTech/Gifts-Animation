package cn.lry.animation.anim;

import android.view.View;

/**
 * ClassName: GiftAnimationble
 * Description TODO Animation Interface
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/20 16:55
 */
public interface GiftAnimationble {

    View getAnimationView();

    void startAnimation();

    void stopAnimation();

    abstract class Factory<T> {

        /**
         * 用户生成礼物动画
         *
         * @return
         */
        public abstract T getGiftAnimationView();

        /**
         * 动画显示时间
         *
         * @return
         */
        public abstract long getGiftShowingTime();

        /**
         * 礼物ID
         *
         * @return
         */
        public abstract String getGiftId();

        /**
         * 礼物名称
         *
         * @return
         */
        public abstract String getGiftName();
    }
}
