package cn.lry.animation.factory;

import android.content.Context;

import cn.lry.animation.anim.Gift2;
import cn.lry.animation.anim.GiftAnimationble;

/**
 * ClassName: Gift2Factory
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/21 11:12
 */
public class Gift2Factory extends GiftAnimationble.Factory<Gift2> {

    private Context mContext;

    public static Gift2Factory create(Context context) {
        return new Gift2Factory(context);
    }

    public Gift2Factory(Context context) {
        this.mContext = context;
    }

    @Override
    public Gift2 getGiftAnimationView() {
        return new Gift2(this.mContext);
    }

    /**
     * 默认礼物显示3秒
     *
     * @return
     */
    @Override
    public long getGiftShowingTime() {
        return 10000;
    }

    @Override
    public String getGiftId() {
        return "124";
    }

    @Override
    public String getGiftName() {
        return "Gift2";
    }
}
