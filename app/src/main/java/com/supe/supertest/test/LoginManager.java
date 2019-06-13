package com.supe.supertest.test;

import android.content.Context;

/**
 * @Author yinzh
 * @Date 2019/5/28 16:06
 * @Description
 */
 public class LoginManager {
        private static LoginManager mInstance;
        private Context mContext;

        private LoginManager(Context context) {
            this.mContext = context;
        }


        public static LoginManager getInstance(Context context) {
            if (mInstance == null) {
                synchronized (LoginManager.class) {
                    if (mInstance == null) {
                        mInstance = new LoginManager(context);
                    }
                }
            }
            return mInstance;
        }

        public void dealData() {
        }

    }
