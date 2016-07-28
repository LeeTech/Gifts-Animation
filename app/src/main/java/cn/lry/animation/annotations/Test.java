package cn.lry.animation.annotations;

import cn.lry.animation.R;

/**
 * ClassName: Test
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/7/1 15:05
 */
public class Test extends AnnotationsTest {

    @Override
    protected void testCallSuper() {
        super.testCallSuper();
    }

    public void test() {
        testStringRes(R.string.app_name);
        testLayoutRes(R.layout.activity_gifts);
        testDrawableRes(R.mipmap.githublogo);

        String s = "ssssssss";
        testNonNull(s);
        testNullable(null);

        String ss = testCheckResult();
    }
}