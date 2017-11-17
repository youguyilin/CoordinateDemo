package com.example.alex.coordinatedemo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.alex.coordinatedemo.base.mvp.IBasePresenter;
import com.example.alex.coordinatedemo.base.permissions.PermissionHelper;
import com.example.alex.coordinatedemo.base.permissions.PermissionListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;


/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 13:50.
 * @email: youguyilin@126.com
 */

public abstract class BaseMvpActivity<P extends IBasePresenter> extends FragmentActivity {
    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    public static final int REQUEST_CODE = 1;

    protected Context mContext;
    protected P mPresenter;

    protected static final String DEFALUT_PARCEABLE_NAME = "DEFAULT_PARCEABLE_NAME";
    protected static final String DEFALUT_PARCEABLE_LIST_NAME = "DEFAULT_PARCEABLE_LIST_NAME";

    private static final String TAG = "YinGen";

    protected String className = getClass().getSimpleName();

    protected ProgressDialog mDialog;

    private static PermissionListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
        initWindow();
        //base setup
        mContext = this;
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            mPresenter = getPresenter();
            if (mPresenter != null) {
                mPresenter.onCreate();
            }
            initView();
            initData(savedInstanceState);
        } else {
            Logger.t(TAG).w("BaseActivity" + "onCreate: Error contentView");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
        if (mDialog != null) {
            mDialog.cancel();
            mDialog.dismiss();
        }

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        ;
    }

    /**
     * 在setContentView前，初始化window设置
     */
    private void initWindow() {

    }

    /**
     * 获取presenter
     *
     * @return
     */
    protected abstract P getPresenter();

    /**
     * 获取layout的ID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    protected abstract void initView();

    protected void initLisenter() {
    }

    protected void initData(Bundle saveInstanceState) {
    }

    public void setOnclickLisenter(View.OnClickListener lisenter, @IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(lisenter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: +" + className);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: +" + className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: +" + className);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: +" + className);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: +" + className);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: +" + className);
    }

    public static void requestPermissions(String[] permissions, PermissionListener listener) {
        //获取 topActivity 支持静态方法
        Activity top = ActivityCollector.getTop();
        if (top == null) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        mPermissionListener = listener;
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(top, permission) != PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(top, permissions, REQUEST_CODE);
            }
            list.add(permission);
        }
        if (list.isEmpty()) {
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        list.add(permission);
                    }
                }
                //权限全部被允许
                if (list.isEmpty()) {
                    mPermissionListener.onGranted();
                } else {
                    //有被拒绝的权限
                    ArrayList<String> permanentDeniedPermissions = new ArrayList<>();
                    if (PermissionHelper.isM() && PermissionHelper.handlePermanentlyDenied(this, list, permanentDeniedPermissions)) {
                        mPermissionListener.onParmanentDenied(permanentDeniedPermissions);
                    }
                    if (!list.isEmpty()) {
                        mPermissionListener.onDenied(list);
                    }
                }

                break;
        }
    }

    protected <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    protected void showFragment(int containId, Fragment from, Fragment to, String tag) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (!to.isAdded()) {//先判断fragment是否add过
            if (tag != null) {
                transaction.hide(from).add(containId, to, tag);
            } else {
                transaction.hide(from).add(containId, to);
            }
            //隐藏当前的fragment，add下一个到Activity中
        } else {
            //隐藏当前fragment，显示下一个
            transaction.hide(from).show(to);
        }
        transaction.commit();
    }

    protected void replaceFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment).commit();
    }

    /**
     * 启动Activity
     */
    public void readyGo(Class<?> tClass) {
        Intent intent = new Intent(this, tClass);
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable) {
        this.readyGo(clazz, DEFALUT_PARCEABLE_NAME, parcelable);
    }

    public void readyGo(Class<?> clazz, String name, Parcelable parcelable) {
        Intent intent = new Intent(this, clazz);
        if (parcelable != null) {
            intent.putExtra(name, parcelable);
        }
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable, String action) {
        Intent intent = new Intent(this, clazz);
        intent.setAction(action);
        if (null != parcelable) {
            intent.putExtra(DEFALUT_PARCEABLE_NAME, parcelable);
        }
        startActivity(intent);
    }

    public void readyGo(Class<? extends Activity> clazz, ArrayList<? extends Parcelable> parcelableList) {

    }

    public void readyGo(Class<? extends Activity> clazz, String name, ArrayList<? extends Parcelable> parcelableList) {
        Intent intent = new Intent(this, clazz);
        if (null != parcelableList) {
            intent.putExtra(name, parcelableList);
        }
        startActivity(intent);
    }

    /**
     * 启动activity 传递数据
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        this.finish();
    }

    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.show();
    }

    public void showProgressDialog(String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.setMessage(msg);
        mDialog.show();
    }

    public void hideProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 根据字符串弹出toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据资源id串弹出toast
     *
     * @param msg
     */
    protected void showToast(int msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

