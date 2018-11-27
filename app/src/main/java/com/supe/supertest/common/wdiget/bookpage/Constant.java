package com.supe.supertest.common.wdiget.bookpage;


import com.supe.supertest.common.utils.FileUtils;

import java.io.File;


/**
 * @Author yinzh
 * @Date 2018/11/26 19:34
 * @Description
 */
public class Constant {

    public static final String ZHUISHU_IMAGE_URL = "http://statics.zhuishushenqi.com";
    //Book Date Convert Format
    public static final String FORMAT_BOOK_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_FILE_DATE = "yyyy-MM-dd";

    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = FileUtils.getCachePath() + File.separator
            + "book_cache" + File.separator;

}
