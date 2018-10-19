package com.supe.supertest.common.utils;

import android.Manifest;

import com.supe.supertest.R;
import com.supermax.base.common.utils.QsHelper;

import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/19 10:44
 * @Description
 */
public class PermissionUtils {
    private static final String TAG = "PermissionUtils";


    /**
     *
     * @param permission
     * @return
     */
    public static String getPermissionDialogMessage(List<String> permission){

        if(permission == null || permission.size() < 1 ) return null;
        StringBuilder stringbuilder =new StringBuilder();

        stringbuilder.append("(");
        for (int i = 0, size = permission.size(); i < size; i++){
            switch (permission.get(i)){
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    stringbuilder.append("定位").append(i == size - 1 ? "" :  ",") ;
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    stringbuilder.append("读取外部储存").append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_write_external_storage_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.READ_CONTACTS:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_constants_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.CALL_PHONE:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_call_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.CAMERA:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_camera_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_record_audio_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    stringbuilder.append(QsHelper.getInstance().getApplication().getString(R.string.request_read_phone_state_permission)).append((i == size - 1) ? "" : "，");
                    break;
            }
        }
        return stringbuilder.append(")").append("权限被禁止啦，需要开启才能执行后续操作").toString();
    }
}
