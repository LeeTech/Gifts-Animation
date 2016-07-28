package cn.lry.animation.guide;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * ClassName: DemoApp
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/7/19 17:25
 */
public class DemoApp extends Application {

    public static int screenHeight = 0;
    public static int screenWidth = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }
}
