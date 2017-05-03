package com.glidedemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 初始化数据及服务
 *
 * @author: xiaoluo
 * @date: 2016-12-20 17:53
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    private static Context mAppContext;
    private static BaseApplication instance;

    // 记录当前栈里所有的activity
    private List<Activity> mAllActivity = new ArrayList<Activity>();

    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();

        // 注册Crash捕捉上报系统
        CrashHandler.getInstance().init(mAppContext);

        // 获取屏幕宽高
        getScreen();
    }


    /**
     * 获取屏幕宽高
     */
    private void getScreen() {
        Point outSize = new Point();
        WindowManager windowManager = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(outSize);
        Constant.WIDTH_OF_SCREEN = outSize.x;
        Constant.HEIGHT_OF_SCREEN = outSize.y;

    }

    // 解决65K方法限制
    // 如果有自己的Application，继承MulitDexApplication。
    // 如果当前代码已经继承自其它Application没办法修改那也行，就重写 Application的attachBaseContext()这个方法。
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    /**
     * 获取AppContext
     */
    public static Context getAppContext() {
        return mAppContext;
    }

    /**
     * 新开了activity
     */
    public void addActivity(Activity activity) {
        mAllActivity.add(activity);
    }

}
