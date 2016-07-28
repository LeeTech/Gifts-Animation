package cn.lry.animation.control;

import java.util.Vector;

import cn.lry.animation.anim.GiftAnimationble;
import cn.lry.animation.utils.BusProvider;

/**
 * ClassName: GiftsControl
 * Description TODO 礼物持有者
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/20 17:52
 */
public class GiftsControl {

    private static GiftsControl instance = new GiftsControl();

    private Vector<GiftAnimationble.Factory<GiftAnimationble>> mGiftList;

    private GiftsControl() {
        mGiftList = new Vector<>();
    }

    public static synchronized GiftsControl getInstance() {
        if (instance == null) {
            instance = new GiftsControl();
        }
        return instance;
    }

    /**
     * 加入礼物
     *
     * @param factory
     */
    public synchronized void loadGift(GiftAnimationble.Factory<GiftAnimationble> factory) {
        if (mGiftList != null) {
            mGiftList.add(factory);
            BusProvider.getInstance().post("gifts_changed_action", mGiftList.size());
        }
    }

    /**
     * 取出礼物
     *
     * @return
     */
    public synchronized GiftAnimationble.Factory<GiftAnimationble> get() {
        GiftAnimationble.Factory<GiftAnimationble> factory = null;
        if (mGiftList.size() != 0) {
            factory = mGiftList.get(0);
            mGiftList.remove(0);
        }
        return factory;
    }

    /**
     * 清除所有礼物
     */
    public synchronized void cleanAll() {
        if (mGiftList != null) {
            mGiftList.clear();
        }
    }

    /**
     * 礼物是否为空
     *
     * @return
     */
    public synchronized boolean isEmpty() {
        if (mGiftList == null || mGiftList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
