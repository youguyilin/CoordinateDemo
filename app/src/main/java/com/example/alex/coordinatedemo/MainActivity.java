package com.example.alex.coordinatedemo;

import android.Manifest;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.example.alex.coordinatedemo.activity.ToolBarSnapActivity;
import com.example.alex.coordinatedemo.base.BaseMvpActivity;
import com.example.alex.coordinatedemo.base.mvp.IBasePresenter;
import com.example.alex.coordinatedemo.base.permissions.PermissionListener;

import java.util.List;

public class MainActivity extends BaseMvpActivity {
    private static final String TAG = "YinGen";

    String[] mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                decorView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Rect rect = new Rect();
                //getWindow().getDecorView()获取到window中的最顶层view，可以从window中获取到该view
                //decorView通过getwindowVisiableDisplayFrame()方法可以获取到程序显示的区域。
                //包括标题栏，但不包括状态栏
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //获取状态栏的高度
                int statusBarHight = rect.top;
                //获取内容布局
                getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                //获取内容高度
                int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
                int titleBarHigh = contentTop - statusBarHight;
                Log.i(TAG, "onGlobalLayout: titleBarHigh=" + titleBarHigh);
                Log.i(TAG, "onGlobalLayout: statusBarHight=" + statusBarHight);
            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        super.initData(saveInstanceState);
        requestPermissions(mPermissions, new PermissionListener() {
            @Override
            public void onGranted() {
                showToast("授权成功！！！");
            }

            @Override
            public void onDenied(List<String> permissions) {
                showToast("授权失败！！！");
            }

            @Override
            public void onParmanentDenied(List<String> parmanentDeniedPer) {
                showToast("权限被永久拒绝！！！");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e(TAG, "屏幕高度：" + dm.heightPixels);

        //应用区域
        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        Log.e(TAG,"应用区顶部："+ outRect.top);
        Log.e(TAG,"应用区高："+ outRect.height());
    }

    public void onClickBtn(View view){
      switch (view.getId()){
          case R.id.toolbar_snap:
              readyGo(ToolBarSnapActivity.class);
              break;
      }
    }
}
