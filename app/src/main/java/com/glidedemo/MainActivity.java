package com.glidedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.normal_iv)
    ImageView normalIv;
    @BindView(R.id.avatar_iv)
    ImageView avatarIv;
    @BindView(R.id.gif_iv)
    ImageView gifIv;
    @BindView(R.id.blur_iv)
    ImageView blurIv;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
    }

    /**
     * 普通加载
     */
    public void normalLoad(View view) {
        GlideLoader.getInstance().loadImage(mContext, Constant.IMAGE_URL, normalIv);
    }

    /**
     * 加载头像
     */
    public void avatarLoad(View view) {
        GlideLoader.getInstance().loadImage(mContext, Constant.IMAGE_URL, avatarIv, GlideLoader.LoadOption.LOAD_AVATAR);
    }

    /**
     * 加载GIF
     */
    public void gifLoad(View view) {
        GlideLoader.getInstance().loadImage(mContext, Constant.GIF_URL, gifIv, GlideLoader.LoadOption.LOAD_GIF);
    }

    /**
     * 加载毛玻璃
     */
    public void blurLoad(View view) {
        GlideLoader.getInstance().loadImage(mContext, Constant.IMAGE_URL, blurIv, GlideLoader.LoadOption.LOAD_BLUR);
    }

    /**
     * 清除缓存
     */
    public void clear(View view) {
        GlideLoader.getInstance().clearMemory(mContext);
    }
}
