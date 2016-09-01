# Gifts-Animation
直播送礼物连击效果

1.Step
```
 <cn.lry.animation.guide.LeftGiftControlLayout
        android:id="@+id/giftLl"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:gravity="center_vertical"
        android:orientation="vertical">


        <cn.lry.animation.guide.LeftGiftsItemLayout
            android:id="@+id/gift1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:visibility="invisible"
            app:left_gift_layout_index="0" />

        <cn.lry.animation.guide.LeftGiftsItemLayout
            android:id="@+id/gift2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="6dp"
            android:visibility="invisible"
            app:left_gift_layout_index="1" />
    </cn.lry.animation.guide.LeftGiftControlLayout>
    ```
