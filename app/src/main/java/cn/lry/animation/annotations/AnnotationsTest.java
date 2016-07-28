package cn.lry.animation.annotations;

import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.Keep;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * ClassName: AnnotationsTest
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/7/1 15:02
 */
public class AnnotationsTest {

    @CallSuper
    protected void testCallSuper() {

    }

    public void testStringRes(@StringRes int res) {

    }

    public void testLayoutRes(@LayoutRes int res){

    }

    public void testDrawableRes(@DrawableRes int res){

    }

    public void testNonNull(@NonNull String s) {

    }

    @Keep
    public void testNullable(@Nullable String s) {

    }

    @CheckResult
    public String testCheckResult() {
        return "test";
    }
}
