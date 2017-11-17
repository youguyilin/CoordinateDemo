package com.example.alex.coordinatedemo.base.permissions;

import java.util.List;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 10:45.
 * @email: youguyilin@126.com
 */

public interface PermissionListener {
    void onGranted();
    void onDenied(List<String> permissions);
    void onParmanentDenied(List<String> parmanentDeniedPer);
}
