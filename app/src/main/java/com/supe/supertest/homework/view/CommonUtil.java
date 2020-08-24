package com.supe.supertest.homework.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.supermax.base.common.log.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author yinzh
 * @Date 2020/8/24 14:26
 * @Description
 */
public class CommonUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取APP版本
     *
     * @param context
     * @return
     */
    public static String getApkVersion(Context context) {
        String version = "1.0.0";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            L.d("EduSoho", "get apk version error");
        }
        return version;
    }

    /**
     * 获取系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取sdk版本
     *
     * @return
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 屏幕工具
     */
    public static class screenUtil {
        private static int screenHeight = 0;
        private static int screenWidth = 0;
        private static int statusBarHeight = 0;

        /**
         * 获取屏幕宽度
         */
        public static int getScreenWidth(Context mContext) {
            if (screenWidth > 0) {
                return screenWidth;
            }

            if (mContext == null) {
                return 0;
            }
            int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            return screenWidth;
        }

        /**
         * 获取屏幕高度,是否包含导航栏高度
         */
        public static int getScreenHeight(Context mContext, boolean isIncludeNav) {
            if (mContext == null) {
                return 0;
            }
            int screenHeight = getScreenHeight(mContext);
            if (isIncludeNav) {
                return screenHeight;
            } else {
                return screenHeight - getNavigationBarHeight(mContext);
            }
        }

        /**
         * 获取屏幕高(包括底部虚拟按键)
         *
         * @param mContext
         * @return
         */
        public static int getScreenHeight(Context mContext) {
            if (screenHeight > 0) {
                return screenHeight;
            }

            if (mContext == null) {
                return 0;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            //WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = getWindowManager(mContext).getDefaultDisplay();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    display.getRealMetrics(displayMetrics);
                } else {
                    display.getMetrics(displayMetrics);
                }
                screenHeight = displayMetrics.heightPixels;
            } catch (Exception e) {
                screenHeight = display.getHeight();
            }
            return screenHeight;
        }

        /**
         * 获取NavigationBar的高度
         */
        public static int getNavigationBarHeight(Context mContext) {
            if (!hasNavigationBar(mContext)) {
                return 0;
            }
            Resources resources = mContext.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            return resources.getDimensionPixelSize(resourceId);
        }

        /**
         * 是否存在NavigationBar
         */
        public static boolean hasNavigationBar(Context mContext) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display display = getWindowManager(mContext).getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                return realSize.x != size.x || realSize.y != size.y;
            } else {
                boolean menu = ViewConfiguration.get(mContext).hasPermanentMenuKey();
                boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                return !(menu || back);
            }
        }

        /**
         * 获取WindowManager。
         */
        public static WindowManager getWindowManager(Context mContext) {
            if (mContext == null) {
                return null;
            }
            return (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        /**
         * 状态栏height
         *
         * @param mContext
         * @return
         */
        public static int getStatusBarHeight(Context mContext) {
            int height = 0;
            int resourceId = mContext.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                height = mContext.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
            }
            return height;
        }
    }

    /**
     * @param v1
     * @param v2
     * @return
     * @throws RuntimeException
     */
    //服务器api版本
    public static final int NORMAL_VERSIO = 0;
    public static final int HEIGHT_VERSIO = 1;
    public static final int LOW_VERSIO = -1;
    public static int compareVersion(String v1, String v2) throws RuntimeException {
        if (v1 == null || v2 == null) {
            return NORMAL_VERSIO;
        }
        String[] v1Versons = v1.split("\\.");
        String[] v2Versons = v2.split("\\.");
        if (v1Versons.length != v2Versons.length) {
            throw new RuntimeException("版本不一致，无法对比");
        }

        int length = v1Versons.length;
        for (int i = 0; i < length; i++) {
            int firstVersion = Integer.parseInt(v1Versons[i]);
            int secondVersion = Integer.parseInt(v2Versons[i]);
            if (firstVersion > secondVersion) {
                return HEIGHT_VERSIO;
            }
            if (firstVersion < secondVersion) {
                return LOW_VERSIO;
            }
        }

        return NORMAL_VERSIO;
    }

    /**
     * 是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 是否wifi环境
     *
     * @param context
     * @return
     */
    public static boolean isWiFiConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * rom可用大小
     */
    public static String getRomAvailableSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks).replace("B", "");
    }


    public static boolean inArray(String find, String[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        int result = Arrays.binarySearch(array, find, new Comparator<String>() {
            @Override
            public int compare(String find, String str) {
                if (str.equals(find)) {
                    return 0;
                }
                return -1;
            }
        });
        return result >= 0;
    }

    public static String coverCourseAbout(String about) {
        return about.replaceAll("<[^>]+>", "");
    }

    public static int getCourseCorverHeight(int width) {
        float scale = (float) width / 480;
        return (int) (270 * scale);
    }

    /**
     * 将服务器端的时间格式转化为milli Second
     *
     * @param time
     * @return
     */
    public static long convertMilliSec(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long returnTime = 0;
        try {
            String tDate = time.split("[+]")[0].replace('T', ' ');
            return sdf.parse(tDate).getTime();

        } catch (Exception ex) {
            L.d("AppUtil.convertMilliSec", ex.toString());
        }
        return returnTime;
    }

    /**
     * 去掉头部、末尾产生的"\n"
     */
    public static String removeHtml(String strHtml) {
        if (strHtml.length() > 0 && strHtml.contains("\n")) {
            if (strHtml.substring(0, 1).equals("\n")) {
                strHtml = strHtml.substring(1, strHtml.length() - 1);
                return removeHtml(strHtml);
            }
            if (strHtml.substring(strHtml.length() - 1, strHtml.length()).equals("\n")) {
                strHtml = strHtml.substring(0, strHtml.length() - 1);
                return removeHtml(strHtml);
            }
        }
        return strHtml;
    }

    /**
     * 去掉全部"<p></p>"
     */
    public static String removeHtml_P(String strHtml) {
        strHtml = strHtml.replace("<p>", "");
        strHtml = strHtml.replace("</p>", "");
        return strHtml;
    }

    /**
     * 去掉由于Html.fromHtml产生的'\n'
     *
     * @param spanned
     * @return
     */
    public static CharSequence setHtmlContent(Spanned spanned) {
        if (spanned == null) {
            return "";
        }
        if (spanned.length() > 2 && spanned.subSequence(spanned.length() - 2, spanned.length()).toString().equals("\n\n")) {
            return spanned.subSequence(0, spanned.length() - 2);
        }
        return spanned;
    }

    private static Pattern pattern_1 = Pattern.compile("(<img src=\".*?\" .>)");

    /**
     * 去掉所有<Img>标签
     *
     * @param content
     * @return
     */
    public static String removeImgTagFromString(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        Matcher m = pattern_1.matcher(content);
        new StringBuffer().append("1");
        while (m.find()) {
            content = content.replace(m.group(1), "");
        }
        return content;
    }

    private static Pattern pattern_2 = Pattern.compile("\\t|\\n");

    /**
     * 去掉字符串中的\n\type
     *
     * @param content
     * @return
     */
    public static String removeHtmlSpace(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        Matcher m = pattern_2.matcher(content);
        while (m.find()) {
            content = content.replace(m.group(0), "");
        }
        return content;
    }

    public static int computeSampleSize(
            BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(
            BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static int getNumberLength(int number) {
        int length = 1;
        while (number >= 10) {
            length++;
            number = number / 10;
        }

        return length;
    }

    public static SpannableString getColorTextAfter(String text, String newStr, int color) {
        StringBuffer stringBuffer = new StringBuffer(text);
        int start = stringBuffer.length();
        stringBuffer.append(newStr);
        SpannableString spannableString = new SpannableString(stringBuffer);
        spannableString.setSpan(
                new ForegroundColorSpan(color), start, stringBuffer.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    public static SpannableString getColorTextBefore(String text, String newStr, int color) {
        StringBuffer stringBuffer = new StringBuffer(text);
        int start = stringBuffer.length();
        stringBuffer.append(newStr);
        SpannableString spannableString = new SpannableString(stringBuffer);
        spannableString.setSpan(
                new ForegroundColorSpan(color), 0, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    public static Intent getViewFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setAction(Intent.ACTION_VIEW);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String type = mimeTypeMap.getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()));

        intent.setDataAndType(Uri.fromFile(file), type);

        return intent;
    }

    public static int parseInt(String value) {
        int i = 0;
        if (value == null) {
            return i;
        }
        try {
            i = Integer.parseInt(value);
        } catch (Exception e) {
            i = 0;
        }

        return i;
    }

    public static float parseFloat(String value) {
        float i = 0.0f;
        if (value == null) {
            return i;
        }
        try {
            i = Float.parseFloat(value);
        } catch (Exception e) {
            i = 0.0f;
        }

        return i;
    }

    /**
     * 去掉'\n','\type'
     *
     * @return
     */
    public static String filterSpace(String str) {
        return str.replaceAll("\\n|\\t", "");
    }

    /**
     * 旋转图片
     *
     * @param view
     * @param start
     * @param end
     */
    public static void rotation(View view, float start, float end) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        objectAnimator.setDuration(180);
        objectAnimator.start();
    }

    /**
     * 格式化容量
     *
     * @param totalSize
     * @return
     */
    public static String formatSize(long totalSize) {
        L.d(null, "totalSize->" + totalSize);
        float kb = 1024.0f;
        if (totalSize < (kb * kb)) {
            return String.format("%.1f%s", (totalSize / kb), "KB");
        }

        if (totalSize < (kb * kb * kb)) {
            return String.format("%.1f%s", (totalSize / (kb * kb)), "M");
        }

        return String.format("%.1f%s", (totalSize / (kb * kb * kb)), "G");
    }

    public static String timeFormat(int second) {
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp = "0";
        if (0 != hh) {
            strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            strTemp = String.format("%02d:%02d", mm, ss);
        }

        return strTemp;
    }

    /**
     * 初始化对话框
     */
    public static ProgressDialog initProgressDialog(Context context, String msg) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }

    public static String getFileExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos != -1) {
            return fileName.substring(pos);
        }
        return null;
    }

    public static File getCacheFileDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        File workSpace = new File(sdcard, "/edusoho");
        if (!workSpace.exists()) {
            workSpace.mkdir();
        }
        return workSpace;
    }

    public static void longToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }

    public static void shortToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }

    //居中弹出toast
    public static void shortCenterToast(Context context, String title) {
        Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static class MoveTimerTask extends TimerTask {
        public static int LEFT = 0001;
        public static int RIGHT = 0002;

        public int DEF_SIZE;
        public int step;
        public int mOffset;
        private int step_def;
        private int type;

        public MoveTimerTask(int offset, int type, int defsize) {
            this.type = type;
            this.step = offset;
            this.mOffset = type == LEFT ? 0 : offset;
            if (defsize == -1) {
                DEF_SIZE = type == LEFT ? 5 : -5;
            } else {
                DEF_SIZE = type == LEFT ? defsize : -defsize;
            }
            step_def = Math.abs(DEF_SIZE);
        }

        public boolean step() {
            if (step > 0 && step < step_def) {
                DEF_SIZE = type == LEFT ? step : -step;
                step = 0;
                return true;
            }
            step -= step_def;
            return step > 0;
        }

        @Override
        public void run() {

        }
    }

    /**
     * 图片缩小
     *
     * @param bitmap    图片
     * @param imageSize 图片大小
     * @param degree    图片旋转的角度，如果没有旋转，则为0
     * @param context   context
     * @return
     */
    public static Bitmap scaleImage(Bitmap bitmap, float imageSize, int degree, Context context) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //float density = context.getResources().getDisplayMetrics().density;
        int bounding = Math.round(imageSize);

        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(xScale, xScale);
        matrix.postRotate((float) degree);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        return scaledBitmap;
    }

    /**
     * 图片旋转
     *
     * @param bitmap    图片
     * @param imageSize 图片大小
     * @param degree    图片旋转的角度，如果没有旋转，则为0
     * @return
     */
    public static Bitmap scaleImage(Bitmap bitmap, float imageSize, int degree) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //float density = context.getResources().getDisplayMetrics().density;
        int bounding = Math.round(imageSize);

        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postRotate((float) degree);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean bundleHasKey(Bundle bundle, String key) {
        for (String element : bundle.keySet()) {
            if (element.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T[] concatArray(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 判断是否Debug模式
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean isIntentSafe(Activity activity, Uri uri) {
        Intent mapCall = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapCall, 0);
        return activities.size() > 0;
    }
}
