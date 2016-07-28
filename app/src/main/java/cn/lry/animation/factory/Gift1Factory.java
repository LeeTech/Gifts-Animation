package cn.lry.animation.factory;

import android.content.Context;

import cn.lry.animation.anim.Gift1;
import cn.lry.animation.anim.GiftAnimationble;

/**
 * ClassName: Gift1Factory
 * Description TODO Gift1礼物生成工厂
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/20 17:29
 */
public final class Gift1Factory extends GiftAnimationble.Factory<Gift1> {

    private Context mContext;

    public static Gift1Factory create(Context context) {
        return new Gift1Factory(context);
    }

    public Gift1Factory(Context context) {
        this.mContext = context;
    }

    @Override
    public Gift1 getGiftAnimationView() {
        return new Gift1(this.mContext);
    }

    /**
     * 默认礼物显示3秒
     *
     * @return
     */
    @Override
    public long getGiftShowingTime() {
        return 3000;
    }

    @Override
    public String getGiftId() {
        return "123";
    }

    @Override
    public String getGiftName() {
        return "Gift1";
    }
}
