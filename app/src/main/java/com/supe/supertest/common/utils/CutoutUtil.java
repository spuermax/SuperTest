package com.supe.supertest.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import com.supermax.base.common.utils.QsHelper;

import java.lang.reflect.Method;

/**
 * @Author yinzh
 * @Date 2019/3/18 10:02
 * @Description 判断是否是允许全屏界面内容显示到刘海区域的刘海屏机型
 */
public class CutoutUtil {
    private static Boolean sAllowDisplayToCutout;

    public static boolean allowDispalyToCuout(){
        if (sAllowDisplayToCutout == null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                // 9.0系统全屏界面默认会保留黑边，不允许显示内容到刘海区域
                return sAllowDisplayToCutout = false;
            }
            Context context = QsHelper.getInstance().getApplication();
            if (hasCutout_Huawei(context)) {
                return sAllowDisplayToCutout = true;
            }
            if (hasCutout_OPPO(context)) {
                return sAllowDisplayToCutout = true;
            }
            if (hasCutout_VIVO(context)) {
                return sAllowDisplayToCutout = true;
            }
            if (hasCutout_XIAOMI(context)) {
                return sAllowDisplayToCutout = true;
            }
            return sAllowDisplayToCutout = false;
        } else {
            return sAllowDisplayToCutout;
        }
    }


    /**
     * 是否是华为刘海屏机型
     */
    @SuppressWarnings("unchecked")
    private static boolean hasCutout_Huawei(Context context) {
        if (!Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            return false;
        }
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            if (HwNotchSizeUtil != null) {
                Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
                return (boolean) get.invoke(HwNotchSizeUtil);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 是否是oppo刘海屏机型
     */
    @SuppressWarnings("unchecked")
    private static boolean hasCutout_OPPO(Context context) {
        if (!Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 是否是vivo刘海屏机型
     */
    @SuppressWarnings("unchecked")
    private static boolean hasCutout_VIVO(Context context) {
        if (!Build.MANUFACTURER.equalsIgnoreCase("vivo")) {
            return false;
        }
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressLint("PrivateApi")
            Class ftFeatureUtil = cl.loadClass("android.util.FtFeature");
            if (ftFeatureUtil != null) {
                Method get = ftFeatureUtil.getMethod("isFeatureSupport", int.class);
                return (boolean) get.invoke(ftFeatureUtil, 0x00000020);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是小米刘海屏机型
     */
    @SuppressWarnings("unchecked")
    private static boolean hasCutout_XIAOMI(Context context) {
        if (!Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            return false;
        }
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressLint("PrivateApi")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
            //参数
            Object[] params = new Object[2];
            params[0] = "ro.miui.notch";
            params[1] = 0;
            return (Integer) getInt.invoke(SystemProperties, params) == 1;
        } catch (Exception e) {
            return false;
        }
    }





}
