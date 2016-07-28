package cn.lry.animation.control;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lry.animation.R;
import cn.lry.animation.anim.GiftAnimationble;
import cn.lry.animation.factory.Gift1Factory;
import cn.lry.animation.factory.Gift2Factory;

/**
 * ClassName: GiftManage
 * Description TODO 所有礼物集合
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/20 18:09
 */
public class GiftManage {

    private static List<GiftAnimationble.Factory> mGiftList;

    private static HashMap<String, Integer> mLeftGiftMap;

    /**
     * 用户配置礼物,如有新的礼物加入,add到集合即可(必须先初始化)
     *
     * @param context
     */
    public static void init(Context context) {
        // 大礼物
        mGiftList = new ArrayList<>();
        mGiftList.add(Gift1Factory.create(context));
        mGiftList.add(Gift2Factory.create(context));

        // 左侧小礼物
        mLeftGiftMap = new HashMap<>();
        mLeftGiftMap.put("123", R.mipmap.ic_launcher);
    }

    public static int getGiftRes(String giftId) {
        int giftRes = 0;
        if (!TextUtils.isEmpty(giftId)) {
            return mLeftGiftMap.get(giftId);
        }
        return giftRes;
    }

    public static GiftAnimationble.Factory getGiftById(String giftId) {
        if (!TextUtils.isEmpty(giftId)) {
            if (mGiftList != null && mGiftList.size() > 0) {
                for (GiftAnimationble.Factory factory : mGiftList) {
                    if (factory.getGiftId().equals(giftId)) {
                        return factory;
                    }
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }
}
