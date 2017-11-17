package com.example.alex.coordinatedemo.base.permissions;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.List;

import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 10:48.
 * @email: youguyilin@126.com
 */

public class PermissionHelper {

    public  static boolean isM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 处理是否有权限被永久拒绝
     * @param activity
     * @param deniedPermissions 被拒绝的权限（包括被永久拒绝的权限，和只被拒绝一次的权限），处理完之后变成只被拒绝一次的权限
     * @param permanentPermissions 永久被拒绝的权限，（勾选了不再提醒）
     * @return 如果有权限被永久拒绝，返回true，否则返回false
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean handlePermanentlyDenied(@Nullable Activity activity,
                                                  @Nullable List<String> deniedPermissions,
                                                  List<String> permanentPermissions){
        for (String deniedPermission : deniedPermissions){
            if(permissionPermanentlyDenied(activity,deniedPermission)){
                permanentPermissions.add(deniedPermission);
                deniedPermissions.remove(deniedPermission);
            }
        }
        return !permanentPermissions.isEmpty();
    }

    /**
     * 判断权限是否被永久拒绝
     * @param activity
     * @param deniedPermission
     * @return
     */
    public static boolean permissionPermanentlyDenied(@Nullable Activity activity,
                                                      @Nullable String deniedPermission){
         return !shouldShowRequestPermissionRationale(activity,deniedPermission);
    }

}
