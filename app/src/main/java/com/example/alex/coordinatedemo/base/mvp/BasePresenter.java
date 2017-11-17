package com.example.alex.coordinatedemo.base.mvp;

import java.lang.ref.SoftReference;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/16 14:02.
 * @email: youguyilin@126.com
 */

public class BasePresenter<V extends IBaseView>implements IBasePresenter {
    protected final SoftReference<V> mReference;

    public BasePresenter(V view) {
        this.mReference = new SoftReference<>(view);
    }

    @Override
    public void onDestroy() {
        mReference.clear();
    }

    @Override
    public void onCreate() {

    }

    public V getView(){
        return  mReference.get();
    }
}
