package cn.lry.animation.control;

import android.content.Context;
import android.graphics.Color;
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

    public static int[] mNumColorArray;

    public static int[] mRoundColorArray;

    public static int[][] mTextShapeColors;

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

        mNumColorArray = new int[]{3, 5, 10, 15};

        mRoundColorArray = new int[]{
                R.drawable.progress_background_shape,
                R.drawable.progress_yellow_background_shape,
                R.drawable.progress_purple_background_shape,
                R.drawable.progress_blue_background_shape};

        mTextShapeColors = new int[][]{
                {Color.parseColor("#F6E04C"), Color.parseColor("#F6E04C"), Color.parseColor("#FE8A18")},
                {Color.parseColor("#26E3F7"), Color.parseColor("#26E3F7"), Color.parseColor("#08A4FD")},
                {Color.parseColor("#B6EA01"), Color.parseColor("#B6EA01"), Color.parseColor("#66CC00")},
                {Color.parseColor("#FE89A8"), Color.parseColor("#FE89A8"), Color.parseColor("#FB4472")}
        };


        if (mNumColorArray.length != mRoundColorArray.length) {
            throw new IllegalArgumentException("The number of array must be consistent with the length of the color set");
        }
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
