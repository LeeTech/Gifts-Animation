package cn.lry.animation.guide;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import cn.lry.animation.R;

public class ShowViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private TextureView textureView;

    private Camera mCamra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_view);
        textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);
    }

    // 可用状态
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamra = Camera.open();
        Camera.Size size = mCamra.getParameters().getPreviewSize();
        textureView.setLayoutParams(new RelativeLayout.LayoutParams(size.width, size.height));
        try {
            mCamra.setPreviewTexture(surface);
            mCamra.startPreview();
            textureView.setAlpha(1.0f);
            textureView.setRotation(90.0f);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ShowViewActivity.this, "视频初始化有误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamra.stopPreview();
        mCamra.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
