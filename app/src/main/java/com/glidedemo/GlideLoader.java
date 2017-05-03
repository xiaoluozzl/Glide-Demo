package com.glidedemo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.glidedemo.GlideLoader.LoadOption.LOAD_ORIGIN;

/**
 * Glide加载工具
 *
 * author: xiaoluo
 * date: 2017/5/3 11:36
 */
public class GlideLoader {
    private final static String TAG = GlideLoader.class.getSimpleName();

    private static GlideLoader glideInstance;
    ;

    /**
     * 单例模式
     */
    public static GlideLoader getInstance() {
        if (glideInstance == null) {
            glideInstance = new GlideLoader();
        }
        return glideInstance;
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        initLoadImage(Glide.with(context), context, url, imageView, LOAD_ORIGIN);
    }

    public void loadImage(Context context, String url, ImageView imageView, LoadOption option) {
        initLoadImage(Glide.with(context), context, url, imageView, option);
    }

    /**
     * 统一加载方法
     * 采用RequestManager, 可以和Actvity生命周期同步
     */
    private void initLoadImage(RequestManager glide, final Context context, String url, final ImageView imageView, LoadOption option) {
        // You cannot start a load for a destroyed activity
        // 子线程加载图片, context不用appcontext时,在activity结束后会报此错,如果不采用ReqeustManager, 可以再加个判断

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
//                return;
//            }
//        }
        switch (option) {
            case LOAD_ORIGIN:
                glide.load(url)
                        .fitCenter()
                        .error(R.mipmap.delete)
                        .into(imageView);
                break;
            case LOAD_MEDIUM:
                glide.load(url)
                        .override(500, 500)
                        .error(R.mipmap.delete)
                        .into(imageView);
                break;
            case LOAD_SMALL:
                glide.load(url)
                        .override(300, 300)
                        .error(R.mipmap.delete)
                        .into(imageView);
                break;
            case LOAD_AVATAR:
                // 原生Glide加载圆形头像
//                glide.load(url)
//                        .asBitmap()
//                        .centerCrop()
//                        .override(300, 300)
//                        .error(R.mipmap.delete)
//                        .into(new BitmapImageViewTarget(imageView) {
//                            @Override
//                            protected void setResource(Bitmap resource) {
//                                RoundedBitmapDrawable circularBitmapDrawable =
//                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                                circularBitmapDrawable.setCircular(true);
//                                imageView.setImageDrawable(circularBitmapDrawable);
//                            }
//                        });
                // 采用glide-transformations加载圆形头像
                glide
                        .load(url)
                        .centerCrop()
                        .override(200, 200)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(imageView);
                break;
            case LOAD_GIF:
                glide.load(url)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
                break;
            case LOAD_BLUR:
                glide.load(url)
                        .bitmapTransform(new BlurTransformation(context, 15))
                        .into(imageView);
                break;
        }
    }

    /**
     * 清理缓存
     */
    public void clearMemory(final Context context) {
        Glide.get(context).clearMemory();  // 在UI线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();  // 在子线程执行
            }
        }).start();
    }

    public enum LoadOption {
        LOAD_ORIGIN, // 原图
        LOAD_MEDIUM, // 中图
        LOAD_SMALL,  // 小图
        LOAD_AVATAR, // 头像
        LOAD_GIF,    // GIF
        LOAD_BLUR    // 毛玻璃
    }
}
