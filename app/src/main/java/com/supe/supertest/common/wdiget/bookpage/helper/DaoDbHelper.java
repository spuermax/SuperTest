package com.supe.supertest.common.wdiget.bookpage.helper;

import android.database.sqlite.SQLiteDatabase;

import com.supe.supertest.common.wdiget.bookpage.bean.DaoMaster;
import com.supe.supertest.common.wdiget.bookpage.bean.DaoSession;
import com.supermax.base.common.utils.QsHelper;

/**
 * @Author yinzh
 * @Date 2018/11/26 16:39
 * @Description
 */
public class DaoDbHelper {
    private static final String DB_NAME = "WeYueReader_DB";

    private static volatile DaoDbHelper sInstance;
    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;

    private DaoDbHelper() {
        //封装数据库的创建、更新、删除
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(QsHelper.getInstance().getApplication(), DB_NAME, null);
        //获取数据库
        mDb = openHelper.getWritableDatabase();
        //封装数据库中表的创建、更新、删除
        mDaoMaster = new DaoMaster(mDb);  //合起来就是对数据库的操作
        //对表操作的对象。
        mSession = mDaoMaster.newSession(); //可以认为是对数据的操作
    }


    public static DaoDbHelper getInstance() {
        if (sInstance == null) {
            synchronized (DaoDbHelper.class) {
                if (sInstance == null) {
                    sInstance = new DaoDbHelper();
                }
            }
        }
        return sInstance;
    }

    public DaoSession getSession() {
        return mSession;
    }

    public SQLiteDatabase getDatabase() {
        return mDb;
    }

    public DaoSession getNewSession() {
        return mDaoMaster.newSession();
    }
}

