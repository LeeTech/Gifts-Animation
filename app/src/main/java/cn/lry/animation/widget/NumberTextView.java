package cn.lry.animation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.lry.animation.R;

/**
 * ClassName: NumberTextView
 * Description TODO 數字拼接TextView
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/9/22 18:10
 */
public class NumberTextView extends LinearLayout {

    private LinearLayout mNumberLl;

    private LayoutParams mLayoutParams;

    public NumberTextView(Context context) {
        super(context);
        init();
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.number_calculator_layout, this);
        mNumberLl = (LinearLayout) findViewById(R.id.numberLl);
        mLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = -8;
    }

    public void updataNumber(String number) {
        mNumberLl.removeAllViews();
        for (int i = 0; i < number.length(); i++) {
            mNumberLl.addView(getTimerImage(number.charAt(i)));
        }
    }

    private ImageView getTimerImage(char number) {
        ImageView image = new ImageView(getContext());
        switch (number) {
            case '0':
                image.setImageResource(R.mipmap.number_0);
                break;
            case '1':
                image.setImageResource(R.mipmap.number_1);
                break;
            case '2':
                image.setImageResource(R.mipmap.number_2);
                break;
            case '3':
                image.setImageResource(R.mipmap.number_3);
                break;
            case '4':
                image.setImageResource(R.mipmap.number_4);
                break;
            case '5':
                image.setImageResource(R.mipmap.number_5);
                break;
            case '6':
                image.setImageResource(R.mipmap.number_6);
                break;
            case '7':
                image.setImageResource(R.mipmap.number_7);
                break;
            case '8':
                image.setImageResource(R.mipmap.number_8);
                break;
            case '9':
                image.setImageResource(R.mipmap.number_9);
                break;
        }
        image.setLayoutParams(mLayoutParams);
        return image;
    }
}
